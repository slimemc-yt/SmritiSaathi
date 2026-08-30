package com.socklet.smritisaathi.ui.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.*
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler
import com.socklet.smritisaathi.ui.games.*
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager
import kotlinx.coroutines.launch

data class GameCatalogItem(
    val gameType: GameType,
    val title: String,
    val subtitle: String,
    val domain: String,
    val icon: ImageVector,
    val badgeColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientGamesScreen(
    patient: Patient,
    repository: PatientRepository,
    voiceAssistant: VoiceAssistantManager,
    gameScheduler: AdaptiveGameScheduler
) {
    val coroutineScope = rememberCoroutineScope()
    val recentGameResults by repository.getGameResultsFlow(patient.id).collectAsStateWithLifecycle(initialValue = emptyList())
    val memories by repository.getReminiscenceContentFlow(patient.id).collectAsStateWithLifecycle(initialValue = emptyList())

    var activePlayingGame by remember { mutableStateOf<GameType?>(null) }
    var selectedDifficulty by remember { mutableStateOf(1) }
    var showDistressCalming by remember { mutableStateOf(false) }
    var lastCompletedGameName by remember { mutableStateOf<String?>(null) }

    val allGames = listOf(
        GameCatalogItem(
            gameType = GameType.FACE_RECOGNITION,
            title = "Face & Family Memory 👤",
            subtitle = "Recognize family members, loved ones, and familiar faces.",
            domain = "Visual Memory & Social Recall",
            icon = Icons.Default.Person,
            badgeColor = Color(0xFF1E88E5)
        ),
        GameCatalogItem(
            gameType = GameType.CARD_MATCHING,
            title = "Card Match & Motifs 🎴",
            subtitle = "Flip cards and find matching pairs of familiar shapes and symbols.",
            domain = "Working Memory & Focus",
            icon = Icons.Default.Style,
            badgeColor = Color(0xFF43A047)
        ),
        GameCatalogItem(
            gameType = GameType.DAILY_ROUTINE,
            title = "Daily Routine Steps 📅",
            subtitle = "Arrange morning to evening activities in the right sequence.",
            domain = "Executive Function & Sequencing",
            icon = Icons.Default.Schedule,
            badgeColor = Color(0xFFFB8C00)
        ),
        GameCatalogItem(
            gameType = GameType.NER_FAMILIAR_IMAGES,
            title = "NER Heritage & Culture 🦏",
            subtitle = "Identify famous North East landmarks, festivals, and cultural treasures.",
            domain = "Long-term Memory & Cultural Grounding",
            icon = Icons.Default.Forest,
            badgeColor = Color(0xFF8E24AA)
        ),
        GameCatalogItem(
            gameType = GameType.SHOPPING_BASKET,
            title = "Shopping Basket 🧺",
            subtitle = "Memorize grocery items on your list, then pick them from the market shelf.",
            domain = "Short-term Visual Memory",
            icon = Icons.Default.ShoppingBasket,
            badgeColor = Color(0xFF00ACC1)
        ),
        GameCatalogItem(
            gameType = GameType.QUICK_RECALL,
            title = "Quick Recall Stories 📖",
            subtitle = "Listen to short warm stories and answer fun recall questions.",
            domain = "Verbal Comprehension & Retention",
            icon = Icons.Default.MenuBook,
            badgeColor = Color(0xFF3949AB)
        ),
        GameCatalogItem(
            gameType = GameType.LIFE_STAGE_MEMORY,
            title = "Life-Stage Memory Lane 🌸",
            subtitle = "Cherish nostalgic moments from childhood, wedding, and family life.",
            domain = "Episodic Reminiscence & Identity",
            icon = Icons.Default.PhotoLibrary,
            badgeColor = Color(0xFFE91E63)
        ),
        GameCatalogItem(
            gameType = GameType.GUESS_WHOS_SPEAKING,
            title = "Guess Who's Speaking 🎙️",
            subtitle = "Listen to voice messages and identify which family member is speaking.",
            domain = "Auditory Memory & Voice Recognition",
            icon = Icons.Default.RecordVoiceOver,
            badgeColor = Color(0xFFD81B60)
        ),
        GameCatalogItem(
            gameType = GameType.MUSIC_MEMORY,
            title = "Folk & Song Memory 🎶",
            subtitle = "Recall legendary regional melodies by Bhupen Hazarika and tribal folk rhythms.",
            domain = "Emotional Recall & Melody Pattern",
            icon = Icons.Default.MusicNote,
            badgeColor = Color(0xFF5E35B1)
        )
    )

    // Recommended game from AI scheduler
    val recommendation = remember(recentGameResults) {
        gameScheduler.decideNextGame(recentGameResults)
    }

    // 1. Distress Calming View
    if (showDistressCalming) {
        DistressCalmingScreen(
            patientName = patient.name,
            voiceAssistant = voiceAssistant,
            onRecovered = {
                showDistressCalming = false
                activePlayingGame = null
            }
        )
        return
    }

    // 2. Active Game Screen Router
    activePlayingGame?.let { gameType ->
        when (gameType) {
            GameType.FACE_RECOGNITION -> {
                FaceRecognitionGameScreen(
                    contacts = patient.familyContacts,
                    difficultyLevel = selectedDifficulty,
                    voiceAssistant = voiceAssistant,
                    onComplete = { result ->
                        coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                        lastCompletedGameName = "Face Recognition"
                        activePlayingGame = null
                    },
                    onDistressTriggered = { showDistressCalming = true },
                    onExit = { activePlayingGame = null }
                )
            }
            GameType.CARD_MATCHING -> {
                CardMatchingGameScreen(
                    difficultyLevel = selectedDifficulty,
                    voiceAssistant = voiceAssistant,
                    onComplete = { result ->
                        coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                        lastCompletedGameName = "Card Match"
                        activePlayingGame = null
                    },
                    onDistressTriggered = { showDistressCalming = true },
                    onExit = { activePlayingGame = null }
                )
            }
            GameType.DAILY_ROUTINE -> {
                DailyRoutineOrderingGameScreen(
                    difficultyLevel = selectedDifficulty,
                    voiceAssistant = voiceAssistant,
                    onComplete = { result ->
                        coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                        lastCompletedGameName = "Daily Routine"
                        activePlayingGame = null
                    },
                    onDistressTriggered = { showDistressCalming = true },
                    onExit = { activePlayingGame = null }
                )
            }
            GameType.NER_FAMILIAR_IMAGES -> {
                NERFamiliarImagesGameScreen(
                    patientState = "Assam",
                    difficultyLevel = selectedDifficulty,
                    voiceAssistant = voiceAssistant,
                    onComplete = { result ->
                        coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                        lastCompletedGameName = "NER Heritage"
                        activePlayingGame = null
                    },
                    onDistressTriggered = { showDistressCalming = true },
                    onExit = { activePlayingGame = null }
                )
            }
            GameType.SHOPPING_BASKET -> {
                ShoppingBasketGameScreen(
                    difficultyLevel = selectedDifficulty,
                    voiceAssistant = voiceAssistant,
                    onComplete = { result ->
                        coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                        lastCompletedGameName = "Shopping Basket"
                        activePlayingGame = null
                    },
                    onDistressTriggered = { showDistressCalming = true },
                    onExit = { activePlayingGame = null }
                )
            }
            GameType.QUICK_RECALL -> {
                QuickRecallGameScreen(
                    difficultyLevel = selectedDifficulty,
                    voiceAssistant = voiceAssistant,
                    onComplete = { result ->
                        coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                        lastCompletedGameName = "Quick Recall"
                        activePlayingGame = null
                    },
                    onDistressTriggered = { showDistressCalming = true },
                    onExit = { activePlayingGame = null }
                )
            }
            GameType.LIFE_STAGE_MEMORY -> {
                LifeStageMemoryGameScreen(
                    reminiscenceList = memories,
                    difficultyLevel = selectedDifficulty,
                    voiceAssistant = voiceAssistant,
                    onComplete = { result ->
                        coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                        lastCompletedGameName = "Life Stages"
                        activePlayingGame = null
                    },
                    onDistressTriggered = { showDistressCalming = true },
                    onExit = { activePlayingGame = null }
                )
            }
            GameType.GUESS_WHOS_SPEAKING -> {
                GuessWhosSpeakingGameScreen(
                    contacts = patient.familyContacts,
                    difficultyLevel = selectedDifficulty,
                    voiceAssistant = voiceAssistant,
                    onComplete = { result ->
                        coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                        lastCompletedGameName = "Voice Guess"
                        activePlayingGame = null
                    },
                    onDistressTriggered = { showDistressCalming = true },
                    onExit = { activePlayingGame = null }
                )
            }
            GameType.MUSIC_MEMORY -> {
                MusicMemoryGameScreen(
                    difficultyLevel = selectedDifficulty,
                    voiceAssistant = voiceAssistant,
                    onComplete = { result ->
                        coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                        lastCompletedGameName = "Music Memory"
                        activePlayingGame = null
                    },
                    onDistressTriggered = { showDistressCalming = true },
                    onExit = { activePlayingGame = null }
                )
            }
        }
        return
    }

    // 3. Games Hub List View
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimensions.ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Space16)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Cognitive Games 🎮",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Fun, no-guilt exercises to nurture your brain",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(onClick = {
                    voiceAssistant.speak("Welcome to your cognitive games. Tap any game to start playing, or tap the top card for your recommended activity.")
                }) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Voice Guide",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        // Completion Banner if recently finished a game
        lastCompletedGameName?.let { gameName ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Space16),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Celebration, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(Dimensions.Space12))
                            Column {
                                Text(
                                    text = "Well Done! 🎉",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32),
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "You completed $gameName activity!",
                                    fontSize = 13.sp,
                                    color = Color(0xFF1B5E20)
                                )
                            }
                        }
                        IconButton(onClick = { lastCompletedGameName = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = Color(0xFF2E7D32))
                        }
                    }
                }
            }
        }

        // AI Recommended Hero Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activePlayingGame = recommendation.gameType
                        selectedDifficulty = recommendation.difficultyLevel
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Space20),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(Dimensions.Space16))
                        Column {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "ADAPTIVE AI RECOMMENDATION",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = recommendation.gameType.displayName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Level ${recommendation.difficultyLevel} • Tap to Start ➔",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Icon(
                        Icons.Default.PlayCircle,
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }
        }

        // Difficulty Level Selector
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Difficulty Level",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimensions.Space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Space8)
                ) {
                    listOf(
                        Triple(1, "Level 1", "Gentle (2 items)"),
                        Triple(2, "Level 2", "Balanced (3 items)"),
                        Triple(3, "Level 3", "Active (5 items)")
                    ).forEach { (lvl, title, desc) ->
                        val isSelected = selectedDifficulty == lvl
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedDifficulty = lvl },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = desc,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Title: All Games Catalog
        item {
            Text(
                text = "Choose Any Game (${allGames.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // All 10 Games Grid / List
        items(allGames) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activePlayingGame = item.gameType
                        voiceAssistant.speak("Starting ${item.title}")
                    },
                shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Space16),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = item.badgeColor.copy(alpha = 0.15f),
                            modifier = Modifier.size(50.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    tint = item.badgeColor,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(Dimensions.Space16))

                        Column {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = item.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surface
                            ) {
                                Text(
                                    text = item.domain,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = item.badgeColor,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(Dimensions.Space24))
        }
    }
}
