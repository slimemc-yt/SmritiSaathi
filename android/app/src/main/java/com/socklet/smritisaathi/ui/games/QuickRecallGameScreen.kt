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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

data class StoryPrompt(
    val id: String,
    val story: String,
    val questions: List<RecallQuestion>,
    val category: String
)

data class RecallQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)

object QuickRecallStories {
    val allStories = listOf(
        // Daily activities
        StoryPrompt("qr1", 
            "Ananya went to Guwahati market on Wednesday morning and bought three juicy mangoes for grandfather.",
            listOf(
                RecallQuestion("Where did Ananya go?", listOf("Guwahati Market", "Dispur Library", "Shillong Hills"), 0),
                RecallQuestion("Which fruit did she buy?", listOf("Juicy Mangoes", "Sweet Oranges", "Red Apples"), 0),
                RecallQuestion("On which day did she visit?", listOf("Wednesday", "Sunday", "Friday"), 0)
            ), "Daily"),
        StoryPrompt("qr2",
            "Grandmother planted yellow marigold flowers in the garden next to the tea table yesterday afternoon.",
            listOf(
                RecallQuestion("What color flowers were planted?", listOf("Yellow Marigolds", "Red Roses", "White Lilies"), 0),
                RecallQuestion("Where were the flowers planted?", listOf("In the Garden", "On the Balcony", "By the Road"), 0),
                RecallQuestion("When did she plant them?", listOf("Yesterday", "Last week", "Today"), 0)
            ), "Daily"),
        StoryPrompt("qr3",
            "Rajesh cooked rice and dal for lunch. He added salt and turmeric to the dal. The family ate together at noon.",
            listOf(
                RecallQuestion("What did Rajesh cook?", listOf("Rice and Dal", "Bread and Butter", "Roti and Sabzi"), 0),
                RecallQuestion("What spices did he add?", listOf("Salt and Turmeric", "Sugar and Pepper", "Chili and Ginger"), 0),
                RecallQuestion("When did the family eat?", listOf("At Noon", "In the Morning", "At Night"), 0)
            ), "Daily"),
            
        // Family
        StoryPrompt("qr4",
            "Sunita's daughter Priya scored 95 marks in her mathematics exam. She received a new bicycle as reward from her parents.",
            listOf(
                RecallQuestion("Who scored 95 marks?", listOf("Priya", "Sunita", "Rajesh"), 0),
                RecallQuestion("In which subject?", listOf("Mathematics", "Science", "English"), 0),
                RecallQuestion("What was the reward?", listOf("New Bicycle", "New Book", "New Phone"), 0)
            ), "Family"),
        StoryPrompt("qr5",
            "Grandfather told stories about his village to his grandchildren every evening. They sat under the banyan tree and listened.",
            listOf(
                RecallQuestion("Who told stories?", listOf("Grandfather", "Grandmother", "Father"), 0),
                RecallQuestion("When did he tell stories?", listOf("Every Evening", "Every Morning", "Every Night"), 0),
                RecallQuestion("Where did they sit?", listOf("Under Banyan Tree", "In the House", "On the Bed"), 0)
            ), "Family"),
        StoryPrompt("qr6",
            "Meera's son Aarav learned to ride a bicycle last summer. He practiced in the park near their house with his father.",
            listOf(
                RecallQuestion("Who learned to ride?", listOf("Aarav", "Meera", "Priya"), 0),
                RecallQuestion("What did he learn?", listOf("Ride Bicycle", "Swim", "Drive Car"), 0),
                RecallQuestion("Where did he practice?", listOf("In the Park", "On the Road", "In the Garden"), 0)
            ), "Family"),
            
        // Festivals
        StoryPrompt("qr7",
            "During Bihu festival, the family wore traditional mekhela chador and ate pitha. They danced to dhol and pepa music.",
            listOf(
                RecallQuestion("Which festival was it?", listOf("Bihu", "Diwali", "Holi"), 0),
                RecallQuestion("What did they wear?", listOf("Mekhela Chador", "Saree", "Salwar Kameez"), 0),
                RecallQuestion("What did they eat?", listOf("Pitha", "Ladoo", "Biryani"), 0)
            ), "Festival"),
        StoryPrompt("qr8",
            "At Diwali, the house was decorated with lights and rangoli. Children burst fireworks and ate sweets.",
            listOf(
                RecallQuestion("How was the house decorated?", listOf("Lights and Rangoli", "Flowers Only", "Balloons"), 0),
                RecallQuestion("What did children burst?", listOf("Fireworks", "Balloons", "Bubbles"), 0),
                RecallQuestion("What did they eat?", listOf("Sweets", "Fruits", "Vegetables"), 0)
            ), "Festival"),
        StoryPrompt("qr9",
            "During Christmas, the church was decorated with stars. The choir sang carols and distributed cakes to everyone.",
            listOf(
                RecallQuestion("Where was decorated?", listOf("Church", "Temple", "Mosque"), 0),
                RecallQuestion("What did the choir do?", listOf("Sing Carols", "Play Drums", "Dance"), 0),
                RecallQuestion("What was distributed?", listOf("Cakes", "Fruits", "Flowers"), 0)
            ), "Festival"),
            
        // Travel
        StoryPrompt("qr10",
            "The family traveled to Shillong by car last month. They stayed at a hotel near Ward's Lake for three days.",
            listOf(
                RecallQuestion("Where did they travel?", listOf("Shillong", "Guwahati", "Kolkata"), 0),
                RecallQuestion("How did they travel?", listOf("By Car", "By Train", "By Bus"), 0),
                RecallQuestion("How long did they stay?", listOf("Three Days", "One Week", "One Day"), 0)
            ), "Travel"),
        StoryPrompt("qr11",
            "Rajesh visited Kaziranga National Park with his school. He saw one-horned rhinoceros and wild elephants.",
            listOf(
                RecallQuestion("Where did Rajesh visit?", listOf("Kaziranga", "Sundarbans", "Jim Corbett"), 0),
                RecallQuestion("Who did he visit with?", listOf("His School", "His Family", "His Friends"), 0),
                RecallQuestion("What animals did he see?", listOf("Rhinoceros and Elephants", "Tigers and Lions", "Deer and Birds"), 0)
            ), "Travel"),
        StoryPrompt("qr12",
            "The grandmother went to the temple on Saturday morning. She offered flowers and prayed for the family's wellbeing.",
            listOf(
                RecallQuestion("Where did grandmother go?", listOf("Temple", "Market", "Park"), 0),
                RecallQuestion("When did she go?", listOf("Saturday Morning", "Sunday Evening", "Friday Night"), 0),
                RecallQuestion("What did she offer?", listOf("Flowers", "Fruits", "Sweets"), 0)
            ), "Daily"),
            
        // Health
        StoryPrompt("qr13",
            "Doctor Baruah checked grandfather's blood pressure. It was normal. He prescribed morning walk and light food.",
            listOf(
                RecallQuestion("Who checked blood pressure?", listOf("Doctor Baruah", "Nurse Priya", "Dr. Singh"), 0),
                RecallQuestion("What was the result?", listOf("Normal", "High", "Low"), 0),
                RecallQuestion("What was prescribed?", listOf("Morning Walk", "Medicine", "Rest"), 0)
            ), "Health"),
        StoryPrompt("qr14",
            "Grandmother takes her medicines after breakfast every day. Her daughter reminds her and keeps the medicines ready.",
            listOf(
                RecallQuestion("When does she take medicines?", listOf("After Breakfast", "Before Sleep", "After Lunch"), 0),
                RecallQuestion("Who reminds her?", listOf("Her Daughter", "Her Son", "Her Grandson"), 0),
                RecallQuestion("What does the daughter do?", listOf("Keeps Medicines Ready", "Cooks Food", "Cleans House"), 0)
            ), "Health"),
            
        // Education
        StoryPrompt("qr15",
            "Priya studies in class 8. She likes mathematics and science. Her school starts at 9 AM and ends at 3 PM.",
            listOf(
                RecallQuestion("Which class is Priya in?", listOf("Class 8", "Class 10", "Class 5"), 0),
                RecallQuestion("What subjects does she like?", listOf("Math and Science", "History and Geography", "English and Art"), 0),
                RecallQuestion("When does school end?", listOf("3 PM", "1 PM", "5 PM"), 0)
            ), "Education"),
        StoryPrompt("qr16",
            "Aarav learned to ride a bicycle last summer. He practiced in the park near their house with his father.",
            listOf(
                RecallQuestion("What did Aarav learn?", listOf("Ride Bicycle", "Swim", "Drive Car"), 0),
                RecallQuestion("When did he learn?", listOf("Last Summer", "Last Winter", "Last Month"), 0),
                RecallQuestion("Who helped him?", listOf("His Father", "His Mother", "His Brother"), 0)
            ), "Education"),
            
        // Nature
        StoryPrompt("qr17",
            "It rained heavily in Assam during July. The Brahmaputra river flooded many villages. People moved to relief camps.",
            listOf(
                RecallQuestion("When did it rain?", listOf("July", "March", "December"), 0),
                RecallQuestion("Which river flooded?", listOf("Brahmaputra", "Ganges", "Yamuna"), 0),
                RecallQuestion("Where did people go?", listOf("Relief Camps", "Hotels", "Cities"), 0)
            ), "Nature"),
        StoryPrompt("qr18",
            "The garden has many fruit trees: mango, jackfruit, and banana. Grandmother waters them every evening.",
            listOf(
                RecallQuestion("What trees are in the garden?", listOf("Mango, Jackfruit, Banana", "Apple, Orange, Grape"), 0),
                RecallQuestion("Who waters them?", listOf("Grandmother", "Grandfather", "Father"), 0),
                RecallQuestion("When does she water?", listOf("Every Evening", "Every Morning", "Every Night"), 0)
            ), "Nature"),
        StoryPrompt("qr19",
            "The peacock danced in the field after the first rain. Everyone came out to watch the beautiful dance.",
            listOf(
                RecallQuestion("Which bird danced?", listOf("Peacock", "Parrot", "Sparrow"), 0),
                RecallQuestion("When did it dance?", listOf("After First Rain", "In the Morning", "At Night"), 0),
                RecallQuestion("What did everyone do?", listOf("Watched the Dance", "Went Inside", "Slept"), 0)
            ), "Nature"),
        StoryPrompt("qr20",
            "The farmer planted rice seedlings in the wet field. He worked from morning until sunset. The field looked green.",
            listOf(
                RecallQuestion("What did the farmer plant?", listOf("Rice Seedlings", "Wheat Seeds", "Vegetables"), 0),
                RecallQuestion("How long did he work?", listOf("Morning to Sunset", "One Hour", "All Night"), 0),
                RecallQuestion("What color was the field?", listOf("Green", "Brown", "Yellow"), 0)
            ), "Nature")
    )
}

