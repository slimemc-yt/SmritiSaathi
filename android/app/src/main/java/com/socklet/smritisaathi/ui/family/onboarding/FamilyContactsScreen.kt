package com.socklet.smritisaathi.ui.family.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.FamilyContact
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyContactsScreen(
    viewModel: PatientOnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddContactDialog by remember { mutableStateOf(false) }
    var editingIndex by remember { mutableStateOf(-1) }

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
                text = "Family Contacts",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        Text(
            text = "Add family members who will be used in the face-recognition game and receive alerts.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        if (uiState.familyContacts.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(Dimensions.Space16))
                    Text(
                        text = "No contacts added yet",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(Dimensions.Space8))
                    Text(
                        text = "Add at least one family contact to continue",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            // List of contacts
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
            ) {
                items(uiState.familyContacts.withIndex().toList()) { (index, contact) ->
                    FamilyContactCard(
                        contact = contact,
                        onEdit = {
                            editingIndex = index
                            viewModel.setEditingContact(index)
                            showAddContactDialog = true
                        },
                        onRemove = { viewModel.removeFamilyContact(index) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        // Add Contact Button
        OutlinedButton(
            onClick = {
                editingIndex = -1
                viewModel.clearEditingContact()
                showAddContactDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Text("Add Family Contact")
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        // Continue Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            enabled = uiState.familyContacts.isNotEmpty(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Text("Continue")
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }

    // Add/Edit Contact Dialog
    if (showAddContactDialog) {
        AddContactDialog(
            isEditing = editingIndex >= 0,
            initialName = uiState.contactName,
            initialRelationship = uiState.contactRelationship,
            initialPhone = uiState.contactPhone,
            onDismiss = {
                showAddContactDialog = false
                viewModel.clearEditingContact()
            },
            onSave = { name, relationship, phone ->
                viewModel.setContactName(name)
                viewModel.setContactRelationship(relationship)
                viewModel.setContactPhone(phone)
                if (editingIndex >= 0) {
                    viewModel.updateFamilyContact(editingIndex)
                } else {
                    viewModel.addFamilyContact()
                }
                showAddContactDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddContactDialog(
    isEditing: Boolean,
    initialName: String,
    initialRelationship: String,
    initialPhone: String,
    onDismiss: () -> Unit,
    onSave: (name: String, relationship: String, phone: String) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var relationship by remember { mutableStateOf(initialRelationship) }
    var phone by remember { mutableStateOf(initialPhone) }

    val relationships = listOf("Spouse", "Son", "Daughter", "Father", "Mother", "Brother", "Sister", "Other")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEditing) "Edit Contact" else "Add Family Contact") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensions.Space16)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    singleLine = true
                )

                // Relationship dropdown
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = relationship,
                        onValueChange = {},
                        label = { Text("Relationship") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        leadingIcon = { Icon(Icons.Default.FamilyRestroom, contentDescription = null) },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        relationships.forEach { rel ->
                            DropdownMenuItem(
                                text = { Text(rel) },
                                onClick = {
                                    relationship = rel
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(name, relationship, phone) },
                enabled = name.isNotBlank() && relationship.isNotBlank() && phone.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FamilyContactCard(
    contact: FamilyContact,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.take(1).uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(Dimensions.Space16))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${contact.relationship} • ${contact.phoneNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
