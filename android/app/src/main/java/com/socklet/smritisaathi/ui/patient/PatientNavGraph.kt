package com.socklet.smritisaathi.ui.patient

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.socklet.smritisaathi.domain.model.*
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler
import com.socklet.smritisaathi.navigation.Screen
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager
import kotlinx.coroutines.launch

sealed class PatientScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : PatientScreen("patient_home", "Today", Icons.Default.Home)
    object Games : PatientScreen("patient_games", "Games", Icons.Default.SportsEsports)
    object Reminders : PatientScreen("patient_reminders", "Schedule", Icons.Default.Notifications)
    object Reminiscence : PatientScreen("patient_reminiscence", "Memories", Icons.Default.PhotoLibrary)
}

fun NavGraphBuilder.patientNavGraph(
    @Suppress("UNUSED_PARAMETER") navController: NavHostController,
    patientId: String = "default_patient"
) {
    composable(Screen.PatientHome.route) {
        PatientMainScreen(patientId = patientId)
    }
}

@Composable
fun PatientMainScreen(
    patientId: String = "default_patient",
    viewModel: PatientContainerViewModel = hiltViewModel(),
    repository: PatientRepository = viewModel.repository,
    voiceAssistant: VoiceAssistantManager = viewModel.voiceAssistant,
    gameScheduler: AdaptiveGameScheduler = viewModel.gameScheduler,
    dataStoreManager: com.socklet.smritisaathi.data.datastore.DataStoreManager = viewModel.dataStoreManager
) {
    val patientNavController = rememberNavController()
    val navBackStackEntry by patientNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val coroutineScope = rememberCoroutineScope()

    // Load the actual patientId from DataStore (saved during pairing)
    var resolvedPatientId by remember { mutableStateOf(patientId) }
    
    // Collect the paired patient ID from DataStore
    val pairedPatientId by dataStoreManager.pairedPatientIdFlow.collectAsStateWithLifecycle(initialValue = null)
    
    LaunchedEffect(pairedPatientId) {
        if (!pairedPatientId.isNullOrBlank()) {
            resolvedPatientId = pairedPatientId!!
            android.util.Log.d("PatientNavGraph", "✓ Loaded patientId from DataStore: $resolvedPatientId")
        } else {
            android.util.Log.d("PatientNavGraph", "No saved patientId in DataStore, using: $resolvedPatientId")
        }
    }

    val patientState by repository.getPatientFlow(resolvedPatientId).collectAsStateWithLifecycle(initialValue = null)
    val currentPatient = patientState ?: run {
        android.util.Log.w("PatientNavGraph", "Patient not found for ID: $resolvedPatientId")
        null
    }

    // Check if patient data failed to load (account deleted or patient removed)
    LaunchedEffect(currentPatient, resolvedPatientId) {
        if (currentPatient == null && resolvedPatientId != "default_patient" && resolvedPatientId.isNotBlank()) {
            android.util.Log.w("PatientNavGraph", "Patient data not found - account may have been deleted")
            // Clear the invalid pairing
            dataStoreManager.clearUserSession()
        }
    }

    val tierConfig = currentPatient?.let {
        TierConfig.fromDementiaStage(
            it.dementiaStage,
            it.enhancedSupportEnabled
        )
    } ?: TierConfig() // Default fallback for loading state

    // Show loading state if patient data hasn't loaded yet
    if (currentPatient == null) {
        // If no patient is paired, navigate back to language/role selection
        if (resolvedPatientId == "default_patient" || resolvedPatientId.isBlank()) {
            LaunchedEffect(Unit) {
                // Navigate back to start (language selection)
                // This will be handled by the parent nav controller
                android.util.Log.d("PatientNavGraph", "No patient paired, should navigate to language selection")
            }
        }
        
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimensions.Space16),
                modifier = Modifier.padding(32.dp)
            ) {
                if (resolvedPatientId == "default_patient" || resolvedPatientId.isBlank()) {
                    // No pairing yet
                    Icon(
                        imageVector = Icons.Default.VpnKey,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "No Patient Paired",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Please use the pairing code from your family member's device to connect.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Returning to home screen...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                } else {
                    // Patient ID exists but data not found (account deleted)
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "Patient Data Not Found",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "The patient account may have been deleted. Please contact your family member or pair with a new account.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = {
                            // Clear pairing and restart
                            coroutineScope.launch {
                                dataStoreManager.clearUserSession()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Reset & Re-pair")
                    }
                }
            }
        }
        return
    }

    Scaffold(
        bottomBar = {
            if (tierConfig.hasBottomNavigation) {
                PatientBottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        patientNavController.navigate(route) {
                            popUpTo(patientNavController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = patientNavController,
            startDestination = PatientScreen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(PatientScreen.Home.route) {
                PatientTodayScreen(
                    patient = currentPatient,
                    repository = repository,
                    voiceAssistant = voiceAssistant,
                    gameScheduler = gameScheduler
                )
            }
            composable(PatientScreen.Games.route) {
                PatientGamesScreen(
                    patient = currentPatient,
                    repository = repository,
                    voiceAssistant = voiceAssistant,
                    gameScheduler = gameScheduler
                )
            }
            composable(PatientScreen.Reminders.route) {
                PatientScheduleScreen(
                    patient = currentPatient,
                    repository = repository,
                    voiceAssistant = voiceAssistant
                )
            }
            composable(PatientScreen.Reminiscence.route) {
                val memories by repository.getReminiscenceContentFlow(currentPatient.id).collectAsStateWithLifecycle(initialValue = emptyList())
                PatientReminiscenceScreen(
                    memories = memories,
                    voiceAssistant = voiceAssistant,
                    onBack = { patientNavController.navigate(PatientScreen.Home.route) }
                )
            }
        }
    }
}

@Composable
fun PatientBottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        PatientScreen.Home,
        PatientScreen.Games,
        PatientScreen.Reminders,
        PatientScreen.Reminiscence
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title,
                        modifier = Modifier.size(28.dp)
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                selected = currentRoute == screen.route,
                onClick = { onNavigate(screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

@Composable
fun PatientScheduleScreen(
    patient: Patient,
    repository: PatientRepository,
    voiceAssistant: VoiceAssistantManager
) {
    val reminders by repository.getRemindersFlow(patient.id).collectAsStateWithLifecycle(initialValue = emptyList())

    val displayReminders = remember(reminders) {
        if (reminders.isNotEmpty()) reminders
        else listOf(
            Reminder(title = "Morning Medicine", scheduledTime = "8:30 AM", type = ReminderType.MEDICATION),
            Reminder(title = "Healthy Lunch & Warm Soup", scheduledTime = "1:00 PM", type = ReminderType.MEAL),
            Reminder(title = "Evening Brain Activity & Walk", scheduledTime = "5:00 PM", type = ReminderType.ACTIVITY),
            Reminder(title = "Night Medicine & Sleep", scheduledTime = "9:30 PM", type = ReminderType.MEDICATION)
        )
    }

    LaunchedEffect(Unit) {
        voiceAssistant.speak("Here is your daily schedule for today.")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.ScreenPadding)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Today's Schedule 📅",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = {
                voiceAssistant.speak("You have ${displayReminders.size} scheduled routine activities today.")
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Read", modifier = Modifier.size(32.dp))
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
        ) {
            items(displayReminders) { reminder ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            voiceAssistant.speak("${reminder.title} at ${reminder.scheduledTime}")
                        },
                    shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Space16),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(48.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = when (reminder.type) {
                                            ReminderType.MEDICATION -> Icons.Default.Medication
                                            ReminderType.MEAL -> Icons.Default.Restaurant
                                            else -> Icons.Default.DirectionsWalk
                                        },
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(Dimensions.Space16))
                            Column {
                                Text(
                                    text = reminder.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = reminder.scheduledTime,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Speak",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
