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
import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

data class MusicQuestion(
    val id: String,
    val category: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val hint: String
)

object MusicQuestions {
    val questions = listOf(
        // Assamese music
        MusicQuestion("m1", "Assamese", "Who is known as the 'Bard of Brahmaputra'?", 
            listOf("Bhupen Hazarika", "Zubeen Garg", "Lachit Barphukan"), 0,
            "He composed 'Mur Minoti' and many timeless melodies"),
        MusicQuestion("m2", "Assamese", "Which instrument is commonly used in Bihu songs?",
            listOf("Dhol", "Guitar", "Flute"), 0,
            "It's a traditional drum played during Bihu festival"),
        MusicQuestion("m3", "Assamese", "What is the name of the Assamese New Year song?",
            listOf("Bihu", "Durga Puja", "Holi"), 0,
            "Celebrated in mid-April with dance and music"),
            
        // Indian classical
        MusicQuestion("m4", "Classical", "Who is known as the 'Nightingle of India'?",
            listOf("Lata Mangeshkar", "Asha Bhosle", "Suman Kalyanpur"), 0,
            "She sang over 25,000 songs in multiple languages"),
        MusicQuestion("m5", "Classical", "Which instrument did Ravi Shankar play?",
            listOf("Sitar", "Tabla", "Violin"), 0,
            "He brought Indian classical music to the world"),
        MusicQuestion("m6", "Classical", "What is the tabla?",
            listOf("A pair of drums", "A string instrument", "A wind instrument"), 0,
            "It's played with hands and produces rhythmic sounds"),
            
        // Folk music
        MusicQuestion("m7", "Folk", "Wangala festival is celebrated in which state?",
            listOf("Meghalaya", "Assam", "Manipur"), 0,
            "It's a harvest festival with 100 drums"),
        MusicQuestion("m8", "Folk", "Bhangra is a folk dance from which state?",
            listOf("Punjab", "Gujarat", "Rajasthan"), 0,
            "It's performed during harvest celebrations"),
        MusicQuestion("m9", "Folk", "Garba is a traditional dance from which state?",
            listOf("Gujarat", "Maharashtra", "Kerala"), 0,
            "Performed during Navratri festival"),
            
        // Bollywood classics
        MusicQuestion("m10", "Bollywood", "Who sang 'Lag Ja Gale'?",
            listOf("Lata Mangeshkar", "Asha Bhosle", "Geeta Dutt"), 0,
            "A classic song from the 1960s"),
        MusicQuestion("m11", "Bollywood", "Which song starts with 'Ajeeb Dastaan Hai Yeh'?",
            listOf("Dil Apna Aur Preet Parai", "Mughal-e-Azam", "Guide"), 0,
            "A famous song about life's strange story"),
        MusicQuestion("m12", "Bollywood", "Who composed music for 'Mughal-e-Azam'?",
            listOf("Naushad", "S.D. Burman", "Shankar-Jaikishan"), 0,
            "He composed 'Pyar Kiya To Darna Kya'"),
            
        // Devotional
        MusicQuestion("m13", "Devotional", "Which hymn starts with 'Amazing Grace'?",
            listOf("Christian hymn", "Hindu bhajan", "Sufi qawwali"), 0,
            "How sweet the sound that saved a wretch like me"),
        MusicQuestion("m14", "Devotional", "What is a bhajan?",
            listOf("Devotional song", "Love song", "Patriotic song"), 0,
            "Sung in praise of God in Indian traditions"),
        MusicQuestion("m15", "Devotional", "Qawwali is associated with which tradition?",
            listOf("Sufi", "Vedic", "Sikh"), 0,
            "Nusrat Fateh Ali Khan was a famous qawwal"),
            
        // Musical instruments
        MusicQuestion("m16", "Instruments", "How many strings does a sitar typically have?",
            listOf("18-21", "6", "10"), 0,
            "It's a long-necked string instrument"),
        MusicQuestion("m17", "Instruments", "The flute is made of?",
            listOf("Bamboo", "Metal", "Wood"), 0,
            "Lord Krishna is often shown playing it"),
        MusicQuestion("m18", "Instruments", "Which instrument has keys and is played with fingers?",
            listOf("Piano", "Drum", "Flute"), 0,
            "It has black and white keys")
    )
}

@Composable
fun MusicMemoryGameScreen(
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
    var showHint by remember { mutableStateOf(false) }
    
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
        MusicQuestions.questions.shuffled().take(questionsPerRound)
    }
    
    val currentQuestion = roundQuestions.getOrNull(currentQuestionIndex)

    LaunchedEffect(currentQuestionIndex) {
        currentQuestion?.let {
            voiceAssistant.speak("${it.category} music: ${it.question}")
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
                    text = "Music Memory",
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
                currentQuestion?.let { voiceAssistant.speak("${it.category} music: ${it.question}") }
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Hear Again", modifier = Modifier.size(32.dp))
            }
        }

        // Question Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(Dimensions.Space20),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(Modifier.height(Dimensions.Space12))
                Text(
                    text = currentQuestion?.category ?: "",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(Dimensions.Space8))
                Text(
                    text = currentQuestion?.question ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                if (showHint) {
                    Spacer(Modifier.height(Dimensions.Space8))
                    Text(
                        text = "💡 ${currentQuestion?.hint}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(Modifier.height(Dimensions.Space16))

        // Options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
        ) {
            currentQuestion?.options?.forEachIndexed { index, option ->
                val isCorrect = index == currentQuestion.correctIndex
                val isSelected = selectedOptIdx == index

                val buttonColor = if (isFeedbackShowing) {
                    when {
                        isSelected && isCorrect -> MaterialTheme.colorScheme.primaryContainer
                        isSelected && !isCorrect -> MaterialTheme.colorScheme.errorContainer
                        isCorrect -> MaterialTheme.colorScheme.primaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clickable(enabled = !isFeedbackShowing) {
                            selectedOptIdx = index
                            isFeedbackShowing = true

                            if (isCorrect) {
                                roundScore += (100 / questionsPerRound)
                                voiceAssistant.speak("Correct! Well done!")
                            } else {
                                roundMistakes++
                                totalMistakes++
                                voiceAssistant.speak("The correct answer is ${currentQuestion.options[currentQuestion.correctIndex]}")
                                
                                // Show hint after 2 consecutive mistakes
                                if (roundMistakes >= 2 && !showHint) {
                                    showHint = true
                                }
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
                            text = option,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                        if (isFeedbackShowing) {
                            if (isCorrect) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                            } else if (isSelected) {
                                Icon(Icons.Default.Close, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(32.dp))
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
                    showHint = false
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
            Spacer(Modifier.height(Dimensions.ButtonHeight))
        }
    }
    
    // Round Complete Dialog
    if (showRoundCompleteDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Round Complete! 🎵") },
            text = {
                Column {
                    Text("Great job with music memory!")
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
                    voiceAssistant.speak("Round $currentRound! Let's test your music memory!")
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
                            avgAccuracy >= 90 -> "Excellent! Your music memory is sharp! 🏆"
                            avgAccuracy >= 70 -> "Great job! You know your music well! 👏"
                            avgAccuracy >= 50 -> "Good effort! Keep listening! 💪"
                            else -> "Thank you for playing! Music is good for the soul! 🌸"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showSessionCompleteDialog = false
                    val result = GameResult(
                        gameType = GameType.MUSIC_MEMORY,
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
