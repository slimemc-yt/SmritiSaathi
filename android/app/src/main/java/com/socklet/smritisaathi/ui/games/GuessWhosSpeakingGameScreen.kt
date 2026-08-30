package com.socklet.smritisaathi.ui.games

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.FamilyContact
import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

data class VoiceClipQuestion(
    val id: String,
    val speakerName: String,
    val relationship: String,
    val spokenGreeting: String,
    val options: List<String>,
    val correctIndex: Int,
    val category: String
)

object VoiceClipQuestions {
    val allQuestions = listOf(
        // Family members
        VoiceClipQuestion("v1", "Aarav", "Grandson", 
            "Dadu, I love you! I am coming over to play ludo this evening!",
            listOf("Aarav (Grandson)", "Rajesh (Son)", "Dr. Baruah"), 0, "Family"),
        VoiceClipQuestion("v2", "Sunita", "Daughter",
            "Namaste Maa! Did you have your morning tea and medicines today?",
            listOf("Sunita (Daughter)", "Meera (Daughter-in-law)", "Nurse Priyanka"), 0, "Family"),
        VoiceClipQuestion("v3", "Rajesh", "Son",
            "Baba, I'll pick you up for the doctor's appointment at 10 AM tomorrow.",
            listOf("Rajesh (Son)", "Aarav (Grandson)", "Neighbor Ramesh"), 0, "Family"),
        VoiceClipQuestion("v4", "Meera", "Daughter-in-law",
            "Maa, I've prepared your favorite dal bhat for lunch. Please come eat!",
            listOf("Meera (Daughter-in-law)", "Sunita (Daughter)", "Sister Kamala"), 0, "Family"),
        VoiceClipQuestion("v5", "Priya", "Granddaughter",
            "Aai, I drew a picture for you! Can we color together today?",
            listOf("Priya (Granddaughter)", "Sunita (Daughter)", "Neighbor's child"), 0, "Family"),
            
        // Friends/Neighbors
        VoiceClipQuestion("v6", "Ramesh", "Neighbor",
            "Good morning! Would you like to take a walk in the park together?",
            listOf("Ramesh (Neighbor)", "Rajesh (Son)", "Dr. Baruah"), 0, "Friends"),
        VoiceClipQuestion("v7", "Kamala", "Sister",
            "Didi, I'm making pickle today. Remember how we used to make it together?",
            listOf("Kamala (Sister)", "Sunita (Daughter)", "Meera (Daughter-in-law)"), 0, "Friends"),
        VoiceClipQuestion("v8", "Bimal", "Old Friend",
            "Remember our college days? Let's meet for tea this weekend!",
            listOf("Bimal (Old Friend)", "Ramesh (Neighbor)", "Rajesh (Son)"), 0, "Friends"),
            
        // Caregivers
        VoiceClipQuestion("v9", "Nurse Priyanka", "Nurse",
            "Good morning! It's time for your blood pressure check and morning walk.",
            listOf("Nurse Priyanka", "Dr. Baruah", "Sunita (Daughter)"), 0, "Caregivers"),
        VoiceClipQuestion("v10", "Dr. Baruah", "Doctor",
            "How are you feeling today? Remember to take your medicines after meals.",
            listOf("Dr. Baruah", "Nurse Priyanka", "Rajesh (Son)"), 0, "Caregivers"),
        VoiceClipQuestion("v11", "Helper Laxmi", "Helper",
            "Didi, I've cleaned the house and washed the clothes. What else should I do?",
            listOf("Helper Laxmi", "Meera (Daughter-in-law)", "Kamala (Sister)"), 0, "Caregivers"),
            
        // Daily activities
        VoiceClipQuestion("v12", "Milkman", "Milkman",
            "Good morning! Here's your daily milk delivery. That's 50 rupees please.",
            listOf("Milkman", "Helper Laxmi", "Neighbor Ramesh"), 0, "Daily"),
        VoiceClipQuestion("v13", "Postman", "Postman",
            "You have a letter from your grandson studying in Delhi!",
            listOf("Postman", "Rajesh (Son)", "Dr. Baruah"), 0, "Daily"),
        VoiceClipQuestion("v14", "Grocery Shop", "Shopkeeper",
            "Welcome! We have fresh vegetables today. What would you like to buy?",
            listOf("Grocery Shop", "Milkman", "Helper Laxmi"), 0, "Daily"),
            
        // Cultural/Religious
        VoiceClipQuestion("v15", "Temple Priest", "Priest",
            "Tomorrow is Ekadashi. Would you like to come for the morning prayers?",
            listOf("Temple Priest", "Dr. Baruah", "Sister Kamala"), 0, "Cultural"),
        VoiceClipQuestion("v16", "Bhajan Group", "Group Leader",
            "We're singing bhajans this evening at 5 PM. Please join us!",
            listOf("Bhajan Group", "Temple Priest", "Sister Kamala"), 0, "Cultural")
    )
}

