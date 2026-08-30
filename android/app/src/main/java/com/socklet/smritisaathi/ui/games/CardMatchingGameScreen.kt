package com.socklet.smritisaathi.ui.games

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.GamePerformance
import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

// Card data with category support
data class MemoryCard(
    val id: Int,
    val pairId: Int,
    val icon: ImageVector,
    val label: String,
    val category: String,
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false
)

// Content categories with familiar items
object CardMatchingContent {
    data class CardCategory(
        val name: String,
        val items: List<Pair<ImageVector, String>>
    )
    
    val categories = listOf(
        CardCategory("Fruits", listOf(
            Icons.Default.Restaurant to "Apple",
            Icons.Default.Restaurant to "Banana",
            Icons.Default.Restaurant to "Mango",
            Icons.Default.Restaurant to "Orange",
            Icons.Default.Restaurant to "Grapes",
            Icons.Default.Restaurant to "Watermelon",
            Icons.Default.Restaurant to "Papaya",
            Icons.Default.Restaurant to "Guava",
            Icons.Default.Restaurant to "Pomegranate",
            Icons.Default.Restaurant to "Pineapple"
        )),
        CardCategory("Animals", listOf(
            Icons.Default.Pets to "Cat",
            Icons.Default.Pets to "Dog",
            Icons.Default.Pets to "Cow",
            Icons.Default.Pets to "Elephant",
            Icons.Default.Pets to "Lion",
            Icons.Default.Pets to "Tiger",
            Icons.Default.Pets to "Rabbit",
            Icons.Default.Pets to "Horse",
            Icons.Default.Pets to "Sheep",
            Icons.Default.Pets to "Goat"
        )),
        CardCategory("Flowers", listOf(
            Icons.Default.LocalFlorist to "Rose",
            Icons.Default.LocalFlorist to "Lotus",
            Icons.Default.LocalFlorist to "Sunflower",
            Icons.Default.LocalFlorist to "Jasmine",
            Icons.Default.LocalFlorist to "Marigold",
            Icons.Default.LocalFlorist to "Lily",
            Icons.Default.LocalFlorist to "Orchid",
            Icons.Default.LocalFlorist to "Tulip",
            Icons.Default.LocalFlorist to "Daisy",
            Icons.Default.LocalFlorist to "Hibiscus"
        )),
        CardCategory("Vehicles", listOf(
            Icons.Default.DirectionsCar to "Car",
            Icons.Default.DirectionsBus to "Bus",
            Icons.Default.DirectionsBike to "Bicycle",
            Icons.Default.Train to "Train",
            Icons.Default.AirportShuttle to "Airplane",
            Icons.Default.DirectionsBoat to "Boat",
            Icons.Default.LocalTaxi to "Taxi",
            Icons.Default.TwoWheeler to "Motorcycle",
            Icons.Default.Agriculture to "Tractor",
            Icons.Default.LocalShipping to "Truck"
        )),
        CardCategory("Household", listOf(
            Icons.Default.Coffee to "Cup",
            Icons.Default.Restaurant to "Plate",
            Icons.Default.Kitchen to "Spoon",
            Icons.Default.Chair to "Chair",
            Icons.Default.Dining to "Table",
            Icons.Default.Bed to "Bed",
            Icons.Default.Tv to "Television",
            Icons.Default.Phone to "Phone",
            Icons.Default.Watch to "Clock",
            Icons.Default.Key to "Key"
        )),
        CardCategory("Nature", listOf(
            Icons.Default.WbSunny to "Sun",
            Icons.Default.WaterDrop to "Water",
            Icons.Default.Air to "Air",
            Icons.Default.Terrain to "Mountain",
            Icons.Default.Grass to "Tree",
            Icons.Default.Eco to "Leaf",
            Icons.Default.WbCloudy to "Cloud",
            Icons.Default.Thunderstorm to "Rain",
            Icons.Default.Star to "Star",
            Icons.Default.NightsStay to "Moon"
        )),
        CardCategory("Family", listOf(
            Icons.Default.Person to "Mother",
            Icons.Default.Person to "Father",
            Icons.Default.Person to "Child",
            Icons.Default.Person to "Grandmother",
            Icons.Default.Person to "Grandfather",
            Icons.Default.Person to "Sister",
            Icons.Default.Person to "Brother",
            Icons.Default.Person to "Uncle",
            Icons.Default.Person to "Aunt",
            Icons.Default.Person to "Friend"
        )),
        CardCategory("Colors", listOf(
            Icons.Default.Palette to "Red",
            Icons.Default.Palette to "Blue",
            Icons.Default.Palette to "Green",
            Icons.Default.Palette to "Yellow",
            Icons.Default.Palette to "Orange",
            Icons.Default.Palette to "Purple",
            Icons.Default.Palette to "Pink",
            Icons.Default.Palette to "White",
            Icons.Default.Palette to "Black",
            Icons.Default.Palette to "Brown"
        ))
    )
}

