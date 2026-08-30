package com.socklet.smritisaathi.ui.family

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.Reminder
import com.socklet.smritisaathi.domain.model.ReminderStatus
import com.socklet.smritisaathi.domain.model.ReminderType
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.ui.theme.Dimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyRemindersManagerScreen(
    patientId: String,
    repository: PatientRepository,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val reminders by repository.getRemindersFlow(patientId).collectAsStateWithLifecycle(initialValue = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var medicineName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var scheduledTime by remember { mutableStateOf("08:00 AM") }
    var selectedType by remember { mutableStateOf(ReminderType.MEDICATION) }
    var typeDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Reminder", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimensions.ScreenPadding)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Routine & Medication Schedule",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(Dimensions.Space16))

            if (reminders.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Reminders Configured",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tap + to add medications, meals, or routine alerts.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
                ) {
                    items(reminders) { reminder ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
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
                                                    else -> Icons.Default.Alarm
                                                },
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Text(
                                            text = reminder.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Time: ${reminder.scheduledTime} • ${reminder.dosage ?: "Routine"}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = when (reminder.status) {
                                        ReminderStatus.TAKEN -> Color(0xFFE8F5E9)
                                        ReminderStatus.HELP_REQUESTED -> Color(0xFFFFEBEE)
                                        else -> MaterialTheme.colorScheme.surface
                                    }
                                ) {
                                    Text(
                                        text = reminder.status.displayName,
                                        color = when (reminder.status) {
                                            ReminderStatus.TAKEN -> Color(0xFF2E7D32)
                                            ReminderStatus.HELP_REQUESTED -> Color(0xFFC62828)
                                            else -> MaterialTheme.colorScheme.onSurface
                                        },
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Reminder / Medicine") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Reminder Title (e.g. 'Morning BP Meds')") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = scheduledTime,
                        onValueChange = { scheduledTime = it },
                        label = { Text("Time (e.g. '08:30 AM')") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = dosage,
                        onValueChange = { dosage = it },
                        label = { Text("Dosage / Instructions (e.g. '1 tablet after breakfast')") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenuBox(
                        expanded = typeDropdownExpanded,
                        onExpandedChange = { typeDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = selectedType.displayName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Reminder Type") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeDropdownExpanded) }
                        )
                        ExposedDropdownMenu(
                            expanded = typeDropdownExpanded,
                            onDismissRequest = { typeDropdownExpanded = false }
                        ) {
                            ReminderType.values().forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type.displayName) },
                                    onClick = {
                                        selectedType = type
                                        typeDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            coroutineScope.launch {
                                repository.saveReminder(
                                    patientId,
                                    Reminder(
                                        patientId = patientId,
                                        title = title,
                                        scheduledTime = scheduledTime,
                                        dosage = dosage,
                                        type = selectedType
                                    )
                                )
                                showAddDialog = false
                                title = ""
                                dosage = ""
                            }
                        }
                    },
                    enabled = title.isNotBlank()
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
