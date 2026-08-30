package com.socklet.smritisaathi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.socklet.smritisaathi.ui.onboarding.AuthViewModel
import com.socklet.smritisaathi.ui.onboarding.LanguageSelectionScreen
import com.socklet.smritisaathi.ui.onboarding.LoginScreen
import com.socklet.smritisaathi.ui.onboarding.OtpVerificationScreen
import com.socklet.smritisaathi.ui.onboarding.RoleSelectionScreen
import com.socklet.smritisaathi.ui.onboarding.SplashScreen
import com.socklet.smritisaathi.ui.patient.patientNavGraph
import com.socklet.smritisaathi.ui.family.familyNavGraph
import com.socklet.smritisaathi.ui.doctor.doctorNavGraph
import com.socklet.smritisaathi.domain.model.UserRole

@Composable
fun SmritiSaathiNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Shared onboarding flow
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLanguage = {
                    navController.navigate(Screen.LanguageSelection.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToPatientHome = {
                    navController.navigate(Screen.PatientHome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToFamilyHome = {
                    navController.navigate(Screen.FamilyHome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToDoctorHome = {
                    navController.navigate(Screen.DoctorHome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.LanguageSelection.route) {
            LanguageSelectionScreen(
                onLanguageSelected = {
                    navController.navigate(Screen.RoleSelection.route)
                }
            )
        }

        composable(Screen.RoleSelection.route) {
            RoleSelectionScreen(
                onPatientSelected = {
                    navController.navigate(Screen.PatientPairing.route)
                },
                onFamilySelected = {
                    navController.navigate(Screen.Login.createRoute(UserRole.FAMILY))
                },
                onDoctorSelected = {
                    navController.navigate(Screen.Login.createRoute(UserRole.DOCTOR))
                },
                onDemoSelected = {
                    navController.navigate(Screen.PatientHome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.PatientPairing.route) {
            com.socklet.smritisaathi.ui.onboarding.PatientPairingScreen(
                onPairingSuccess = {
                    navController.navigate(Screen.PatientHome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Login.routeWithArgs,
            arguments = listOf(
                navArgument("role") {
                    type = NavType.StringType
                    defaultValue = "FAMILY"
                }
            )
        ) { backStackEntry ->
            val roleStr = backStackEntry.arguments?.getString("role") ?: "FAMILY"
            val initialRole = try { UserRole.valueOf(roleStr) } catch (_: Exception) { UserRole.FAMILY }
            LoginScreen(
                initialRole = initialRole,
                onLoginSuccess = { role, isNewUser ->
                    val destination = when (role) {
                        UserRole.PATIENT -> Screen.PatientHome.route
                        UserRole.FAMILY -> if (isNewUser) Screen.FamilyAddPatient.route else Screen.FamilyHome.route
                        UserRole.DOCTOR -> Screen.DoctorHome.route
                    }
                    navController.navigate(destination) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToOtp = { phoneNumber ->
                    navController.navigate(Screen.OtpVerification.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                initialRole = null,
                onLoginSuccess = { role, isNewUser ->
                    val destination = when (role) {
                        UserRole.PATIENT -> Screen.PatientHome.route
                        UserRole.FAMILY -> if (isNewUser) Screen.FamilyAddPatient.route else Screen.FamilyHome.route
                        UserRole.DOCTOR -> Screen.DoctorHome.route
                    }
                    navController.navigate(destination) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToOtp = { phoneNumber ->
                    navController.navigate(Screen.OtpVerification.route)
                }
            )
        }

        composable(Screen.OtpVerification.route) {
            OtpVerificationScreen(
                onVerificationSuccess = { role ->
                    val destination = when (role) {
                        UserRole.PATIENT -> Screen.PatientHome.route
                        UserRole.FAMILY -> Screen.FamilyHome.route
                        UserRole.DOCTOR -> Screen.DoctorHome.route
                    }
                    navController.navigate(destination) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Separate navigation graphs for each role
        patientNavGraph(navController)
        familyNavGraph(
            navController,
            patientId = null,
            onSignOut = {
                navController.navigate(Screen.RoleSelection.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        doctorNavGraph(
            navController,
            onSignOut = {
                navController.navigate(Screen.RoleSelection.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
}

// Keep UserRole in navigation for backward compatibility, but alias to domain model
typealias UserRoleNav = com.socklet.smritisaathi.domain.model.UserRole
