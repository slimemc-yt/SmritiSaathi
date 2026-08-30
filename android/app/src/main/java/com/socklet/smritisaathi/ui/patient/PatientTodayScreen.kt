package com.socklet.smritisaathi.ui.patient

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.*
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler
import com.socklet.smritisaathi.ui.games.*
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager
import kotlinx.coroutines.launch
import java.util.Calendar

enum class PatientActiveView {
    CALM_TODAY,
    INCOMING_CALL_OVERLAY,
    PLAYING_GAME,
    MEDICATION_PROMPT,
    MEAL_CHECK_IN,
    REMINISCENCE_VIEW,
    DISTRESS_CALMING
}

@Composable
fun PatientTodayScreen(
    patient: Patient,
    repository: PatientRepository,
    voiceAssistant: VoiceAssistantManager,
    gameScheduler: AdaptiveGameScheduler,
    dataStoreManager: com.socklet.smritisaathi.data.datastore.DataStoreManager = hiltViewModel<PatientContainerViewModel>().dataStoreManager,
    apiManager: com.socklet.smritisaathi.data.api.ApiManager = hiltViewModel<PatientContainerViewModel>().apiManager
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var activeView by remember { mutableStateOf(PatientActiveView.CALM_TODAY) }
    var activeTask by remember { mutableStateOf<CognitiveTask?>(null) }
    var activeReminder by remember { mutableStateOf<Reminder?>(null) }
    var currentGameType by remember { mutableStateOf(GameType.CARD_MATCHING) }
    var currentDifficulty by remember { mutableStateOf(1) }
    
    // AI-generated routine state
    var aiRoutine by remember { mutableStateOf<String?>(null) }
    var isLoadingRoutine by remember { mutableStateOf(false) }
    var showSignOutDialog by remember { mutableStateOf(false) }

    val recentGameResults by repository.getGameResultsFlow(patient.id).collectAsStateWithLifecycle(initialValue = emptyList())
    val pendingActions by repository.getUnprocessedPendingActionsFlow(patient.id).collectAsStateWithLifecycle(initialValue = emptyList())
    val reminders by repository.getRemindersFlow(patient.id).collectAsStateWithLifecycle(initialValue = emptyList())
    val memories by repository.getReminiscenceContentFlow(patient.id).collectAsStateWithLifecycle(initialValue = emptyList())
    
    // Get current language
    val currentLanguage by dataStoreManager.preferredLanguageFlow.collectAsStateWithLifecycle(initialValue = "en")

    // Generate AI routine on first load
    LaunchedEffect(patient.id, currentLanguage) {
        if (aiRoutine == null && !isLoadingRoutine) {
            isLoadingRoutine = true
            try {
                val patientContext = """
                    Patient Name: ${patient.name}
                    Age: ${patient.age}
                    Dementia Stage: ${patient.dementiaStage.name}
                    Preferred Language: $currentLanguage
                    Recent Game Results: ${recentGameResults.size} games played
                    Current Reminders: ${reminders.size} active
                """.trimIndent()
                
                val result = apiManager.generateRoutine(patientContext, currentLanguage)
                aiRoutine = result.getOrNull()
                Log.d("PatientTodayScreen", "AI Routine generated: ${aiRoutine?.take(100)}...")
            } catch (e: Exception) {
                Log.e("PatientTodayScreen", "Failed to generate AI routine: ${e.message}")
                aiRoutine = "Take a gentle walk in the garden 🌸\nSpend time looking at family photos 📸\nListen to favorite music 🎵"
            } finally {
                isLoadingRoutine = false
            }
        }
    }

    // Greeting according to time of day
    val greeting = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when {
            hour < 12 -> "Good morning"
            hour < 17 -> "Good afternoon"
            else -> "Good evening"
        }
    }

    // Next scheduled reminder
    val nextPendingReminder = remember(reminders) {
        reminders.firstOrNull { it.status == ReminderStatus.PENDING }
    }

    // Real-time Pending Action Listener (Grandchild game invites or Family Nudges)
    LaunchedEffect(pendingActions) {
        val latestAction = pendingActions.firstOrNull()
        if (latestAction != null && activeView == PatientActiveView.CALM_TODAY) {
            when (latestAction.type) {
                ActionType.PLAY_INVITE -> {
                    activeTask = CognitiveTask(
                        id = latestAction.id,
                        type = TaskType.GRANDCHILD_INVITE,
                        title = "${latestAction.senderName} wants to play a game! 🎮",
                        subtitle = "Tap answer to start playing together",
                        gameType = latestAction.targetGame ?: GameType.CARD_MATCHING
                    )
                    activeView = PatientActiveView.INCOMING_CALL_OVERLAY
                    voiceAssistant.speak("${latestAction.senderName} has sent you a game invite! Tap answer to play.")
                }
                ActionType.NUDGE -> {
                    voiceAssistant.speak("Message from ${latestAction.senderName}: ${latestAction.message}")
                    coroutineScope.launch {
                        repository.markActionProcessed(patient.id, latestAction.id)
                    }
                }
                ActionType.TRIGGER_CALMING -> {
                    activeView = PatientActiveView.DISTRESS_CALMING
                }
                ActionType.FORCE_GAME -> {
                    currentGameType = latestAction.targetGame ?: GameType.CARD_MATCHING
                    currentDifficulty = 1
                    activeView = PatientActiveView.PLAYING_GAME
                }
            }
        }
    }

    // Active View Router
    when (activeView) {
        PatientActiveView.INCOMING_CALL_OVERLAY -> {
            activeTask?.let { task ->
                IncomingCallReminderOverlay(
                    task = task,
                    onAccept = {
                        coroutineScope.launch {
                            if (task.id.isNotBlank()) {
                                repository.markActionProcessed(patient.id, task.id)
                            }
                        }
                        when (task.type) {
                            TaskType.GRANDCHILD_INVITE, TaskType.COGNITIVE_GAME -> {
                                currentGameType = task.gameType ?: GameType.CARD_MATCHING
                                currentDifficulty = task.difficultyLevel
                                activeView = PatientActiveView.PLAYING_GAME
                            }
                            TaskType.MEDICATION_REMINDER -> {
                                activeView = PatientActiveView.MEDICATION_PROMPT
                            }
                            TaskType.DAILY_CHECK_IN -> {
                                activeView = PatientActiveView.MEAL_CHECK_IN
                            }
                            TaskType.REMINISCENCE_MOMENT -> {
                                activeView = PatientActiveView.REMINISCENCE_VIEW
                            }
                            else -> {
                                activeView = PatientActiveView.CALM_TODAY
                            }
                        }
                    },
                    onDecline = {
                        activeView = PatientActiveView.CALM_TODAY
                    }
                )
            } ?: run { activeView = PatientActiveView.CALM_TODAY }
        }

        PatientActiveView.MEDICATION_PROMPT -> {
            val medReminder = activeReminder ?: Reminder(
                id = "temp_1",
                title = "Morning Blood Pressure & Memory Vitamin",
                medicineName = "Memory Support & BP Tablet",
                dosage = "1 tablet after food",
                scheduledTime = "8:30 AM"
            )

            MedicationPromptScreen(
                reminder = medReminder,
                voiceAssistant = voiceAssistant,
                onTaken = {
                    coroutineScope.launch {
                        repository.updateReminderStatus(patient.id, medReminder.id, ReminderStatus.TAKEN)
                    }
                    activeView = PatientActiveView.CALM_TODAY
                },
                onRemindLater = {
                    coroutineScope.launch {
                        repository.updateReminderStatus(patient.id, medReminder.id, ReminderStatus.SNOOZED)
                    }
                    activeView = PatientActiveView.CALM_TODAY
                },
                onNeedHelp = {
                    coroutineScope.launch {
                        repository.updateReminderStatus(patient.id, medReminder.id, ReminderStatus.HELP_REQUESTED)
                        repository.triggerAlert(
                            patient.id,
                            BehavioralAlert(
                                patientId = patient.id,
                                patientName = patient.name,
                                type = AlertType.MEDICATION_HELP_REQUESTED,
                                severity = AlertSeverity.URGENT,
                                title = "Help Needed for Medication",
                                description = "${patient.name} requested help for ${medReminder.title} at ${medReminder.scheduledTime}."
                            )
                        )
                    }
                    activeView = PatientActiveView.CALM_TODAY
                }
            )
        }

        PatientActiveView.MEAL_CHECK_IN -> {
            DailyCheckInScreen(
                patientName = patient.name,
                voiceAssistant = voiceAssistant,
                onAnswerSubmitted = { answer ->
                    coroutineScope.launch {
                        repository.triggerAlert(
                            patient.id,
                            BehavioralAlert(
                                patientId = patient.id,
                                patientName = patient.name,
                                type = AlertType.ROUTINE_DEVIATION,
                                severity = AlertSeverity.INFO,
                                title = "Meal Check-in Recorded",
                                description = "${patient.name} reported: $answer"
                            )
                        )
                    }
                    activeView = PatientActiveView.CALM_TODAY
                },
                onDismiss = { activeView = PatientActiveView.CALM_TODAY }
            )
        }

        PatientActiveView.REMINISCENCE_VIEW -> {
            PatientReminiscenceScreen(
                memories = memories,
                voiceAssistant = voiceAssistant,
                onBack = { activeView = PatientActiveView.CALM_TODAY }
            )
        }

        PatientActiveView.DISTRESS_CALMING -> {
            DistressCalmingScreen(
                patientName = patient.name,
                voiceAssistant = voiceAssistant,
                onRecovered = { activeView = PatientActiveView.CALM_TODAY }
            )
        }

        PatientActiveView.PLAYING_GAME -> {
            // Render the selected Game
            when (currentGameType) {
                GameType.FACE_RECOGNITION -> {
                    FaceRecognitionGameScreen(
                        contacts = patient.familyContacts,
                        difficultyLevel = currentDifficulty,
                        voiceAssistant = voiceAssistant,
                        onComplete = { result ->
                            coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                            activeView = PatientActiveView.CALM_TODAY
                        },
                        onDistressTriggered = {
                            coroutineScope.launch {
                                repository.triggerAlert(
                                    patient.id,
                                    BehavioralAlert(
                                        patientId = patient.id,
                                        patientName = patient.name,
                                        type = AlertType.CONFUSION_DISTRESS_DETECTED,
                                        severity = AlertSeverity.MEDIUM,
                                        title = "Confusion Detected in Face Recognition",
                                        description = "Repeated errors triggered comforting reassurance mode for ${patient.name}."
                                    )
                                )
                            }
                            activeView = PatientActiveView.DISTRESS_CALMING
                        },
                        onExit = { activeView = PatientActiveView.CALM_TODAY }
                    )
                }
                GameType.CARD_MATCHING -> {
                    CardMatchingGameScreen(
                        difficultyLevel = currentDifficulty,
                        voiceAssistant = voiceAssistant,
                        onComplete = { result ->
                            coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                            activeView = PatientActiveView.CALM_TODAY
                        },
                        onDistressTriggered = { activeView = PatientActiveView.DISTRESS_CALMING },
                        onExit = { activeView = PatientActiveView.CALM_TODAY }
                    )
                }
                GameType.DAILY_ROUTINE -> {
                    DailyRoutineOrderingGameScreen(
                        difficultyLevel = currentDifficulty,
                        customRoutine = patient.dailyRoutine,
                        voiceAssistant = voiceAssistant,
                        onComplete = { result ->
                            coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                            activeView = PatientActiveView.CALM_TODAY
                        },
                        onDistressTriggered = { activeView = PatientActiveView.DISTRESS_CALMING },
                        onExit = { activeView = PatientActiveView.CALM_TODAY }
                    )
                }
                GameType.NER_FAMILIAR_IMAGES -> {
                    NERFamiliarImagesGameScreen(
                        patientState = "Assam",
                        difficultyLevel = currentDifficulty,
                        voiceAssistant = voiceAssistant,
                        onComplete = { result ->
                            coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                            activeView = PatientActiveView.CALM_TODAY
                        },
                        onDistressTriggered = { activeView = PatientActiveView.DISTRESS_CALMING },
                        onExit = { activeView = PatientActiveView.CALM_TODAY }
                    )
                }
                GameType.SHOPPING_BASKET -> {
                    ShoppingBasketGameScreen(
                        difficultyLevel = currentDifficulty,
                        voiceAssistant = voiceAssistant,
                        onComplete = { result ->
                            coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                            activeView = PatientActiveView.CALM_TODAY
                        },
                        onDistressTriggered = { activeView = PatientActiveView.DISTRESS_CALMING },
                        onExit = { activeView = PatientActiveView.CALM_TODAY }
                    )
                }
                GameType.QUICK_RECALL -> {
                    QuickRecallGameScreen(
                        difficultyLevel = currentDifficulty,
                        voiceAssistant = voiceAssistant,
                        onComplete = { result ->
                            coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                            activeView = PatientActiveView.CALM_TODAY
                        },
                        onDistressTriggered = { activeView = PatientActiveView.DISTRESS_CALMING },
                        onExit = { activeView = PatientActiveView.CALM_TODAY }
                    )
                }
                GameType.LIFE_STAGE_MEMORY -> {
                    LifeStageMemoryGameScreen(
                        reminiscenceList = memories,
                        difficultyLevel = currentDifficulty,
                        voiceAssistant = voiceAssistant,
                        onComplete = { result ->
                            coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                            activeView = PatientActiveView.CALM_TODAY
                        },
                        onDistressTriggered = { activeView = PatientActiveView.DISTRESS_CALMING },
                        onExit = { activeView = PatientActiveView.CALM_TODAY }
                    )
                }
                GameType.GUESS_WHOS_SPEAKING -> {
                    GuessWhosSpeakingGameScreen(
                        contacts = patient.familyContacts,
                        difficultyLevel = currentDifficulty,
                        voiceAssistant = voiceAssistant,
                        onComplete = { result ->
                            coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                            activeView = PatientActiveView.CALM_TODAY
                        },
                        onDistressTriggered = { activeView = PatientActiveView.DISTRESS_CALMING },
                        onExit = { activeView = PatientActiveView.CALM_TODAY }
                    )
                }
                GameType.MUSIC_MEMORY -> {
                    MusicMemoryGameScreen(
                        difficultyLevel = currentDifficulty,
                        voiceAssistant = voiceAssistant,
                        onComplete = { result ->
                            coroutineScope.launch { repository.saveGameResult(patient.id, result) }
                            activeView = PatientActiveView.CALM_TODAY
                        },
                        onDistressTriggered = { activeView = PatientActiveView.DISTRESS_CALMING },
                        onExit = { activeView = PatientActiveView.CALM_TODAY }
                    )
                }
            }
        }

        PatientActiveView.CALM_TODAY -> {
            // STEP 5: CALM "TODAY" VIEW
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .safeDrawingPadding()
                    .padding(Dimensions.ScreenPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header: Patient Greeting with Sign Out
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimensions.Space16),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "$greeting, ${patient.name.ifBlank { "Friend" }} 🌞",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(Dimensions.Space4))
                        Text(
                            text = "SmritiSaathi is here by your side",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // Sign Out Button
                    IconButton(
                        onClick = {
                            showSignOutDialog = true
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Sign Out",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Sign Out Confirmation Dialog
                if (showSignOutDialog) {
                    AlertDialog(
                        onDismissRequest = { showSignOutDialog = false },
                        title = {
                            Text(
                                text = "Sign Out",
                                fontWeight = FontWeight.Bold
                            )
                        },
                        text = {
                            Text("Are you sure you want to sign out? You will need to pair this device again to access the patient dashboard.")
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showSignOutDialog = false
                                    coroutineScope.launch {
                                        // Clear patient pairing
                                        dataStoreManager.clearUserSession()
                                        
                                        // Wait for DataStore to be cleared
                                        kotlinx.coroutines.delay(500)
                                        
                                        // Restart app to go back to language selection
                                        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                                        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        context.startActivity(intent)
                                    }
                                }
                            ) {
                                Text("Sign Out", color = MaterialTheme.colorScheme.error)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showSignOutDialog = false }
                            ) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                // AI-Generated Daily Routine Card
                if (isLoadingRoutine) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimensions.Space12),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimensions.Space16),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(Dimensions.Space12))
                            Text(
                                text = "AI is creating your personalized routine...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                } else if (aiRoutine != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimensions.Space12),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimensions.Space16)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(Dimensions.Space8))
                                Text(
                                    text = "✨ AI-Personalized Routine",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.height(Dimensions.Space8))
                            Text(
                                text = aiRoutine ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                // Center Primary Action: Single Big Button for AI Recommended Activity
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val recommendation = remember(recentGameResults) {
                        gameScheduler.decideNextGame(recentGameResults)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clickable {
                                currentGameType = recommendation.gameType
                                currentDifficulty = recommendation.difficultyLevel
                                activeView = PatientActiveView.PLAYING_GAME
                            },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(Dimensions.Space20),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(54.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.SportsEsports,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(Dimensions.Space12))

                            Text(
                                text = "Start Your 10-Minute Activity 🎯",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${recommendation.gameType.displayName} (Level ${recommendation.difficultyLevel})",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Dimensions.Space16))

                    // Secondary Quick-Access Cues: Memories & Next Schedule
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Space12)
                    ) {
                        // Reminiscence Corner
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp)
                                .clickable { activeView = PatientActiveView.REMINISCENCE_VIEW },
                            shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Family Photos 🌸", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }

                        // Next Reminder Cue
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp)
                                .clickable {
                                    if (nextPendingReminder != null) {
                                        activeReminder = nextPendingReminder
                                        activeView = PatientActiveView.MEDICATION_PROMPT
                                    } else {
                                        activeView = PatientActiveView.MEAL_CHECK_IN
                                    }
                                },
                            shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(Icons.Default.Medication, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = nextPendingReminder?.scheduledTime ?: "Daily Check-in 🍲",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                // BOTTOM: PERSISTENT LARGE SOS EMERGENCY BUTTON
                Button(
                    onClick = {
                        // Trigger SOS call and Alert
                        coroutineScope.launch {
                            repository.triggerAlert(
                                patient.id,
                                BehavioralAlert(
                                    patientId = patient.id,
                                    patientName = patient.name,
                                    type = AlertType.SOS_TRIGGERED,
                                    severity = AlertSeverity.URGENT,
                                    title = "EMERGENCY: SOS Button Pressed",
                                    description = "${patient.name} pressed the SOS Emergency button in the app."
                                )
                            )
                        }

                        val targetSosNumber = patient.sosNumber.ifBlank {
                            patient.emergencyContact.phoneNumber.ifBlank { "112" }
                        }
                        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$targetSosNumber"))
                        context.startActivity(dialIntent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    shape = RoundedCornerShape(Dimensions.ButtonCornerRadius),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                ) {
                    Icon(Icons.Default.Emergency, contentDescription = "SOS", modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.width(Dimensions.Space12))
                    Text(text = "EMERGENCY SOS 🆘", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