@Composable
fun CardMatchingGameScreen(
    difficultyLevel: Int,
    voiceAssistant: VoiceAssistantManager,
    onComplete: (GameResult) -> Unit,
    onDistressTriggered: () -> Unit,
    onExit: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    
    // Session state
    var currentLevel by remember { mutableStateOf(difficultyLevel) }
    var currentRound by remember { mutableStateOf(1) }
    val totalRounds = 3 // 3 rounds per session
    
    // Game state
    var cards by remember { mutableStateOf(listOf<MemoryCard>()) }
    var flippedCards by remember { mutableStateOf<List<Int>>(emptyList()) }
    var matchesFound by remember { mutableStateOf(0) }
    var totalMistakes by remember { mutableStateOf(0) }
    var consecutiveMistakes by remember { mutableStateOf(0) }
    var isProcessing by remember { mutableStateOf(false) }
    var showHint by remember { mutableStateOf(false) }
    var hintPairId by remember { mutableStateOf<Int?>(null) }
    
    // Performance tracking
    var roundScores by remember { mutableStateOf(listOf<Int>()) }
    var roundAccuracies by remember { mutableStateOf(listOf<Float>()) }
    val startTime = remember { System.currentTimeMillis() }
    var roundStartTime by remember { mutableStateOf(System.currentTimeMillis()) }
    
    // Category tracking to avoid repetition
    var usedCategories by remember { mutableStateOf(listOf<String>()) }
    
    // Dialogs
    var showRoundCompleteDialog by remember { mutableStateOf(false) }
    var showSessionCompleteDialog by remember { mutableStateOf(false) }
    
    fun initializeRound() {
        // Select category (avoid recent categories)
        val availableCategories = CardMatchingContent.categories.filter { it.name !in usedCategories.takeLast(3) }
        val category = availableCategories.randomOrNull() ?: CardMatchingContent.categories.random()
        usedCategories = usedCategories + category.name
        
        // Determine number of pairs based on level
        val numPairs = when (currentLevel) {
            1 -> 4  // 8 cards
            2 -> 5  // 10 cards
            3 -> 6  // 12 cards
            4 -> 7  // 14 cards
            else -> 8  // 16 cards
        }
        
        // Create cards
        val selectedItems = category.items.shuffled().take(numPairs)
        val deck = mutableListOf<MemoryCard>()
        var idCounter = 0
        
        selectedItems.forEachIndexed { pairIndex, (icon, label) ->
            deck.add(MemoryCard(id = idCounter++, pairId = pairIndex, icon = icon, label = label, category = category.name))
            deck.add(MemoryCard(id = idCounter++, pairId = pairIndex, icon = icon, label = label, category = category.name))
        }
        
        cards = deck.shuffled()
        flippedCards = emptyList()
        matchesFound = 0
        isProcessing = false
        showHint = false
        hintPairId = null
        roundStartTime = System.currentTimeMillis()
    }
    
    // Initialize first round
    LaunchedEffect(Unit) {
        initializeRound()
        voiceAssistant.speak("Find the matching pairs! Tap two cards to turn them over.")
    }
    
    fun completeRound() {
        val roundTime = (System.currentTimeMillis() - roundStartTime) / 1000
        val numPairs = cards.size / 2
        val score = ((numPairs.toFloat() / (numPairs + totalMistakes)) * 100).toInt().coerceIn(40, 100)
        val accuracy = (numPairs.toFloat() / (numPairs + totalMistakes)).coerceIn(0f, 1f)
        
        roundScores = roundScores + score
        roundAccuracies = roundAccuracies + accuracy
        
        if (currentRound >= totalRounds) {
            // Session complete
            showSessionCompleteDialog = true
        } else {
            // Round complete, show dialog
            showRoundCompleteDialog = true
        }
    }
    
    fun onCardClick(cardId: Int) {
        if (isProcessing) return
        
        val card = cards.find { it.id == cardId } ?: return
        if (card.isFaceUp || card.isMatched) return
        
        // Flip the card
        cards = cards.map { if (it.id == cardId) it.copy(isFaceUp = true) else it }
        flippedCards = flippedCards + cardId
        
        // Check for match
        if (flippedCards.size == 2) {
            isProcessing = true
            val firstCard = cards.find { it.id == flippedCards[0] }
            val secondCard = cards.find { it.id == flippedCards[1] }
            
            coroutineScope.launch {
                delay(800) // Show cards briefly
                
                if (firstCard?.pairId == secondCard?.pairId) {
                    // Match found!
                    cards = cards.map { 
                        if (it.id in flippedCards) it.copy(isMatched = true) else it 
                    }
                    matchesFound++
                    consecutiveMistakes = 0
                    voiceAssistant.speak("Great match! ${firstCard?.label}")
                    
                    // Check if round complete
                    if (matchesFound == cards.size / 2) {
                        delay(500)
                        completeRound()
                    }
                } else {
                    // No match
                    cards = cards.map { 
                        if (it.id in flippedCards) it.copy(isFaceUp = false) else it 
                    }
                    totalMistakes++
                    consecutiveMistakes++
                    
                    // Offer hint after 3 consecutive mistakes
                    if (consecutiveMistakes >= 3 && !showHint) {
                        showHint = true
                        hintPairId = cards.first { !it.isMatched }.pairId
                        voiceAssistant.speak("Here's a hint! Look for the matching pair.")
                    }
                }
                
                flippedCards = emptyList()
                isProcessing = false
            }
        }
    }
    
    fun startNextRound() {
        currentRound++
        currentLevel = when {
            roundAccuracies.takeLast(2).average() >= 0.85 -> (currentLevel + 1).coerceAtMost(5)
            roundAccuracies.takeLast(2).average() < 0.5 -> (currentLevel - 1).coerceAtLeast(1)
            else -> currentLevel
        }
        showRoundCompleteDialog = false
        initializeRound()
        voiceAssistant.speak("Round $currentRound! Let's find the matches!")
    }
    
    fun endSession() {
        showSessionCompleteDialog = false
        val totalTime = (System.currentTimeMillis() - startTime) / 1000
        val avgScore = roundScores.average().toInt()
        val avgAccuracy = roundAccuracies.average().toFloat()
        
        // Create performance record
        val performance = GamePerformance(
            patientId = "", // Will be set by caller
            gameType = GameType.CARD_MATCHING.name,
            score = avgScore,
            maxScore = 100,
            timeSpentSeconds = totalTime.toInt(),
            difficultyLevel = currentLevel,
            accuracy = avgAccuracy,
            mistakes = totalMistakes,
            completedAt = Date(),
            metadata = mapOf(
                "rounds" to currentRound,
                "categories" to usedCategories,
                "roundScores" to roundScores
            )
        )
        
        val result = GameResult(
            gameType = GameType.CARD_MATCHING,
            score = avgScore,
            difficultyLevel = currentLevel,
            responseTimeMs = totalTime,
            mistakesCount = totalMistakes,
            completedSuccessfully = true
        )
        
        voiceAssistant.speak("Wonderful! You completed $currentRound rounds! Your memory is getting stronger!")
        coroutineScope.launch {
            delay(1500)
            onComplete(result)
        }
    }
    
    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimensions.ScreenPadding)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Memory Match",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Round $currentRound of $totalRounds • Level $currentLevel",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(onClick = onExit) {
                Icon(Icons.Default.Close, contentDescription = "Exit")
            }
        }
        
        Spacer(modifier = Modifier.height(Dimensions.Space16))
        
        // Progress
        LinearProgressIndicator(
            progress = matchesFound.toFloat() / (cards.size / 2),
            modifier = Modifier.fillMaxWidth(),
        )
        
        Text(
            text = "Matches: $matchesFound / ${cards.size / 2}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
        
        Spacer(modifier = Modifier.height(Dimensions.Space16))
        
        // Card Grid
        val columns = when (cards.size) {
            8 -> 4
            10 -> 5
            12 -> 4
            14 -> 7
            else -> 4
        }
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cards, key = { it.id }) { card ->
                MemoryCardView(
                    card = card,
                    isHinted = hintPairId == card.pairId && showHint,
                    onClick = { onCardClick(card.id) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(Dimensions.Space16))
        
        // Distress button
        TextButton(
            onClick = onDistressTriggered,
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Help, contentDescription = null)
            Spacer(Modifier.width(4.dp))
            Text("I need help")
        }
    }
    
    // Round Complete Dialog
    if (showRoundCompleteDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Round Complete! 🎉") },
            text = {
                Column {
                    Text("Great job! You found all the matches!")
                    Spacer(Modifier.height(8.dp))
                    Text("Score: ${roundScores.last()}%")
                    Text("Accuracy: ${(roundAccuracies.last() * 100).toInt()}%")
                    Spacer(Modifier.height(8.dp))
                    Text("Ready for Round ${currentRound + 1}?")
                }
            },
            confirmButton = {
                Button(onClick = { startNextRound() }) {
                    Text("Next Round")
                }
            }
        )
    }
    
    // Session Complete Dialog
    if (showSessionCompleteDialog) {
        val avgScore = roundScores.average().toInt()
        val avgAccuracy = (roundAccuracies.average() * 100).toInt()
        
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
                            avgAccuracy >= 90 -> "Excellent! Your memory is very sharp! 🏆"
                            avgAccuracy >= 70 -> "Great job! You're doing wonderfully! 👏"
                            avgAccuracy >= 50 -> "Good effort! Keep practicing! 💪"
                            else -> "Thank you for playing! Every bit helps! 🌸"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            confirmButton = {
                Button(onClick = { endSession() }) {
                    Text("Finish")
                }
            }
        )
    }
}

@Composable
fun MemoryCardView(
    card: MemoryCard,
    isHinted: Boolean,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (card.isFaceUp || card.isMatched) 180f else 0f,
        animationSpec = tween(durationMillis = 300)
    )
    
    Card(
        modifier = Modifier
            .aspectRatio(0.75f)
            .graphicsLayer { rotationY = rotation }
            .clickable(enabled = !card.isMatched, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                card.isMatched -> MaterialTheme.colorScheme.primaryContainer
                isHinted -> MaterialTheme.colorScheme.tertiaryContainer
                card.isFaceUp -> MaterialTheme.colorScheme.surface
                else -> MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (card.isFaceUp || card.isMatched) {
                // Front of card (visible when flipped)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.graphicsLayer { rotationY = 180f } // Counter-rotate text
                ) {
                    Icon(
                        imageVector = card.icon,
                        contentDescription = card.label,
                        modifier = Modifier.size(32.dp),
                        tint = if (card.isMatched) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = card.label,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            } else {
                // Back of card
                Icon(
                    imageVector = Icons.Default.QuestionMark,
                    contentDescription = "Hidden card",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
