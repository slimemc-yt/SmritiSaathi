package com.socklet.smritisaathi.ui.family

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.LifeStage
import com.socklet.smritisaathi.domain.model.ReminiscenceContent
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.ui.theme.Dimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyReminiscenceManagerScreen(
    patientId: String,
    repository: PatientRepository,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val memories by repository.getReminiscenceContentFlow(patientId).collectAsStateWithLifecycle(initialValue = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var personTag by remember { mutableStateOf("") }
    var uploadedBy by remember { mutableStateOf("") }
    var selectedLifeStage by remember { mutableStateOf(LifeStage.FAMILY_CHILDREN) }
    var lifeStageDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Memory", tint = Color.White)
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
                    text = "Family Reminiscence Corner",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(Dimensions.Space16))

            Text(
                text = "Memories uploaded here automatically power your loved one's Life Stage Game, Face Recognition, and Voice Album.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Dimensions.Space16))

            if (memories.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.PhotoLibrary,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Memories Added Yet",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tap + below to add your first heartwarming family memory.",
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
                    items(memories) { memory ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    ) {
                                        Text(
                                            text = memory.lifeStage.displayName,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }

                                    Text(
                                        text = "By ${memory.uploadedBy}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = memory.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = memory.description,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                if (memory.personTag.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Tagged: ${memory.personTag}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.secondary
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
            title = { Text("Add Family Memory") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Memory Title (e.g. 'Aarav's 5th Birthday')") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Short Story / Prompt") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = personTag,
                        onValueChange = { personTag = it },
                        label = { Text("People in Memory (e.g. 'Aarav, Sunita')") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = uploadedBy,
                        onValueChange = { uploadedBy = it },
                        label = { Text("Your Name / Relation") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Life Stage Selector
                    ExposedDropdownMenuBox(
                        expanded = lifeStageDropdownExpanded,
                        onExpandedChange = { lifeStageDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = selectedLifeStage.displayName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Life Stage") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = lifeStageDropdownExpanded) }
                        )
                        ExposedDropdownMenu(
                            expanded = lifeStageDropdownExpanded,
                            onDismissRequest = { lifeStageDropdownExpanded = false }
                        ) {
                            LifeStage.values().forEach { stage ->
                                DropdownMenuItem(
                                    text = { Text(stage.displayName) },
                                    onClick = {
                                        selectedLifeStage = stage
                                        lifeStageDropdownExpanded = false
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
                                repository.saveReminiscenceContent(
                                    patientId,
                                    ReminiscenceContent(
                                        patientId = patientId,
                                        title = title,
                                        description = description,
                                        personTag = personTag,
                                        uploadedBy = uploadedBy.ifBlank { "Family Member" },
                                        lifeStage = selectedLifeStage
                                    )
                                )
                                showAddDialog = false
                                title = ""
                                description = ""
                                personTag = ""
                                uploadedBy = ""
                            }
                        }
                    },
                    enabled = title.isNotBlank()
                ) {
                    Text("Add Memory")
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
