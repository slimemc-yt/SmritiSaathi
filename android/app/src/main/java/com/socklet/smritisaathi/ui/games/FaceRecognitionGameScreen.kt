package com.socklet.smritisaathi.ui.games

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.socklet.smritisaathi.domain.model.FamilyContact
import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager
import kotlinx.coroutines.delay

// Relationship-based questions for variety
data class RelationshipQuestion(
    val id: String,
    val clue: String,
    val answerId: String,
    val category: String
)

object RelationshipQuestions {
    val questions = listOf(
        // Family relationships
        RelationshipQuestion("q1", "Who cooks delicious food for you?", "1", "Family"),
        RelationshipQuestion("q2", "Who reads you stories?", "2", "Family"),
        RelationshipQuestion("q3", "Who plays games with you?", "3", "Family"),
        RelationshipQuestion("q4", "Who helps you with medicine?", "4", "Family"),
        RelationshipQuestion("q5", "Who takes you for walks?", "5", "Family"),
        RelationshipQuestion("q6", "Who sings songs to you?", "6", "Family"),
        RelationshipQuestion("q7", "Who helps you bathe?", "7", "Family"),
        RelationshipQuestion("q8", "Who reads the newspaper to you?", "8", "Family"),
        
        // Daily activities
        RelationshipQuestion("q9", "Who makes your morning tea?", "1", "Daily"),
        RelationshipQuestion("q10", "Who prepares your lunch?", "2", "Daily"),
        RelationshipQuestion("q11", "Who helps you dress?", "3", "Daily"),
        RelationshipQuestion("q12", "Who reminds you to take medicine?", "4", "Daily"),
        
        // Emotional connections
        RelationshipQuestion("q13", "Who gives you the best hugs?", "5", "Emotional"),
        RelationshipQuestion("q14", "Who makes you laugh the most?", "6", "Emotional"),
        RelationshipQuestion("q15", "Who tells you childhood stories?", "7", "Emotional"),
        RelationshipQuestion("q16", "Who you miss the most?", "8", "Emotional")
    )
}