@Composable
fun GuessWhosSpeakingGameScreen(
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
    
    // UI state
    var selectedOptIdx by remember { mutableStateOf<Int?>(null) }
    var isFeedbackShowing by remember { mutableStateOf(false) }
    
    // Dialogs
    var showRoundCompleteDialog by remember { mutableStateOf(false) }
    var showSessionCompleteDialog by remember { mutableStateOf(false) }
    
    // Questions per round based on difficulty
    val questionsPerRound = when (currentLevel) {
        1 -> 3
        2 -> 4
        3 -> 5
        else -> 5
    }
    
    // Get questions for this round (avoid repetition)
    val roundQuestions = remember(currentRound, currentLevel) {
        VoiceClipQuestions.allQuestions.shuffled().take(questionsPerRound)
    }
    
    val currentQuestion = roundQuestions.getOrNull(currentQuestionIndex)

    LaunchedEffect(currentQuestionIndex) {
        currentQuestion?.let {
            voiceAssistant.speak("Listen to this voice message: \"${it.spokenGreeting}\". Who is speaking?")
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
        // Header
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
                    text = "Voice Recognition",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Round $currentRound of $totalRounds • Question ${currentQuestionIndex + 1}/$questionsPerRound",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = {
                currentQuestion?.let { voiceAssistant.speak("\"${it.spokenGreeting}\"") }
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Play Audio", modifier = Modifier.size(32.dp))
            }
        }

        // Voice Player Box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimensions.Space16),
            shape = RoundedCornerShape(Dimensions.CardCornerRadius),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Space20),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            currentQuestion?.let { voiceAssistant.speak("\"${it.spokenGreeting}\"") }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Replay Audio",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(Dimensions.Space12))

                Text(
                    text = "Tap to replay voice clip 🔊",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(Dimensions.Space8))

                Text(
                    text = "\"${currentQuestion?.spokenGreeting ?: ""}\"",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }

        Text(
            text = "Whose voice did you hear?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space8)
        ) {
            currentQuestion?.options?.forEachIndexed { optIndex, optionText ->
                val isSelected = selectedOptIdx == optIndex
                val isCorrect = optIndex == currentQuestion.correctIndex

                val cardColor = if (isFeedbackShowing && isSelected) {
                    if (isCorrect) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clickable(enabled = !isFeedbackShowing) {
                            selectedOptIdx = optIndex
                            isFeedbackShowing = true

                            if (isCorrect) {
                                roundScore += (100 / questionsPerRound)
                                voiceAssistant.speak("Yes! That was indeed ${currentQuestion.speakerName}.")
                            } else {
                                roundMistakes++
                                totalMistakes++
                                voiceAssistant.speak("That was your ${currentQuestion.relationship}, ${currentQuestion.speakerName}.")
                            }
                        },
                    shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimensions.Space16),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                        if (isFeedbackShowing && isSelected) {
                            if (isCorrect) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            } else {
                                Icon(Icons.Default.Close, contentDescription = null, tint = MaterialTheme.colorScheme.error)
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
                    selectedOptIdx = null
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
                    text = if (currentQuestionIndex + 1 < questionsPerRound) "Next Voice ➔" else "Complete Round 🎵",
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
            title = { Text("Round Complete! 🎵") },
            text = {
                Column {
                    Text("Great job recognizing voices!")
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
                    voiceAssistant.speak("Round $currentRound! Let's recognize more voices!")
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
                            avgAccuracy >= 70 -> "Great job! You know your people well! 👏"
                            avgAccuracy >= 50 -> "Good effort! Keep listening! 💪"
                            else -> "Thank you for playing! Voices are important! 🌸"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showSessionCompleteDialog = false
                    val result = GameResult(
                        gameType = GameType.GUESS_WHOS_SPEAKING,
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
