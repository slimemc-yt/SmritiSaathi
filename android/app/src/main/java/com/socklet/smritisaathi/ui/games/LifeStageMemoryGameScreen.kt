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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.domain.model.LifeStage
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

data class LifeStagePrompt(
    val id: String,
    val stage: LifeStage,
    val icon: ImageVector,
    val prompt: String,
    val category: String
)

object LifeStagePrompts {
    val allPrompts = listOf(
        // Childhood
        LifeStagePrompt("ls1", LifeStage.CHILDHOOD, Icons.Default.ChildCare, 
            "Think about your favorite childhood game or sweet treat in your village or hometown.", "Childhood"),
        LifeStagePrompt("ls2", LifeStage.CHILDHOOD, Icons.Default.ChildCare,
            "Remember the songs you used to sing with friends during childhood festivals.", "Childhood"),
        LifeStagePrompt("ls3", LifeStage.CHILDHOOD, Icons.Default.ChildCare,
            "Think of the stories your grandparents told you before bedtime.", "Childhood"),
            
        // School/College
        LifeStagePrompt("ls4", LifeStage.SCHOOL_COLLEGE, Icons.Default.School,
            "Remember your favorite school friend and the books you loved reading.", "Education"),
        LifeStagePrompt("ls5", LifeStage.SCHOOL_COLLEGE, Icons.Default.School,
            "Think of your favorite teacher and what they taught you.", "Education"),
        LifeStagePrompt("ls6", LifeStage.SCHOOL_COLLEGE, Icons.Default.School,
            "Remember the games you played during school breaks.", "Education"),
            
        // Career
        LifeStagePrompt("ls7", LifeStage.CAREER, Icons.Default.Work,
            "Think of the first accomplishment that made your family proud in your work.", "Career"),
        LifeStagePrompt("ls8", LifeStage.CAREER, Icons.Default.Work,
            "Remember your first day at work and how you felt.", "Career"),
        LifeStagePrompt("ls9", LifeStage.CAREER, Icons.Default.Work,
            "Think of a colleague who became a good friend.", "Career"),
            
        // Marriage
        LifeStagePrompt("ls10", LifeStage.MARRIAGE, Icons.Default.Favorite,
            "Remember the warm blessings, joy, and laughter on your wedding day.", "Marriage"),
        LifeStagePrompt("ls11", LifeStage.MARRIAGE, Icons.Default.Favorite,
            "Think of the beautiful songs played during your wedding ceremony.", "Marriage"),
        LifeStagePrompt("ls12", LifeStage.MARRIAGE, Icons.Default.Favorite,
            "Remember the special meal prepared for your wedding celebration.", "Marriage"),
            
        // Family/Children
        LifeStagePrompt("ls13", LifeStage.FAMILY_CHILDREN, Icons.Default.Diversity3,
            "Think of holding your children or seeing them take their very first steps.", "Family"),
        LifeStagePrompt("ls14", LifeStage.FAMILY_CHILDREN, Icons.Default.Diversity3,
            "Remember teaching your children their first words or songs.", "Family"),
        LifeStagePrompt("ls15", LifeStage.FAMILY_CHILDREN, Icons.Default.Diversity3,
            "Think of family festivals and celebrations with your children.", "Family"),
            
        // Present Day
        LifeStagePrompt("ls16", LifeStage.PRESENT_DAY, Icons.Default.WbSunny,
            "You are surrounded by love and caring family every single day.", "Present"),
        LifeStagePrompt("ls17", LifeStage.PRESENT_DAY, Icons.Default.WbSunny,
            "Think of the beautiful moments you shared this week with loved ones.", "Present"),
        LifeStagePrompt("ls18", LifeStage.PRESENT_DAY, Icons.Default.WbSunny,
            "Remember the kindness shown to you by family members recently.", "Present")
    )
}

