package com.socklet.smritisaathi.ui.family.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyContactScreen(
    viewModel: PatientOnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val relationships = listOf("Spouse", "Son", "Daughter", "Father", "Mother", "Brother", "Sister", "Doctor", "Neighbor", "Other")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.ScreenPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Text(
                text = "Emergency Contact",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        Text(
            text = "Set up emergency contacts and SOS number for quick assistance",
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
            // Emergency Contact Section
            Text(
                text = "Primary Emergency Contact",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            OutlinedTextField(
                value = uiState.emergencyContactName,
                onValueChange = { viewModel.setEmergencyContactName(it) },
                label = { Text("Contact Name") },
                placeholder = { Text("Enter contact name") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true
            )

            // Relationship dropdown
            var relationshipExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = relationshipExpanded,
                onExpandedChange = { relationshipExpanded = it }
            ) {
                OutlinedTextField(
                    value = uiState.emergencyContactRelationship,
                    onValueChange = {},
                    label = { Text("Relationship") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    leadingIcon = { Icon(Icons.Default.FamilyRestroom, contentDescription = null) },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = relationshipExpanded) }
                )
                ExposedDropdownMenu(
                    expanded = relationshipExpanded,
                    onDismissRequest = { relationshipExpanded = false }
                ) {
                    relationships.forEach { rel ->
                        DropdownMenuItem(
                            text = { Text(rel) },
                            onClick = {
                                viewModel.setEmergencyContactRelationship(rel)
                                relationshipExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = uiState.emergencyContactPhone,
                onValueChange = { viewModel.setEmergencyContactPhone(it) },
                label = { Text("Phone Number") },
                placeholder = { Text("Enter 10-digit phone number") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                singleLine = true
            )

            Divider(modifier = Modifier.padding(vertical = Dimensions.Space8))

            // SOS Number Section
            Text(
                text = "SOS Number",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(Dimensions.Space16),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(Dimensions.Space12))
                    Text(
                        text = "This number will be called automatically when the SOS button is pressed. It can be different from the emergency contact above.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Space8))

            OutlinedTextField(
                value = uiState.sosNumber,
                onValueChange = { viewModel.setSosNumber(it) },
                label = { Text("SOS Number") },
                placeholder = { Text("Enter SOS phone number") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Emergency, contentDescription = null) },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                singleLine = true
            )

            // Info card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(Dimensions.Space16),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(Dimensions.Space12))
                    Column {
                        Text(
                            text = "How it works:",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• SOS button appears on patient's home screen\n" +
                                    "• One tap calls the SOS number\n" +
                                    "• Emergency contact receives SMS alert\n" +
                                    "• Location is shared automatically",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        // Continue Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            enabled = uiState.emergencyContactName.isNotBlank() &&
                    uiState.emergencyContactPhone.isNotBlank() &&
                    uiState.sosNumber.isNotBlank(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Text("Continue")
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}
