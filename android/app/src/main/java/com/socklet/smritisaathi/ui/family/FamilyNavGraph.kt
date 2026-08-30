package com.socklet.smritisaathi.ui.family

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.navigation.Screen
import com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingScreen
import com.socklet.smritisaathi.ui.patient.PatientContainerViewModel

sealed class FamilyScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : FamilyScreen("family_home", "Dashboard", Icons.Default.Home)
    object PlayWithGrandpa : FamilyScreen("family_play_invite", "Play Invite", Icons.Default.SportsEsports)
    object Alerts : FamilyScreen("family_alerts", "Alerts", Icons.Default.Notifications)
    object Reminders : FamilyScreen("family_reminders", "Schedule", Icons.Default.Schedule)
    object Reminiscence : FamilyScreen("family_reminiscence", "Memories", Icons.Default.PhotoLibrary)
    object Reports : FamilyScreen("family_reports", "Reports", Icons.Default.Assessment)
}

fun NavGraphBuilder.familyNavGraph(
    navController: NavHostController,
    patientId: String? = null,
    onSignOut: () -> Unit = {}
) {
    composable(Screen.FamilyHome.route) {
        FamilyMainScreen(
            navController = navController,
            patientId = patientId ?: "",
            onSignOut = onSignOut
        )
    }
    composable(Screen.FamilyAddPatient.route) {
        val currentUserId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: "default_family_user"
        PatientOnboardingScreen(
            familyMemberId = currentUserId,
            onOnboardingComplete = { _ ->
                navController.navigate(Screen.FamilyHome.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun FamilyMainScreen(
    navController: NavHostController,
    patientId: String = "",
    onSignOut: () -> Unit = {},
    repository: PatientRepository = hiltViewModel<PatientContainerViewModel>().repository
) {
    val innerNavController = rememberNavController()
    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // CRITICAL PERSISTENCE FIX: Resolve the active patient from the current
    // authenticated Firebase UID. First use the passed patientId; if empty,
    // load this family user's first linked patient from Firestore.
    val currentFirebaseUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
    val isDemoSession = currentFirebaseUser == null || currentFirebaseUser.isAnonymous
    val currentUserId = currentFirebaseUser?.uid
    var resolvedPatientId by remember { mutableStateOf(patientId.ifBlank { "" }) }
    val authViewModel: com.socklet.smritisaathi.ui.onboarding.AuthViewModel = hiltViewModel()

    LaunchedEffect(currentUserId) {
        if (resolvedPatientId.isBlank()) {
            // Real accounts: load their first linked patient from Firestore
            if (currentUserId != null && !isDemoSession) {
                val user = authViewModel.loadUserProfile(currentUserId).getOrNull()
                val firstPatient = user?.linkedPatientIds?.firstOrNull()
                if (firstPatient != null) {
                    resolvedPatientId = firstPatient
                    return@LaunchedEffect
                }
                // Real account with no linked patients keeps the empty state —
                // never fall through to demo data.
            }
            // Demo sessions (anonymous auth): restore the demo patient
            if (isDemoSession) {
                repository.createOrGetDemoPatient().getOrNull()?.let { demo ->
                    resolvedPatientId = demo.id
                }
            }
        }
    }

    val bottomNavItems = listOf(
        FamilyScreen.Home,
        FamilyScreen.Alerts,
        FamilyScreen.Reminders,
        FamilyScreen.Reports
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(imageVector = screen.icon, contentDescription = screen.title)
                        },
                        label = {
                            Text(text = screen.title, style = MaterialTheme.typography.labelMedium)
                        },
                        selected = currentRoute == screen.route,
                        onClick = {
                            innerNavController.navigate(screen.route) {
                                popUpTo(innerNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = innerNavController,
            startDestination = FamilyScreen.Home.route,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            composable(FamilyScreen.Home.route) {
                FamilyDashboardScreen(
                    patientId = resolvedPatientId,
                    repository = repository,
                    onNavigateToPlayInvite = { innerNavController.navigate(FamilyScreen.PlayWithGrandpa.route) },
                    onNavigateToAlerts = { innerNavController.navigate(FamilyScreen.Alerts.route) },
                    onNavigateToReminders = { innerNavController.navigate(FamilyScreen.Reminders.route) },
                    onNavigateToReminiscence = { innerNavController.navigate(FamilyScreen.Reminiscence.route) },
                    onNavigateToReports = { innerNavController.navigate(FamilyScreen.Reports.route) },
                    onSignOut = onSignOut
                )
            }

            composable(FamilyScreen.PlayWithGrandpa.route) {
                PlayWithGrandpaScreen(
                    patientId = resolvedPatientId,
                    repository = repository,
                    onBack = { innerNavController.popBackStack() }
                )
            }

            composable(FamilyScreen.Alerts.route) {
                BehavioralAlertsInboxScreen(
                    patientId = resolvedPatientId,
                    repository = repository,
                    onBack = { innerNavController.popBackStack() }
                )
            }

            composable(FamilyScreen.Reminders.route) {
                FamilyRemindersManagerScreen(
                    patientId = resolvedPatientId,
                    repository = repository,
                    onBack = { innerNavController.popBackStack() }
                )
            }

            composable(FamilyScreen.Reminiscence.route) {
                FamilyReminiscenceManagerScreen(
                    patientId = resolvedPatientId,
                    repository = repository,
                    onBack = { innerNavController.popBackStack() }
                )
            }

            composable(FamilyScreen.Reports.route) {
                FamilyReportScreen(
                    patientId = resolvedPatientId,
                    repository = repository,
                    onBack = { innerNavController.popBackStack() }
                )
            }
        }
    }
}