@Composable
fun FaceRecognitionGameScreen(
    contacts: List<FamilyContact>,
    difficultyLevel: Int,
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
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var roundScore by remember { mutableStateOf(0) }
    var roundMistakes by remember { mutableStateOf(0) }
    var totalMistakes by remember { mutableStateOf(0) }
    val startTime = remember { System.currentTimeMillis() }
    var roundStartTime by remember { mutableStateOf(System.currentTimeMillis()) }
    
    // Performance tracking
    var roundScores by remember { mutableStateOf(listOf<Int>()) }
    var roundAccuracies by remember { mutableStateOf(listOf<Float>()) }
    
    // Dialogs
    var showRoundCompleteDialog by remember { mutableStateOf(false) }
    var showSessionCompleteDialog by remember { mutableStateOf(false) }
    
    val sampleContacts = remember(contacts) {
        if (contacts.isNotEmpty()) contacts
        else listOf(
            FamilyContact(id = "1", name = "Aarav", relationship = "Grandson"),
            FamilyContact(id = "2", name = "Sunita", relationship = "Daughter"),
            FamilyContact(id = "3", name = "Rajesh", relationship = "Son"),
            FamilyContact(id = "4", name = "Meera", relationship = "Daughter-in-law"),
            FamilyContact(id = "5", name = "Priya", relationship = "Granddaughter"),
            FamilyContact(id = "6", name = "Amit", relationship = "Neighbor"),
            FamilyContact(id = "7", name = "Kamala", relationship = "Sister"),
            FamilyContact(id = "8", name = "Ramesh", relationship = "Brother")
        )
    }

    // Questions per round based on difficulty
    val questionsPerRound = when (currentLevel) {
        1 -> 3
        2 -> 4
        3 -> 5
        else -> 5
    }.coerceAtMost(sampleContacts.size)
    
    // Get questions for this round (avoid repetition)
    val roundQuestions = remember(currentRound, currentLevel) {
        RelationshipQuestions.questions.shuffled().take(questionsPerRound)
    }
    
    val currentQuestion = roundQuestions.getOrNull(currentQuestionIndex)
    val currentTarget = currentQuestion?.let { q ->
        sampleContacts.find { it.id == q.answerId }
    }

    val options = remember(currentQuestionIndex) {
        if (currentTarget != null) {
            val others = sampleContacts.filter { it.id != currentTarget.id }.shuffled().take(2)
            (others + currentTarget).shuffled()
        } else emptyList()
    }

    var selectedOption by remember { mutableStateOf<FamilyContact?>(null) }
    var isFeedbackShowing by remember { mutableStateOf(false) }

    LaunchedEffect(currentQuestionIndex) {
        currentQuestion?.let {
            voiceAssistant.speak(it.clue)
        }
    }

    fun completeRound() {
        val accuracy = (questionsPerRound - roundMistakes).toFloat() / questionsPerRound
        val score = (accuracy * 100).toInt().coerceIn(0, 100)
        
        roundScores = roundScores + score
        roundAccuracies = roundAccuracies + accuracy
        
        if (currentRound >= totalRounds) {
            showSessionCompleteDialog = true
        } else {
            showRoundCompleteDialog = true
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
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onExit) {
                Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(32.dp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Round $currentRound of $totalRounds",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Question ${currentQuestionIndex + 1} of $questionsPerRound",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = {
                currentQuestion?.let { voiceAssistant.speak(it.clue) }
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Hear Again", modifier = Modifier.size(32.dp))
            }
        }

        // Center Content: Clue & Photo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = Dimensions.Space16)
        ) {
            // Clue text
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    text = currentQuestion?.clue ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(Dimensions.Space16)
                )
            }
            
            Spacer(modifier = Modifier.height(Dimensions.Space16))

            // Photo
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (!currentTarget?.photoUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = currentTarget?.photoUrl,
                        contentDescription = "Family Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Space16))

            Text(
                text = "Who is this?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        // Options Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
        ) {
            options.forEach { option ->
                val isCorrect = option.id == currentTarget?.id
                val isSelected = selectedOption == option

                val buttonColor = if (isFeedbackShowing && isSelected) {
                    if (isCorrect) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.errorContainer
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clickable(enabled = !isFeedbackShowing) {
                            selectedOption = option
                            isFeedbackShowing = true

                            if (isCorrect) {
                                roundScore += (100 / questionsPerRound)
                                voiceAssistant.speak("Wonderful! Yes, this is ${option.name}, your ${option.relationship}.")
                            } else {
                                roundMistakes++
                                totalMistakes++
                                voiceAssistant.speak("That's okay! This is ${currentTarget?.name}, your ${currentTarget?.relationship}.")
                            }
                        },
                    shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                    colors = CardDefaults.cardColors(containerColor = buttonColor),
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(3.dp, MaterialTheme.colorScheme.primary) else null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimensions.Space20),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${option.name} (${option.relationship})",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                        if (isFeedbackShowing && isSelected) {
                            if (isCorrect) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                            } else {
                                Icon(Icons.Default.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(28.dp))
                            }
                        }
                    }
                }
            }
        }

        // Bottom Action
        if (isFeedbackShowing) {
            Button(
                onClick = {
                    isFeedbackShowing = false
                    selectedOption = null
                    if (currentQuestionIndex + 1 < questionsPerRound) {
                        currentQuestionIndex++
                    } else {
                        completeRound()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.ButtonHeight),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Text(
                    text = if (currentQuestionIndex + 1 < questionsPerRound) "Next Question ➔" else "Complete Round 🎉",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Spacer(modifier = Modifier.height(Dimensions.ButtonHeight))
        }
    }
    
    // Round Complete Dialog
    if (showRoundCompleteDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Round Complete! 🎉") },
            text = {
                Column {
                    Text("Great job recognizing your family members!")
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
                    currentLevel = when {
                        roundAccuracies.takeLast(2).average() >= 0.85 -> (currentLevel + 1).coerceAtMost(5)
                        roundAccuracies.takeLast(2).average() < 0.5 -> (currentLevel - 1).coerceAtLeast(1)
                        else -> currentLevel
                    }
                    currentQuestionIndex = 0
                    roundScore = 0
                    roundMistakes = 0
                    roundStartTime = System.currentTimeMillis()
                    showRoundCompleteDialog = false
                    voiceAssistant.speak("Round $currentRound! Let's recognize more family members!")
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
                    Text("Total Mistakes: $totalMistakes")
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = when {
                            avgAccuracy >= 90 -> "Excellent! You recognize everyone! 🏆"
                            avgAccuracy >= 70 -> "Great job! You know your family well! 👏"
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
                        gameType = GameType.FACE_RECOGNITION,
                        score = avgScore,
                        difficultyLevel = currentLevel,
                        responseTimeMs = totalTime,
                        mistakesCount = totalMistakes,
                        completedSuccessfully = true
                    )
                    voiceAssistant.speak("Wonderful! You completed all rounds! Your memory is getting stronger!")
                    onComplete(result)
                }) {
                    Text("Finish")
                }
            }
        )
    }
}
