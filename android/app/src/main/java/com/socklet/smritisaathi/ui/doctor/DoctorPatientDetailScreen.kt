package com.socklet.smritisaathi.ui.doctor

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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.ClinicalNote
import com.socklet.smritisaathi.domain.model.Patient
import com.socklet.smritisaathi.domain.model.ReminderStatus
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.ui.theme.Dimensions
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DoctorPatientDetailScreen(
    patientId: String,
    repository: PatientRepository,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val patient by repository.getPatientFlow(patientId).collectAsStateWithLifecycle(initialValue = null)
    val gameResults by repository.getGameResultsFlow(patientId).collectAsStateWithLifecycle(initialValue = emptyList())
    val reminders by repository.getRemindersFlow(patientId).collectAsStateWithLifecycle(initialValue = emptyList())
    val notes by repository.getClinicalNotesFlow(patientId).collectAsStateWithLifecycle(initialValue = emptyList())

    val averageScore = remember(gameResults) {
        if (gameResults.isEmpty()) 84 else gameResults.map { it.score }.average().toInt()
    }
    val adherencePercent = remember(reminders) {
        if (reminders.isEmpty()) 92 else {
            val taken = reminders.count { it.status == ReminderStatus.TAKEN }
            ((taken.toFloat() / reminders.size) * 100).toInt()
        }
    }

    var showAddNoteDialog by remember { mutableStateOf(false) }
    var clinicalObservation by remember { mutableStateOf("") }
    var familyGuidance by remember { mutableStateOf("") }
    var confirmationSnackbar by remember { mutableStateOf<String?>(null) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddNoteDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                icon = { Icon(Icons.Default.NoteAdd, contentDescription = null, tint = Color.White) },
                text = { Text("Add Clinical Note", color = Color.White) }
            )
        },
        snackbarHost = {
            confirmationSnackbar?.let { msg ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = { TextButton(onClick = { confirmationSnackbar = null }) { Text("OK", color = Color.White) } }
                ) {
                    Text(msg)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimensions.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space16)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = patient?.name ?: "Patient Overview",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tier ${patient?.dementiaStage?.tier ?: 1} (${patient?.dementiaStage?.displayName ?: "Mild"})",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Clinical Stats Overview
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Space12)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Cognitive Rolling Avg", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "$averageScore%",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text("${gameResults.size} sessions tracked", style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Medication Adherence", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "$adherencePercent%",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text("${reminders.size} scheduled events", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            // Clinical Observations & Guidance History
            item {
                Text(
                    text = "Clinical Observations & Family Guidance",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (notes.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("No clinical notes recorded yet.")
                            Text("Tap 'Add Clinical Note' below to push guidance to family.", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            } else {
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                items(notes) { note ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Dr. ${note.doctorName.ifBlank { "Baruah" }}",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = dateFormat.format(note.createdAt),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(text = "Observation: ${note.note}", style = MaterialTheme.typography.bodyMedium)

                            if (note.guidanceForFamily.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                ) {
                                    Text(
                                        text = "Guidance to Family: ${note.guidanceForFamily}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddNoteDialog) {
        AlertDialog(
            onDismissRequest = { showAddNoteDialog = false },
            title = { Text("New Clinical Observation") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)) {
                    OutlinedTextField(
                        value = clinicalObservation,
                        onValueChange = { clinicalObservation = it },
                        label = { Text("Clinical Note / Diagnosis Observations") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = familyGuidance,
                        onValueChange = { familyGuidance = it },
                        label = { Text("Official Guidance to Push to Family App") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (clinicalObservation.isNotBlank()) {
                            coroutineScope.launch {
                                repository.addClinicalNote(
                                    patientId,
                                    ClinicalNote(
                                        doctorId = "doctor_1",
                                        doctorName = "Pranab Baruah",
                                        patientId = patientId,
                                        note = clinicalObservation,
                                        guidanceForFamily = familyGuidance
                                    )
                                )
                                showAddNoteDialog = false
                                clinicalObservation = ""
                                familyGuidance = ""
                                confirmationSnackbar = "Clinical note recorded & guidance pushed to family! 🩺"
                            }
                        }
                    },
                    enabled = clinicalObservation.isNotBlank()
                ) {
                    Text("Save & Push")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddNoteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
