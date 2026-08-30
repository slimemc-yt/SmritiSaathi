package com.socklet.smritisaathi.ui.family.onboarding

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.Gender
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientBasicDetailsScreen(
    viewModel: PatientOnboardingViewModel,
    onNext: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setPatientPhoto(uri)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        // Photo captured
    }

    var showImageSourceDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .imePadding()
            .padding(Dimensions.ScreenPadding)
    ) {
        Text(
            text = "Patient Details",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        Text(
            text = "Enter the basic information about the patient",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space20)
        ) {
            // Photo Section
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .clickable { showImageSourceDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.patientPhotoUri != null) {
                        // Show selected image
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Patient photo",
                            modifier = Modifier.size(60.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = "Add photo",
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Add Photo",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Name Field
            OutlinedTextField(
                value = uiState.patientName,
                onValueChange = { viewModel.setPatientName(it) },
                label = { Text("Patient Name") },
                placeholder = { Text("Enter full name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Age Field
            OutlinedTextField(
                value = uiState.patientAge,
                onValueChange = { viewModel.setPatientAge(it) },
                label = { Text("Age") },
                placeholder = { Text("Enter age in years") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Cake, contentDescription = null)
                },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Gender Selection
            Text(
                text = "Gender",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Space12)
            ) {
                Gender.values().forEach { gender ->
                    GenderOptionCard(
                        gender = gender,
                        selected = uiState.patientGender == gender,
                        onClick = { viewModel.setPatientGender(gender) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        // Next Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            enabled = uiState.canProceed,
            shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Text("Continue")
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }

    // Image Source Dialog
    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text("Select Photo") },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showImageSourceDialog = false
                                imagePickerLauncher.launch("image/*")
                            }
                            .padding(vertical = Dimensions.Space16),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                        Spacer(modifier = Modifier.width(Dimensions.Space16))
                        Text("Choose from Gallery")
                    }
                    Divider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showImageSourceDialog = false
                                // Camera would need a file provider setup
                                // For now, just gallery
                            }
                            .padding(vertical = Dimensions.Space16),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(modifier = Modifier.width(Dimensions.Space16))
                        Text("Take a Photo")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showImageSourceDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun GenderOptionCard(
    gender: Gender,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(80.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        border = if (selected) {
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.Space12),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = when (gender) {
                    Gender.MALE -> Icons.Default.Male
                    Gender.FEMALE -> Icons.Default.Female
                    Gender.OTHER -> Icons.Default.Person
                },
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = gender.displayName,
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}
