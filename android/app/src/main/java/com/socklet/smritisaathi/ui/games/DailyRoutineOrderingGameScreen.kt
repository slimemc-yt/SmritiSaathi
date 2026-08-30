package com.socklet.smritisaathi.ui.games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.DailyRoutine
import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

data class RoutineStep(
    val id: Int,
    val chronologicalRank: Int,
    val title: String,
    val icon: ImageVector,
    val timeOfDay: String,
    val tagColor: String = "primary"
)

object RoutineGenerator {
    private val morningPool = listOf(
        Pair("Wake Up & Drink Warm Water", Icons.Default.WbSunny),
        Pair("Brush Teeth & Morning Wash", Icons.Default.Bathtub),
        Pair("Morning Prayer & Meditation", Icons.Default.SelfImprovement),
        Pair("Eat Healthy Breakfast", Icons.Default.Restaurant),
        Pair("Take Morning Medication", Icons.Default.Medication),
        Pair("Tend to House Plants / Garden", Icons.Default.LocalFlorist)
    )

    private val afternoonPool = listOf(
        Pair("Eat Warm Nutritious Lunch", Icons.Default.LunchDining),
        Pair("Restful Afternoon Nap", Icons.Default.Bedtime),
        Pair("Drink Hot Assam Chai", Icons.Default.Coffee),
        Pair("Listen to Soothing Music", Icons.Default.MusicNote)
    )

    private val eveningPool = listOf(
        Pair("Evening Walk in Fresh Air", Icons.Default.DirectionsWalk),
        Pair("Family Chai & Conversation", Icons.Default.People),
        Pair("Eat Light Dinner", Icons.Default.DinnerDining),
        Pair("Take Night Medication", Icons.Default.Medication),
        Pair("Brush Teeth & Prepare for Sleep", Icons.Default.CleaningServices),
        Pair("Sleep in Warm Cozy Bed", Icons.Default.NightsStay)
    )

    fun generateSteps(difficultyLevel: Int, customRoutine: DailyRoutine? = null): List<RoutineStep> {
        val selected = mutableListOf<RoutineStep>()
        var idCounter = 1

        when (difficultyLevel) {
            1 -> {
                // 3 steps: 1 Morning -> 1 Afternoon -> 1 Evening/Night
                val m = morningPool.random()
                val a = afternoonPool.random()
                val e = eveningPool.random()

                selected.add(RoutineStep(idCounter++, 1, m.first, m.second, "Morning"))
                selected.add(RoutineStep(idCounter++, 2, a.first, a.second, "Afternoon"))
                selected.add(RoutineStep(idCounter++, 3, e.first, e.second, "Night"))
            }
            2 -> {
                // 4 steps: 2 Morning/Midday -> 1 Afternoon -> 1 Night
                val m1 = morningPool.take(3).random()
                val m2 = morningPool.filter { it != m1 }.random()
                val a = afternoonPool.random()
                val e = eveningPool.takeLast(3).random()

                selected.add(RoutineStep(idCounter++, 1, m1.first, m1.second, "Morning"))
                selected.add(RoutineStep(idCounter++, 2, m2.first, m2.second, "Late Morning"))
                selected.add(RoutineStep(idCounter++, 3, a.first, a.second, "Afternoon"))
                selected.add(RoutineStep(idCounter++, 4, e.first, e.second, "Night"))
            }
            else -> {
                // 5 steps: 2 Morning -> 1 Afternoon -> 2 Evening/Night
                val m1 = morningPool.first()
                val m2 = morningPool.drop(1).random()
                val a = afternoonPool.random()
                val e1 = eveningPool.take(2).random()
                val e2 = eveningPool.last()

                selected.add(RoutineStep(idCounter++, 1, m1.first, m1.second, "Early Morning"))
                selected.add(RoutineStep(idCounter++, 2, m2.first, m2.second, "Morning"))
                selected.add(RoutineStep(idCounter++, 3, a.first, a.second, "Afternoon"))
                selected.add(RoutineStep(idCounter++, 4, e1.first, e1.second, "Evening"))
                selected.add(RoutineStep(idCounter++, 5, e2.first, e2.second, "Bedtime"))
            }
        }
        return selected
    }
}

