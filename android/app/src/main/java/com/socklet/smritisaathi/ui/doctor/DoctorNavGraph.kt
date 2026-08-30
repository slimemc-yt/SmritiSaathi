package com.socklet.smritisaathi.ui.doctor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.navigation.Screen
import com.socklet.smritisaathi.ui.patient.PatientContainerViewModel
import kotlinx.coroutines.launch

sealed class DoctorScreen(val route: String, val title: String, val icon: ImageVector) {
    object Patients : DoctorScreen("doctor_patients", "Patients", Icons.Default.People)
    object PatientDetail : DoctorScreen("doctor_patient_detail/{patientId}", "Detail", Icons.Default.Assessment)
}

fun NavGraphBuilder.doctorNavGraph(
    navController: NavHostController,
    onSignOut: () -> Unit = {}
) {
    composable(Screen.DoctorHome.route) {
        DoctorMainScreen(navController = navController, onSignOut = onSignOut)
    }
}

@Composable
fun DoctorMainScreen(
    navController: NavHostController,
    onSignOut: () -> Unit = {},
    repository: PatientRepository = hiltViewModel<PatientContainerViewModel>().repository
) {
    val innerNavController = rememberNavController()
    val authViewModel: com.socklet.smritisaathi.ui.onboarding.AuthViewModel = hiltViewModel()
    val authUiState by authViewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    var showSignOutConfirm by remember { mutableStateOf(false) }

    val currentFirebaseUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
    val isDemoSession = currentFirebaseUser == null || currentFirebaseUser.isAnonymous
    val doctorId = currentFirebaseUser?.uid ?: "doctor_1"
    var doctorCode by remember { mutableStateOf(authUiState.currentUser?.doctorCode ?: if (isDemoSession) "DR-8821" else "") }
    var doctorName by remember { mutableStateOf(authUiState.currentUser?.name ?: if (isDemoSession) "Dr. Pranab Baruah, MD" else "Dr. Medical Specialist") }

    LaunchedEffect(doctorId) {
        if (currentFirebaseUser != null && !isDemoSession) {
            val user = authViewModel.loadUserProfile(doctorId).getOrNull()
            if (user != null) {
                doctorCode = user.doctorCode ?: "DR-XXXX"
                doctorName = user.name.ifBlank { "Dr. Medical Specialist" }
                // Publish and guarantee doctor is in public searchable directory
                authViewModel.syncDoctorDirectory(user)
            }
        }
    }

    NavHost(
        navController = innerNavController,
        startDestination = DoctorScreen.Patients.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(DoctorScreen.Patients.route) {
            DoctorDashboardScreen(
                doctorId = doctorId,
                doctorCode = doctorCode,
                doctorName = doctorName,
                repository = repository,
                onSelectPatient = { patientId ->
                    innerNavController.navigate("doctor_patient_detail/$patientId")
                },
                onSignOut = { showSignOutConfirm = true }
            )
        }

        composable(DoctorScreen.PatientDetail.route) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: "default_patient"
            DoctorPatientDetailScreen(
                patientId = patientId,
                repository = repository,
                onBack = { innerNavController.popBackStack() }
            )
        }
    }

    if (showSignOutConfirm) {
        AlertDialog(
            onDismissRequest = { showSignOutConfirm = false },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        authViewModel.signOut()
                        showSignOutConfirm = false
                        onSignOut()
                    }
                }) {
                    Text("Sign Out", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSignOutConfirm = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Sign Out") },
            text = { Text("Are you sure you want to sign out? Your data remains safe in Firestore.") }
        )
    }
}
