package com.socklet.smritisaathi.ui.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

data class NERFactCard(
    val id: String,
    val state: String,
    val landmarkName: String,
    val description: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val icon: ImageVector,
    val category: String
)

object NERFacts {
    val allFacts = listOf(
        // Assam
        NERFactCard("n1", "Assam", "Kaziranga National Park",
            "World famous home of the Great One-Horned Rhinoceros & scenic tea gardens.",
            "Which famous animal is Kaziranga known for protecting?",
            listOf("One-Horned Rhinoceros", "Royal Bengal Tiger", "Snow Leopard"), 0,
            Icons.Default.Pets, "Wildlife"),
        NERFactCard("n2", "Assam", "Bihu Festival",
            "The vibrant harvest festival celebrated with joy, dhol, pepa, and graceful dance.",
            "What musical horn is played during Bihu celebrations?",
            listOf("Pepa (Buffalo Horn)", "Sitar", "Flute"), 0,
            Icons.Default.Celebration, "Festival"),
        NERFactCard("n3", "Assam", "Majuli River Island",
            "The world's largest river island, formed by the Brahmaputra River.",
            "Which river forms the mighty Majuli Island?",
            listOf("Brahmaputra", "Ganges", "Yamuna"), 0,
            Icons.Default.Water, "Geography"),
            
        // Meghalaya
        NERFactCard("n4", "Meghalaya", "Living Root Bridges of Cherrapunji",
            "Incredible bio-engineering bridges handmade from aerial roots of rubber trees.",
            "In which beautiful hill state are Living Root Bridges found?",
            listOf("Meghalaya", "Rajasthan", "Gujarat"), 0,
            Icons.Default.Forest, "Architecture"),
        NERFactCard("n5", "Meghalaya", "Wettest Place on Earth",
            "Mawsynram receives the highest rainfall in the world.",
            "Which village receives the highest rainfall in the world?",
            listOf("Mawsynram", "Desert", "Mountain"), 0,
            Icons.Default.Water, "Geography"),
            
        // Nagaland
        NERFactCard("n6", "Nagaland", "Hornbill Festival of Kisama",
            "The 'Festival of Festivals' showcasing rich Naga tribal traditions and music.",
            "Which bird is the famous Nagaland cultural festival named after?",
            listOf("Great Hornbill", "Peacock", "Eagle"), 0,
            Icons.Default.Festival, "Festival"),
        NERFactCard("n7", "Nagaland", "Naga Shawls",
            "Each Naga tribe has distinct shawl patterns indicating their identity.",
            "What traditional textile indicates Naga tribal identity?",
            listOf("Shawls", "Sarees", "Turbans"), 0,
            Icons.Default.Checkroom, "Culture"),
            
        // Manipur
        NERFactCard("n8", "Manipur", "Loktak Lake & Phumdis",
            "The world's only floating national park (Keibul Lamjao) with the Sangai deer.",
            "What is the unique dancing deer of Loktak Lake called?",
            listOf("Sangai Deer", "Chital", "Blackbuck"), 0,
            Icons.Default.Water, "Wildlife"),
        NERFactCard("n9", "Manipur", "Manipuri Dance",
            "Classical dance form known for graceful movements and spiritual themes.",
            "Which classical dance originates from Manipur?",
            listOf("Manipuri", "Bharatanatyam", "Kathak"), 0,
            Icons.Default.MusicNote, "Dance"),
            
        // Mizoram
        NERFactCard("n10", "Mizoram", "Cheraw (Bamboo Dance)",
            "Traditional rhythmic dance where dancers step gracefully between bamboo sticks.",
            "What natural material is used to create the rhythm in Cheraw dance?",
            listOf("Bamboo Poles", "Metal Bells", "Clay Pots"), 0,
            Icons.Default.MusicNote, "Dance"),
        NERFactCard("n11", "Mizoram", "Mizo Puan",
            "Traditional Mizo textile with distinctive patterns and colors.",
            "What is the traditional Mizo cloth called?",
            listOf("Puan", "Saree", "Dhoti"), 0,
            Icons.Default.Checkroom, "Culture"),
            
        // Tripura
        NERFactCard("n12", "Tripura", "Ujjayanta Palace",
            "Beautiful royal palace now serving as the State Museum.",
            "What is the famous royal palace in Tripura called?",
            listOf("Ujjayanta Palace", "Red Fort", "Taj Mahal"), 0,
            Icons.Default.AccountBalance, "Architecture"),
        NERFactCard("n13", "Tripura", "Garia Puja",
            "Major festival celebrating agriculture and livestock.",
            "What does Garia Puja celebrate?",
            listOf("Agriculture", "Rain", "Harvest"), 0,
            Icons.Default.Agriculture, "Festival"),
            
        // Arunachal Pradesh
        NERFactCard("n14", "Arunachal Pradesh", "Tawang Monastery",
            "The largest monastery in India and second largest in the world.",
            "Which famous monastery is in Arunachal Pradesh?",
            listOf("Tawang Monastery", "Golden Temple", "Meenakshi Temple"), 0,
            Icons.Default.TempleBuddhist, "Religion"),
        NERFactCard("n15", "Arunachal Pradesh", "Dong Festival",
            "Celebrated by the Digaru Mishmi tribe with prayers for wealth.",
            "Which tribe celebrates the Dong festival?",
            listOf("Digaru Mishmi", "Naga", "Mizo"), 0,
            Icons.Default.Celebration, "Festival"),
            
        // Sikkim
        NERFactCard("n16", "Sikkim", "Kanchenjunga Peak",
            "The third highest mountain in the world, sacred to Sikkimese people.",
            "Which sacred mountain is in Sikkim?",
            listOf("Kanchenjunga", "Everest", "K2"), 0,
            Icons.Default.Terrain, "Geography"),
        NERFactCard("n17", "Sikkim", "Losar Festival",
            "Tibetan New Year celebrated with mask dances and prayers.",
            "What does Losar festival celebrate?",
            listOf("New Year", "Harvest", "Monsoon"), 0,
            Icons.Default.Celebration, "Festival"),
            
        // General NE
        NERFactCard("n18", "North East", "Eight Sisters",
            "The eight states of North-East India are collectively called Eight Sisters.",
            "How many states are in North-East India?",
            listOf("8", "7", "6"), 0,
            Icons.Default.Map, "Geography"),
        NERFactCard("n19", "North East", "Bamboo Products",
            "North-East India is rich in bamboo crafts and products.",
            "What natural material is abundant in North-East crafts?",
            listOf("Bamboo", "Stone", "Metal"), 0,
            Icons.Default.Forest, "Crafts"),
        NERFactCard("n20", "North East", "Organic Farming",
            "Sikkim is the world's first fully organic state.",
            "Which NE state is fully organic?",
            listOf("Sikkim", "Assam", "Meghalaya"), 0,
            Icons.Default.Eco, "Agriculture")
    )
}