@Composable
fun DailyRoutineOrderingGameScreen(
    difficultyLevel: Int,
    customRoutine: DailyRoutine? = null,
    voiceAssistant: VoiceAssistantManager,
    onComplete: (GameResult) -> Unit,
    onDistressTriggered: () -> Unit,
    onExit: () -> Unit
) {
    // Session state
    var currentRound by remember { mutableStateOf(1) }
    val totalRounds = 3
    var currentLevel by remember { mutableStateOf(difficultyLevel) }
    
    // Round state
    val targetSteps = remember(currentRound, currentLevel) {
        RoutineGenerator.generateSteps(currentLevel, customRoutine)
    }

    var userSequence by remember(currentRound) {
        var shuffled = targetSteps.shuffled()
        var attempts = 0
        while (shuffled.map { it.chronologicalRank } == targetSteps.map { it.chronologicalRank } && attempts < 5) {
            shuffled = targetSteps.shuffled()
            attempts++
        }
        mutableStateOf(shuffled)
    }

    var mistakesCount by remember { mutableStateOf(0) }
    var roundMistakes by remember { mutableStateOf(0) }
    var feedbackMessage by remember { mutableStateOf<String?>(null) }
    val startTime = remember { System.currentTimeMillis() }
    var roundStartTime by remember { mutableStateOf(System.currentTimeMillis()) }
    
    // Performance tracking
    var roundScores by remember { mutableStateOf(listOf<Int>()) }
    var roundAccuracies by remember { mutableStateOf(listOf<Float>()) }
    
    // Dialogs
    var showRoundCompleteDialog by remember { mutableStateOf(false) }
    var showSessionCompleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        voiceAssistant.speak("Let's arrange daily activities from morning to night. Tap the arrows to move steps up or down. Round $currentRound of $totalRounds.")
    }

    fun moveUp(index: Int) {
        if (index > 0) {
            val list = userSequence.toMutableList()
            val temp = list[index]
            list[index] = list[index - 1]
            list[index - 1] = temp
            userSequence = list
            feedbackMessage = null
        }
    }

    fun moveDown(index: Int) {
        if (index < userSequence.size - 1) {
            val list = userSequence.toMutableList()
            val temp = list[index]
            list[index] = list[index + 1]
            list[index + 1] = temp
            userSequence = list
            feedbackMessage = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimensions.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onExit) {
                Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(32.dp))
            }
            Text(
                text = "Order Daily Routine",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = {
                voiceAssistant.speak("Arrange these activities in order from morning to bedtime. Use the arrows.")
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Help Audio", modifier = Modifier.size(32.dp))
            }
        }

        // Subtitle
        Text(
            text = "Morning (Top) ☀️ ➔ Night (Bottom) 🌙",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )

        // Steps List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = Dimensions.Space12),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
        ) {
            itemsIndexed(userSequence, key = { _, step -> step.id }) { index, step ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Space12),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Left step index & icon
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(Dimensions.Space12))

                            Icon(
                                imageVector = step.icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )

                            Spacer(modifier = Modifier.width(Dimensions.Space8))

                            Column {
                                Text(
                                    text = step.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = step.timeOfDay,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Right movement buttons (Up / Down)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { moveUp(index) },
                                enabled = index > 0
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowUpward,
                                    contentDescription = "Move Up",
                                    tint = if (index > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                                )
                            }

                            IconButton(
                                onClick = { moveDown(index) },
                                enabled = index < userSequence.size - 1
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDownward,
                                    contentDescription = "Move Down",
                                    tint = if (index < userSequence.size - 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }
        }

        if (feedbackMessage != null) {
            Text(
                text = feedbackMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(Dimensions.Space8))
        }

        // Check Sequence Button
        Button(
            onClick = {
                val isCorrect = userSequence.mapIndexed { idx, s -> s.chronologicalRank == idx + 1 }.all { it }

                if (isCorrect) {
                    voiceAssistant.speak("Perfect! Your daily routine is arranged in beautiful harmony.")
                    val roundTime = System.currentTimeMillis() - roundStartTime
                    val score = ((targetSteps.size.toFloat() / (targetSteps.size + roundMistakes)) * 100).toInt().coerceIn(50, 100)
                    val accuracy = targetSteps.size.toFloat() / (targetSteps.size + roundMistakes)
                    
                    roundScores = roundScores + score
                    roundAccuracies = roundAccuracies + accuracy
                    
                    if (currentRound >= totalRounds) {
                        showSessionCompleteDialog = true
                    } else {
                        showRoundCompleteDialog = true
                    }
                } else {
                    roundMistakes++
                    mistakesCount++
                    feedbackMessage = "Not quite in order yet. Morning steps should come before evening!"
                    voiceAssistant.speak("Almost there! Look at which activity happens first in the morning.")
                    if (roundMistakes >= 3) {
                        onDistressTriggered()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Text("Check Order ✅", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
    
    // Round Complete Dialog
    if (showRoundCompleteDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Round Complete! 🎉") },
            text = {
                Column {
                    Text("Great job! You arranged the routine correctly!")
                    Spacer(Modifier.height(8.dp))
                    Text("Score: ${roundScores.last()}%")
                    Text("Round $currentRound of $totalRounds complete")
                    Spacer(Modifier.height(8.dp))
                    Text("Ready for Round ${currentRound + 1}?")
                }
            },
            confirmButton = {
                Button(onClick = {
                    currentRound++
                    // Adaptive difficulty
                    currentLevel = when {
                        roundAccuracies.takeLast(2).average() >= 0.85 -> (currentLevel + 1).coerceAtMost(5)
                        roundAccuracies.takeLast(2).average() < 0.5 -> (currentLevel - 1).coerceAtLeast(1)
                        else -> currentLevel
                    }
                    roundMistakes = 0
                    feedbackMessage = null
                    roundStartTime = System.currentTimeMillis()
                    showRoundCompleteDialog = false
                    voiceAssistant.speak("Round $currentRound! Let's arrange the routine!")
                }) {
                    Text("Next Round")
                }
            }
        )
    }
    
    // Session Complete Dialog
    if (showSessionCompleteDialog) {
        val avgScore = roundScores.average().toInt()
        val avgAccuracy = (roundAccuracies.average() * 100).toInt()
        val totalTime = System.currentTimeMillis() - startTime
        
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Session Complete! 🌟") },
            text = {
                Column {
                    Text("Wonderful! You completed all $totalRounds rounds!")
                    Spacer(Modifier.height(12.dp))
                    Text("Average Score: $avgScore%")
                    Text("Average Accuracy: $avgAccuracy%")
                    Text("Total Mistakes: $mistakesCount")
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = when {
                            avgAccuracy >= 90 -> "Excellent! Your sequencing skills are sharp! 🏆"
                            avgAccuracy >= 70 -> "Great job! You're doing wonderfully! 👏"
                            avgAccuracy >= 50 -> "Good effort! Keep practicing! 💪"
                            else -> "Thank you for playing! Every bit helps! 🌸"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showSessionCompleteDialog = false
                    val result = GameResult(
                        gameType = GameType.DAILY_ROUTINE,
                        score = avgScore,
                        difficultyLevel = currentLevel,
                        responseTimeMs = totalTime,
                        mistakesCount = mistakesCount,
                        completedSuccessfully = true
                    )
                    voiceAssistant.speak("Wonderful! You completed all rounds! Your sequencing skills are improving!")
                    onComplete(result)
                }) {
                    Text("Finish")
                }
            }
        )
    }
}
