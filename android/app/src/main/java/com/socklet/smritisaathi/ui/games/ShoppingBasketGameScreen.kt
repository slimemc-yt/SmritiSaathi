package com.socklet.smritisaathi.ui.games

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager
import kotlinx.coroutines.delay

data class GroceryItem(
    val id: String,
    val name: String,
    val icon: ImageVector,
    val category: String
)

object GroceryItems {
    val allItems = listOf(
        // Fruits
        GroceryItem("1", "Fresh Milk", Icons.Default.LocalDrink, "Dairy"),
        GroceryItem("2", "Sweet Apples", Icons.Default.Eco, "Fruits"),
        GroceryItem("3", "Ripe Bananas", Icons.Default.LunchDining, "Fruits"),
        GroceryItem("4", "Juicy Oranges", Icons.Default.Restaurant, "Fruits"),
        GroceryItem("5", "Fresh Grapes", Icons.Default.Eco, "Fruits"),
        GroceryItem("6", "Sweet Mangoes", Icons.Default.Restaurant, "Fruits"),
        
        // Vegetables
        GroceryItem("7", "Fresh Tomatoes", Icons.Default.Eco, "Vegetables"),
        GroceryItem("8", "Green Spinach", Icons.Default.Eco, "Vegetables"),
        GroceryItem("9", "Potatoes", Icons.Default.Eco, "Vegetables"),
        GroceryItem("10", "Onions", Icons.Default.Eco, "Vegetables"),
        GroceryItem("11", "Carrots", Icons.Default.Eco, "Vegetables"),
        GroceryItem("12", "Cauliflower", Icons.Default.Eco, "Vegetables"),
        
        // Grains
        GroceryItem("13", "Basmati Rice", Icons.Default.RiceBowl, "Grains"),
        GroceryItem("14", "Wheat Flour", Icons.Default.BakeryDining, "Grains"),
        GroceryItem("15", "Bread Loaf", Icons.Default.BakeryDining, "Grains"),
        
        // Beverages
        GroceryItem("16", "Assam Tea", Icons.Default.Coffee, "Beverages"),
        GroceryItem("17", "Coffee Powder", Icons.Default.Coffee, "Beverages"),
        GroceryItem("18", "Fruit Juice", Icons.Default.LocalDrink, "Beverages"),
        
        // Dairy
        GroceryItem("19", "Yogurt", Icons.Default.LocalDrink, "Dairy"),
        GroceryItem("20", "Butter", Icons.Default.Restaurant, "Dairy"),
        GroceryItem("21", "Cheese", Icons.Default.Restaurant, "Dairy"),
        
        // Household
        GroceryItem("22", "Cooking Oil", Icons.Default.LocalDrink, "Household"),
        GroceryItem("23", "Sugar", Icons.Default.Restaurant, "Household"),
        GroceryItem("24", "Salt", Icons.Default.Restaurant, "Household"),
        GroceryItem("25", "Soap", Icons.Default.CleaningServices, "Household"),
        GroceryItem("26", "Toothpaste", Icons.Default.Face, "Household")
    )
}