@Composable
fun NERFamiliarImagesGameScreen(
    patientState: String = "Assam",
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
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
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
        NERFacts.allFacts.shuffled().take(questionsPerRound)
    }
    
    val currentCard = roundQuestions.getOrNull(currentQuestionIndex)

    LaunchedEffect(currentQuestionIndex) {
        currentCard?.let {
            voiceAssistant.speak("${it.landmarkName}. ${it.question}")
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
                    text = "NE Heritage",
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
                currentCard?.let { voiceAssistant.speak("${it.landmarkName}. ${it.question}") }
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Hear Question", modifier = Modifier.size(32.dp))
            }
        }

        // Cultural Showcase Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(Dimensions.CardCornerRadius)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Space16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = currentCard?.icon ?: Icons.Default.Map,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.Space8))

                Text(
                    text = currentCard?.landmarkName ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Region: ${currentCard?.state ?: ""}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(Dimensions.Space8))
                Text(
                    text = currentCard?.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Question Prompt
        Text(
            text = currentCard?.question ?: "",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = Dimensions.Space8)
        )

        // Multiple Choices
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space8)
        ) {
            currentCard?.options?.forEachIndexed { optIndex, optionText ->
                val isSelected = selectedOptionIndex == optIndex
                val isCorrect = optIndex == currentCard.correctIndex

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
                            selectedOptionIndex = optIndex
                            isFeedbackShowing = true

                            if (isCorrect) {
                                roundScore += (100 / questionsPerRound)
                                voiceAssistant.speak("Excellent! That is absolutely right.")
                            } else {
                                roundMistakes++
                                totalMistakes++
                                voiceAssistant.speak("Good try! The answer is ${currentCard.options[currentCard.correctIndex]}.")
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
                            style = MaterialTheme.typography.bodyLarge,
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
                    selectedOptionIndex = null
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
                    text = if (currentQuestionIndex + 1 < questionsPerRound) "Next Question ➔" else "Complete Round 🌟",
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
            title = { Text("Round Complete! 🌟") },
            text = {
                Column {
                    Text("Great job learning about North-East heritage!")
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
                    voiceAssistant.speak("Round $currentRound! Let's explore more heritage!")
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
            title = { Text("Heritage Tour Complete! 🌟") },
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
                            avgAccuracy >= 90 -> "Excellent! You know NE heritage well! 🏆"
                            avgAccuracy >= 70 -> "Great job! You're learning fast! 👏"
                            avgAccuracy >= 50 -> "Good effort! Keep exploring! 💪"
                            else -> "Thank you for exploring! Heritage is precious! 🌸"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showSessionCompleteDialog = false
                    val result = GameResult(
                        gameType = GameType.NER_FAMILIAR_IMAGES,
                        score = avgScore,
                        difficultyLevel = currentLevel,
                        responseTimeMs = totalTime,
                        mistakesCount = totalMistakes,
                        completedSuccessfully = true
                    )
                    voiceAssistant.speak("Wonderful! You completed the heritage tour! Your knowledge is growing!")
                    onComplete(result)
                }) {
                    Text("Finish")
                }
            }
        )
    }
}
