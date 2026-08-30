package com.socklet.smritisaathi.ui.family.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.MealTime
import com.socklet.smritisaathi.domain.model.MedicineTime
import com.socklet.smritisaathi.domain.model.NapTime
import com.socklet.smritisaathi.ui.theme.Dimensions
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyRoutineScreen(
    viewModel: PatientOnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showNapDialog by remember { mutableStateOf(false) }
    var showMealDialog by remember { mutableStateOf(false) }
    var showMedicineDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var selectedTimeType by remember { mutableStateOf("wake") }

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
                text = "Daily Routine",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        Text(
            text = "Set up the patient's daily schedule. All times can be edited later.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space20)
        ) {
            // Wake Time
            item {
                TimeSettingCard(
                    title = "Wake Time",
                    time = uiState.wakeTime,
                    icon = Icons.Default.WbSunny,
                    onClick = {
                        selectedTimeType = "wake"
                        showTimePickerDialog = true
                    }
                )
            }

            // Sleep Time
            item {
                TimeSettingCard(
                    title = "Sleep Time",
                    time = uiState.sleepTime,
                    icon = Icons.Default.Bedtime,
                    onClick = {
                        selectedTimeType = "sleep"
                        showTimePickerDialog = true
                    }
                )
            }

            // Nap Times Section
            item {
                SectionHeader(
                    title = "Nap Times",
                    subtitle = "${uiState.napTimes.size} naps configured",
                    onAddClick = { showNapDialog = true }
                )
            }

            items(
                items = uiState.napTimes,
                key = { it.id }
            ) { nap ->
                NapTimeCard(
                    nap = nap,
                    onRemove = { viewModel.removeNapTime(uiState.napTimes.indexOf(nap)) }
                )
            }

            // Meal Times Section
            item {
                Spacer(modifier = Modifier.height(Dimensions.Space8))
                SectionHeader(
                    title = "Meal Times",
                    subtitle = "${uiState.mealTimes.size} meals configured",
                    onAddClick = { showMealDialog = true }
                )
            }

            items(
                items = uiState.mealTimes,
                key = { it.id }
            ) { meal ->
                MealTimeCard(
                    meal = meal,
                    onRemove = { viewModel.removeMealTime(uiState.mealTimes.indexOf(meal)) }
                )
            }

            // Medicine Times Section
            item {
                Spacer(modifier = Modifier.height(Dimensions.Space8))
                SectionHeader(
                    title = "Medicine Times",
                    subtitle = "${uiState.medicineTimes.size} medicines configured",
                    onAddClick = { showMedicineDialog = true }
                )
            }

            items(
                items = uiState.medicineTimes,
                key = { it.id }
            ) { medicine ->
                MedicineTimeCard(
                    medicine = medicine,
                    onRemove = { viewModel.removeMedicineTime(uiState.medicineTimes.indexOf(medicine)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        // Continue Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            enabled = uiState.wakeTime.isNotBlank() && uiState.sleepTime.isNotBlank(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Text("Continue")
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }

    // Time Picker Dialog
    if (showTimePickerDialog) {
        TimePickerDialog(
            title = if (selectedTimeType == "wake") "Set Wake Time" else "Set Sleep Time",
            onDismiss = { showTimePickerDialog = false },
            onTimeSelected = { hour, minute ->
                val time = String.format("%02d:%02d", hour, minute)
                if (selectedTimeType == "wake") {
                    viewModel.setWakeTime(time)
                } else {
                    viewModel.setSleepTime(time)
                }
                showTimePickerDialog = false
            }
        )
    }

    // Nap Dialog
    if (showNapDialog) {
        NapTimeDialog(
            onDismiss = { showNapDialog = false },
            onAdd = { nap ->
                viewModel.addNapTime(nap)
                showNapDialog = false
            }
        )
    }

    // Meal Dialog
    if (showMealDialog) {
        MealTimeDialog(
            onDismiss = { showMealDialog = false },
            onAdd = { meal ->
                viewModel.addMealTime(meal)
                showMealDialog = false
            }
        )
    }

    // Medicine Dialog
    if (showMedicineDialog) {
        MedicineTimeDialog(
            onDismiss = { showMedicineDialog = false },
            onAdd = { medicine ->
                viewModel.addMedicineTime(medicine)
                showMedicineDialog = false
            }
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(onClick = onAddClick) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeSettingCard(
    title: String,
    time: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(Dimensions.Space16))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (time.isNotBlank()) formatTime12Hour(time) else "Tap to set",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (time.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(Icons.Default.Edit, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NapTimeCard(
    nap: NapTime,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Bedtime, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
            Spacer(modifier = Modifier.width(Dimensions.Space12))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${formatTime12Hour(nap.startTime)} - ${formatTime12Hour(nap.endTime)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MealTimeCard(
    meal: MealTime,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Restaurant, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(Dimensions.Space12))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = meal.type,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = formatTime12Hour(meal.time),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MedicineTimeCard(
    medicine: MedicineTime,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Medication, contentDescription = null, tint = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.width(Dimensions.Space12))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medicine.name,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "${medicine.dosage} at ${formatTime12Hour(medicine.time)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    title: String,
    onDismiss: () -> Unit,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    var hour by remember { mutableStateOf(7) }
    var minute by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Hour picker
                var hourExpanded by remember { mutableStateOf(false) }
                var minuteExpanded by remember { mutableStateOf(false) }

                Column {
                    OutlinedTextField(
                        value = String.format("%02d", hour),
                        onValueChange = {},
                        modifier = Modifier.width(80.dp),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { hourExpanded = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = hourExpanded,
                        onDismissRequest = { hourExpanded = false }
                    ) {
                        (0..23).forEach { h ->
                            DropdownMenuItem(
                                text = { Text(String.format("%02d", h)) },
                                onClick = {
                                    hour = h
                                    hourExpanded = false
                                }
                            )
                        }
                    }
                }

                Text(
                    text = ":",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Column {
                    OutlinedTextField(
                        value = String.format("%02d", minute),
                        onValueChange = {},
                        modifier = Modifier.width(80.dp),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { minuteExpanded = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = minuteExpanded,
                        onDismissRequest = { minuteExpanded = false }
                    ) {
                        (0..59 step 5).forEach { m ->
                            DropdownMenuItem(
                                text = { Text(String.format("%02d", m)) },
                                onClick = {
                                    minute = m
                                    minuteExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onTimeSelected(hour, minute) }) {
                Text("Set")
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
private fun NapTimeDialog(
    onDismiss: () -> Unit,
    onAdd: (NapTime) -> Unit
) {
    var startTime by remember { mutableStateOf("14:00") }
    var endTime by remember { mutableStateOf("15:30") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Nap Time") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)) {
                Text("Elderly patients often have varying nap patterns. You can add multiple naps.", style = MaterialTheme.typography.bodyMedium)
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start Time (HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("End Time (HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onAdd(NapTime(
                    id = UUID.randomUUID().toString(),
                    startTime = startTime,
                    endTime = endTime
                ))
            }) {
                Text("Add")
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
private fun MealTimeDialog(
    onDismiss: () -> Unit,
    onAdd: (MealTime) -> Unit
) {
    var mealType by remember { mutableStateOf("Breakfast") }
    var time by remember { mutableStateOf("08:00") }
    var expanded by remember { mutableStateOf(false) }

    val mealTypes = listOf("Breakfast", "Morning Snacks", "Lunch", "Evening Snacks", "Dinner", "Bedtime Snacks")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Meal Time") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = mealType,
                        onValueChange = {},
                        label = { Text("Meal Type") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        mealTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    mealType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time (HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onAdd(MealTime(
                    id = UUID.randomUUID().toString(),
                    type = mealType,
                    time = time
                ))
            }) {
                Text("Add")
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
private fun MedicineTimeDialog(
    onDismiss: () -> Unit,
    onAdd: (MedicineTime) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("08:00") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Medicine") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Medicine Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = dosage,
                    onValueChange = { dosage = it },
                    label = { Text("Dosage") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time (HH:mm)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (Optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAdd(MedicineTime(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        dosage = dosage,
                        time = time,
                        notes = notes.ifBlank { null }
                    ))
                },
                enabled = name.isNotBlank() && dosage.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun formatTime12Hour(time24: String): String {
    return try {
        val parts = time24.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()
        val amPm = if (hour >= 12) "PM" else "AM"
        val hour12 = if (hour % 12 == 0) 12 else hour % 12
        String.format("%d:%02d %s", hour12, minute, amPm)
    } catch (e: Exception) {
        time24
    }
}