@Composable
fun ShoppingBasketGameScreen(
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
    var isMemorizingPhase by remember { mutableStateOf(true) }
    var selectedItemIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    var countdownSeconds by remember { mutableStateOf(8) }
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
    
    // Items per round based on difficulty
    val itemCount = when (currentLevel) {
        1 -> 3
        2 -> 4
        3 -> 5
        4 -> 6
        else -> 7
    }
    
    // Get items for this round (avoid repetition)
    val roundItems = remember(currentRound, currentLevel) {
        GroceryItems.allItems.shuffled().take(itemCount + 4) // Extra items for selection
    }
    
    val targetBasket = remember(currentRound) {
        roundItems.take(itemCount)
    }
    
    val shelfItems = remember(currentRound) {
        roundItems.shuffled()
    }

    LaunchedEffect(isMemorizingPhase, currentRound) {
        if (isMemorizingPhase) {
            countdownSeconds = 8
            val names = targetBasket.joinToString(", ") { it.name }
            voiceAssistant.speak("Round $currentRound! Look at your shopping list! Remember: $names.")
            while (countdownSeconds > 0) {
                delay(1000)
                countdownSeconds--
            }
            isMemorizingPhase = false
            voiceAssistant.speak("Now pick the items that were in your shopping basket.")
        }
    }

    fun completeRound() {
        val correctIds = targetBasket.map { it.id }.toSet()
        val correctlyPicked = selectedItemIds.intersect(correctIds).size
        val accuracy = correctlyPicked.toFloat() / itemCount
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
                    text = "Shopping Basket",
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
                if (isMemorizingPhase) {
                    val names = targetBasket.joinToString(", ") { it.name }
                    voiceAssistant.speak("Remember: $names.")
                } else {
                    voiceAssistant.speak("Tap the items you remember from the basket.")
                }
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Instructions", modifier = Modifier.size(32.dp))
            }
        }

        if (isMemorizingPhase) {
            // PHASE 1: Memorization View
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🛒 Your Shopping Basket",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(Dimensions.Space8))
                Text(
                    text = "Remember these $itemCount items:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Time remaining: $countdownSeconds seconds",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(Dimensions.Space24))

                // Display items in a grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Space12),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
                ) {
                    items(targetBasket) { item ->
                        Card(
                            modifier = Modifier.height(110.dp),
                            shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(44.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = { isMemorizingPhase = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.ButtonHeight),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Text("I Remember! Start Shopping ➔", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            // PHASE 2: Recall & Selection View
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = "Tap the $itemCount items from your basket:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = Dimensions.Space8)
                )
                Text(
                    text = "Selected: ${selectedItemIds.size} / $itemCount",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Space12),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
                ) {
                    items(shelfItems) { item ->
                        val isSelected = item.id in selectedItemIds

                        Card(
                            modifier = Modifier
                                .height(110.dp)
                                .clickable {
                                    if (isSelected) {
                                        selectedItemIds = selectedItemIds - item.id
                                    } else {
                                        if (selectedItemIds.size < itemCount) {
                                            selectedItemIds = selectedItemIds + item.id
                                        }
                                    }
                                },
                            shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(3.dp, MaterialTheme.colorScheme.primary) else null
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name,
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(44.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    val correctIds = targetBasket.map { it.id }.toSet()
                    val correctlyPicked = selectedItemIds.intersect(correctIds).size
                    val mistakes = itemCount - correctlyPicked
                    roundMistakes = mistakes
                    totalMistakes += mistakes
                    
                    if (correctlyPicked == itemCount) {
                        voiceAssistant.speak("Wonderful memory! You remembered every item!")
                    } else {
                        voiceAssistant.speak("Good job! You got $correctlyPicked out of $itemCount items right.")
                    }
                    
                    completeRound()
                },
                enabled = selectedItemIds.size == itemCount,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.ButtonHeight),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Text("Checkout Basket 🛍️", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
    
    // Round Complete Dialog
    if (showRoundCompleteDialog) {
        val correctIds = targetBasket.map { it.id }.toSet()
        val correctlyPicked = selectedItemIds.intersect(correctIds).size
        
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Round Complete! 🛒") },
            text = {
                Column {
                    Text("You remembered $correctlyPicked out of $itemCount items!")
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
                    isMemorizingPhase = true
                    selectedItemIds = emptySet()
                    roundMistakes = 0
                    roundStartTime = System.currentTimeMillis()
                    showRoundCompleteDialog = false
                    voiceAssistant.speak("Round $currentRound! Let's test your shopping memory!")
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
                            avgAccuracy >= 70 -> "Great job! You remember well! 👏"
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
                        gameType = GameType.SHOPPING_BASKET,
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
