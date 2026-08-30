package com.socklet.smritisaathi.ui.family.onboarding

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.socklet.smritisaathi.domain.model.DementiaStage
import com.socklet.smritisaathi.ui.theme.Dimensions

sealed class OnboardingScreen(val route: String) {
    object BasicDetails : OnboardingScreen("basic_details")
    object DementiaStage : OnboardingScreen("dementia_stage")
    object MedicalReports : OnboardingScreen("medical_reports")
    object FamilyContacts : OnboardingScreen("family_contacts")
    object DoctorHospital : OnboardingScreen("doctor_hospital")
    object DailyRoutine : OnboardingScreen("daily_routine")
    object EmergencyContact : OnboardingScreen("emergency_contact")
    object EnhancedSupport : OnboardingScreen("enhanced_support")
    object Completion : OnboardingScreen("completion")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientOnboardingScreen(
    familyMemberId: String,
    onOnboardingComplete: (patientId: String) -> Unit,
    viewModel: PatientOnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    // Calculate total steps based on dementia stage
    val totalSteps = if (uiState.dementiaStage == DementiaStage.SEVERE) 8 else 7

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Progress Indicator
        if (uiState.currentStep < totalSteps) {
            OnboardingProgressIndicator(
                currentStep = uiState.currentStep,
                totalSteps = totalSteps,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Navigation Graph
        NavHost(
            navController = navController,
            startDestination = OnboardingScreen.BasicDetails.route,
            modifier = Modifier.weight(1f)
        ) {
            composable(
                route = OnboardingScreen.BasicDetails.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                PatientBasicDetailsScreen(
                    viewModel = viewModel,
                    onNext = {
                        viewModel.nextStep()
                        navController.navigate(OnboardingScreen.DementiaStage.route)
                    }
                )
            }

            composable(
                route = OnboardingScreen.DementiaStage.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                DementiaStageScreen(
                    viewModel = viewModel,
                    onNext = {
                        viewModel.nextStep()
                        navController.navigate(OnboardingScreen.MedicalReports.route)
                    },
                    onBack = {
                        viewModel.previousStep()
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = OnboardingScreen.MedicalReports.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                MedicalReportsScreen(
                    viewModel = viewModel,
                    onNext = {
                        viewModel.nextStep()
                        navController.navigate(OnboardingScreen.FamilyContacts.route)
                    },
                    onBack = {
                        viewModel.previousStep()
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = OnboardingScreen.FamilyContacts.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                FamilyContactsScreen(
                    viewModel = viewModel,
                    onNext = {
                        viewModel.nextStep()
                        navController.navigate(OnboardingScreen.DoctorHospital.route)
                    },
                    onBack = {
                        viewModel.previousStep()
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = OnboardingScreen.DoctorHospital.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                DoctorHospitalScreen(
                    viewModel = viewModel,
                    onNext = {
                        viewModel.nextStep()
                        navController.navigate(OnboardingScreen.DailyRoutine.route)
                    },
                    onBack = {
                        viewModel.previousStep()
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = OnboardingScreen.DailyRoutine.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                DailyRoutineScreen(
                    viewModel = viewModel,
                    onNext = {
                        viewModel.nextStep()
                        navController.navigate(OnboardingScreen.EmergencyContact.route)
                    },
                    onBack = {
                        viewModel.previousStep()
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = OnboardingScreen.EmergencyContact.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                EmergencyContactScreen(
                    viewModel = viewModel,
                    onNext = {
                        if (uiState.dementiaStage == DementiaStage.SEVERE) {
                            viewModel.nextStep()
                            navController.navigate(OnboardingScreen.EnhancedSupport.route)
                        } else {
                            // Skip enhanced support for non-severe
                            navController.navigate(OnboardingScreen.Completion.route)
                        }
                    },
                    onBack = {
                        viewModel.previousStep()
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = OnboardingScreen.EnhancedSupport.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                EnhancedSupportScreen(
                    viewModel = viewModel,
                    onNext = {
                        navController.navigate(OnboardingScreen.Completion.route)
                    },
                    onBack = {
                        viewModel.previousStep()
                        navController.popBackStack()
                    },
                    onSkip = {
                        navController.navigate(OnboardingScreen.Completion.route)
                    }
                )
            }

            composable(
                route = OnboardingScreen.Completion.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )
                }
            ) {
                OnboardingCompletionScreen(
                    uiState = uiState,
                    onSave = {
                        viewModel.savePatient(familyMemberId) { patientId ->
                            onOnboardingComplete(patientId)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun OnboardingProgressIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    val stepLabels = listOf("Basic", "Stage", "Reports", "Family", "Doctor", "Routine", "Emergency", "Enhanced")

    Column(
        modifier = modifier.padding(Dimensions.Space16)
    ) {
        // Step counter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Step ${currentStep + 1} of $totalSteps",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${((currentStep.toFloat() / totalSteps) * 100).toInt()}%",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space12))

        // Progress bar
        LinearProgressIndicator(
            progress = (currentStep + 1).toFloat() / totalSteps,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimensions.Space12))

        // Step labels (show only first 5 visible)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.Space4),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stepLabels.take(totalSteps).forEachIndexed { index, label ->
                val isCompleted = index < currentStep
                val isCurrent = index == currentStep

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isCompleted) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        } else if (isCurrent) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                    if (index < 4) { // Show only first 4 labels to avoid crowding
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isCompleted || isCurrent) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OnboardingCompletionScreen(
    uiState: PatientOnboardingUiState,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Scrollable content area
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(Dimensions.Space24))

            Text(
                text = "All Set!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(Dimensions.Space12))

            Text(
                text = "Review the details below and save to complete the patient onboarding.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimensions.Space24))

            // Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(Dimensions.Space20),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
                ) {
                    SummaryRow("Patient Name", uiState.patientName)
                    SummaryRow("Age", uiState.patientAge + " years")
                    SummaryRow("Gender", uiState.patientGender.displayName)
                    SummaryRow("Dementia Stage", "${uiState.dementiaStage.displayName} (Tier ${uiState.dementiaStage.tier})")
                    SummaryRow("Family Contacts", "${uiState.familyContacts.size} contacts")
                    SummaryRow("Medical Reports", "${uiState.medicalReports.size} reports")
                    uiState.selectedDoctor?.let { doctor ->
                        SummaryRow("Assigned Doctor", doctor.name)
                    }
                    uiState.selectedHospital?.let { hospital ->
                        SummaryRow("Hospital", hospital.name)
                    }
                    SummaryRow("SOS Number", uiState.sosNumber)

                    if (uiState.enhancedSupportEnabled) {
                        Spacer(modifier = Modifier.height(Dimensions.Space8))
                        Surface(
                            color = MaterialTheme.colorScheme.errorContainer,
                            shape = MaterialTheme.shapes.small
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Warning,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.onErrorContainer
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Enhanced Support Mode Enabled",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }
                }
            }

            if (uiState.error != null) {
                Spacer(modifier = Modifier.height(Dimensions.Space16))
                Text(
                    text = uiState.error!!,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        // Fixed bottom area for button/progress
        Spacer(modifier = Modifier.height(Dimensions.Space16))

        if (uiState.isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(Dimensions.Space12))
            Text(
                text = "Saving patient data...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.ButtonHeight),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(Dimensions.Space8))
                Text("Save & Complete")
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