@Composable
fun LifeStageMemoryGameScreen(
    reminiscenceList: List<com.socklet.smritisaathi.domain.model.ReminiscenceContent>,
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
    var currentPromptIndex by remember { mutableStateOf(0) }
    var promptsCompleted by remember { mutableStateOf(0) }
    val startTime = remember { System.currentTimeMillis() }
    var roundStartTime by remember { mutableStateOf(System.currentTimeMillis()) }
    
    // Performance tracking
    var roundScores by remember { mutableStateOf(listOf<Int>()) }
    
    // Dialogs
    var showRoundCompleteDialog by remember { mutableStateOf(false) }
    var showSessionCompleteDialog by remember { mutableStateOf(false) }
    
    // Prompts per round based on difficulty
    val promptsPerRound = when (currentLevel) {
        1 -> 3
        2 -> 4
        3 -> 5
        else -> 5
    }
    
    // Get prompts for this round (avoid repetition)
    val roundPrompts = remember(currentRound, currentLevel) {
        LifeStagePrompts.allPrompts.shuffled().take(promptsPerRound)
    }
    
    val currentPrompt = roundPrompts.getOrNull(currentPromptIndex)

    LaunchedEffect(currentPromptIndex) {
        currentPrompt?.let {
            voiceAssistant.speak("${it.stage.displayName}. ${it.prompt}")
        }
    }

    fun completeRound() {
        val score = 100 // Always positive for reminiscence
        roundScores = roundScores + score
        
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
                    text = "Memory Lane",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Round $currentRound of $totalRounds • Prompt ${currentPromptIndex + 1}/$promptsPerRound",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = {
                currentPrompt?.let { voiceAssistant.speak("${it.stage.displayName}. ${it.prompt}") }
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Voice Prompt", modifier = Modifier.size(32.dp))
            }
        }

        // Stage Showcase Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = Dimensions.Space16),
            shape = RoundedCornerShape(Dimensions.CardCornerRadius),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimensions.Space20),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = currentPrompt?.icon ?: Icons.Default.WbSunny,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(Dimensions.Space16))

                Text(
                    text = currentPrompt?.stage?.displayName ?: "Present Day",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = currentPrompt?.category ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimensions.Space12))

                Text(
                    text = currentPrompt?.prompt ?: "Cherish this wonderful memory.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )
            }
        }

        // Nostalgic Reflection Button
        Button(
            onClick = {
                voiceAssistant.speak("Heartwarming! Cherishing our past keeps our mind joyful.")
                promptsCompleted++
                
                if (currentPromptIndex + 1 < promptsPerRound) {
                    currentPromptIndex++
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
                text = if (currentPromptIndex + 1 < promptsPerRound) "I Remember This ❤️ ➔" else "Complete Memory Walk 🌸",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    
    // Round Complete Dialog
    if (showRoundCompleteDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Round Complete! ❤️") },
            text = {
                Column {
                    Text("Beautiful! You've cherished $promptsPerRound wonderful memories.")
                    Spacer(Modifier.height(8.dp))
                    Text("Round $currentRound of $totalRounds complete")
                    Spacer(Modifier.height(8.dp))
                    Text("Ready for Round ${currentRound + 1}?")
                }
            },
            confirmButton = {
                Button(onClick = {
                    currentRound++
                    currentLevel = (currentLevel + 1).coerceAtMost(5)
                    currentPromptIndex = 0
                    roundStartTime = System.currentTimeMillis()
                    showRoundCompleteDialog = false
                    voiceAssistant.speak("Round $currentRound! Let's cherish more beautiful memories!")
                }) {
                    Text("Next Round")
                }
            }
        )
    }
    
    // Session Complete Dialog
    if (showSessionCompleteDialog) {
        val totalTime = System.currentTimeMillis() - startTime
        
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Memory Walk Complete! 🌟") },
            text = {
                Column {
                    Text("Wonderful! You've completed all $totalRounds rounds of beautiful memories!")
                    Spacer(Modifier.height(12.dp))
                    Text("Total Prompts: ${promptsPerRound * totalRounds}")
                    Text("Memories Cherished: ${promptsCompleted}")
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Your memories are precious treasures that keep your mind joyful and heart full! ❤️",
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showSessionCompleteDialog = false
                    val result = GameResult(
                        gameType = GameType.LIFE_STAGE_MEMORY,
                        score = 100,
                        difficultyLevel = currentLevel,
                        responseTimeMs = totalTime,
                        mistakesCount = 0,
                        completedSuccessfully = true
                    )
                    voiceAssistant.speak("Beautiful! You've completed the memory walk. Cherishing memories keeps our minds young!")
                    onComplete(result)
                }) {
                    Text("Finish")
                }
            }
        )
    }
}