@Composable
fun QuickRecallGameScreen(
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
    var isStoryPhase by remember { mutableStateOf(true) }
    var currentQuestionIdx by remember { mutableStateOf(0) }
    var roundScore by remember { mutableStateOf(0) }
    var roundMistakes by remember { mutableStateOf(0) }
    var totalMistakes by remember { mutableStateOf(0) }
    val startTime = remember { System.currentTimeMillis() }
    var roundStartTime by remember { mutableStateOf(System.currentTimeMillis()) }
    
    // Performance tracking
    var roundScores by remember { mutableStateOf(listOf<Int>()) }
    var roundAccuracies by remember { mutableStateOf(listOf<Float>()) }
    
    // UI state
    var selectedOptionIdx by remember { mutableStateOf<Int?>(null) }
    var isFeedbackShowing by remember { mutableStateOf(false) }
    
    // Dialogs
    var showRoundCompleteDialog by remember { mutableStateOf(false) }
    var showSessionCompleteDialog by remember { mutableStateOf(false) }
    
    // Questions per story based on difficulty
    val questionsPerStory = when (currentLevel) {
        1 -> 2
        2 -> 3
        else -> 3
    }
    
    // Get story for this round (avoid repetition)
    val currentStory = remember(currentRound, currentLevel) {
        QuickRecallStories.allStories.shuffled().first()
    }
    
    val totalQuestions = questionsPerStory.coerceAtMost(currentStory.questions.size)

    LaunchedEffect(isStoryPhase) {
        if (isStoryPhase) {
            voiceAssistant.speak("Round $currentRound. Listen carefully to this short story: ${currentStory.story}")
        }
    }

    LaunchedEffect(currentQuestionIdx, isStoryPhase) {
        if (!isStoryPhase && currentQuestionIdx < totalQuestions) {
            val q = currentStory.questions[currentQuestionIdx]
            voiceAssistant.speak(q.question)
        }
    }

    fun completeRound() {
        val accuracy = (totalQuestions - roundMistakes).toFloat() / totalQuestions
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
                    text = "Quick Recall",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Round $currentRound of $totalRounds",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = {
                if (isStoryPhase) {
                    voiceAssistant.speak(currentStory.story)
                } else if (currentQuestionIdx < totalQuestions) {
                    val q = currentStory.questions[currentQuestionIdx]
                    voiceAssistant.speak(q.question)
                }
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Read Aloud", modifier = Modifier.size(32.dp))
            }
        }

        if (isStoryPhase) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = Dimensions.Space24),
                shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimensions.Space24),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(Dimensions.Space16))
                    Text(
                        text = currentStory.story,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                }
            }

            Button(
                onClick = { isStoryPhase = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.ButtonHeight),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Text("I'm Ready for Questions ➔", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            val q = currentStory.questions[currentQuestionIdx]

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Question ${currentQuestionIdx + 1}/$totalQuestions",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimensions.Space8))
                Text(
                    text = q.question,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Dimensions.Space24))

                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)) {
                    q.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedOptionIdx == optIndex
                        val isCorrect = optIndex == q.correctIndex

                        val bgColor = if (isFeedbackShowing && isSelected) {
                            if (isCorrect) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .clickable(enabled = !isFeedbackShowing) {
                                    selectedOptionIdx = optIndex
                                    isFeedbackShowing = true

                                    if (isCorrect) {
                                        roundScore += (100 / totalQuestions)
                                        voiceAssistant.speak("Spot on! That is correct.")
                                    } else {
                                        roundMistakes++
                                        totalMistakes++
                                        voiceAssistant.speak("That's okay! The right answer is ${q.options[q.correctIndex]}.")
                                    }
                                },
                            shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                            colors = CardDefaults.cardColors(containerColor = bgColor),
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
            }

            if (isFeedbackShowing) {
                Button(
                    onClick = {
                        isFeedbackShowing = false
                        selectedOptionIdx = null
                        if (currentQuestionIdx + 1 < totalQuestions) {
                            currentQuestionIdx++
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
                        text = if (currentQuestionIdx + 1 < totalQuestions) "Next Question ➔" else "Complete Round 🎉",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(Dimensions.ButtonHeight))
            }
        }
    }
    
    // Round Complete Dialog
    if (showRoundCompleteDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Round Complete! 📚") },
            text = {
                Column {
                    Text("Great job recalling the story!")
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
                    isStoryPhase = true
                    currentQuestionIdx = 0
                    roundScore = 0
                    roundMistakes = 0
                    roundStartTime = System.currentTimeMillis()
                    showRoundCompleteDialog = false
                    voiceAssistant.speak("Round $currentRound! Let's test your recall!")
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
                            avgAccuracy >= 90 -> "Excellent! Your memory is sharp! 🏆"
                            avgAccuracy >= 70 -> "Great job! You recall well! 👏"
                            avgAccuracy >= 50 -> "Good effort! Keep practicing! 💪"
                            else -> "Thank you for playing! Memory improves with practice! 🌸"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showSessionCompleteDialog = false
                    val result = GameResult(
                        gameType = GameType.QUICK_RECALL,
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
