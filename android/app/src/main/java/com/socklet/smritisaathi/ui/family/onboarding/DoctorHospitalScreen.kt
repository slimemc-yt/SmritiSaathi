package com.socklet.smritisaathi.ui.family.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.Doctor
import com.socklet.smritisaathi.domain.model.Hospital
import com.socklet.smritisaathi.domain.model.User
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorHospitalScreen(
    viewModel: PatientOnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddHospitalDialog by remember { mutableStateOf(false) }
    var showAddDoctorDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val registeredDoctors = uiState.registeredDoctors
    val isSearching = uiState.isSearchingDoctors

    val filteredLocalDoctors = remember(uiState.doctors, searchQuery) {
        if (searchQuery.isBlank()) uiState.doctors
        else uiState.doctors.filter { doctor ->
            doctor.name.contains(searchQuery, ignoreCase = true) ||
                    doctor.specialization.contains(searchQuery, ignoreCase = true) ||
                    doctor.hospitalName.contains(searchQuery, ignoreCase = true)
        }
    }

    val filteredHospitals = remember(uiState.hospitals, searchQuery) {
        if (searchQuery.isBlank()) uiState.hospitals
        else uiState.hospitals.filter { hospital ->
            hospital.name.contains(searchQuery, ignoreCase = true) ||
                    hospital.address.contains(searchQuery, ignoreCase = true)
        }
    }

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
                text = "Doctor & Hospital",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        Text(
            text = "Search by Doctor Name or Doctor Code (e.g. DR-8821). A pending request will be sent to the doctor for approval.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        // Search Bar with live Firestore query
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.setSearchQuery(it)
                viewModel.searchRegisteredDoctors(it)
            },
            label = { Text("Search by Doctor Name or Code (e.g. DR-8821)") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        viewModel.setSearchQuery("")
                        viewModel.searchRegisteredDoctors("")
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space16)
        ) {
            // Selected Doctor Card
            if (uiState.selectedDoctor != null) {
                item {
                    SelectedDoctorCard(
                        doctor = uiState.selectedDoctor!!,
                        onRemove = { viewModel.selectDoctor(null) }
                    )
                }
            }

            // Selected Hospital Card
            if (uiState.selectedHospital != null) {
                item {
                    SelectedHospitalCard(
                        hospital = uiState.selectedHospital!!,
                        onRemove = { viewModel.selectHospital(null) }
                    )
                }
            }

            // Loading or Empty feedback for registered doctor search
            if (isSearching) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            } else if (searchQuery.isNotBlank() && registeredDoctors.isEmpty()) {
                item {
                    Text(
                        text = "No registered doctors found matching '$searchQuery'. Check Doctor Code format (e.g. DR-8821).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Registered Doctors Section (from Firestore)
            if (registeredDoctors.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Registered Doctors",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "Live Network",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                items(registeredDoctors) { doctorUser ->
                    val isSelected = uiState.selectedDoctor?.id == doctorUser.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectRegisteredDoctor(doctorUser) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    Icons.Default.MedicalServices,
                                    contentDescription = null,
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = doctorUser.name.ifBlank { "Dr. Medical Specialist" },
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Code: ${doctorUser.doctorCode ?: "DR-XXXX"} • ${doctorUser.specialty ?: "Neurologist"}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = doctorUser.hospitalName ?: "Regional Medical Center",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            if (isSelected) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            // Local / Hospital Directory Doctors Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hospital Directory Doctors",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = { showAddDoctorDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add New")
                    }
                }
            }

            items(filteredLocalDoctors.take(4)) { doctor ->
                DoctorCard(
                    doctor = doctor,
                    selected = uiState.selectedDoctor?.id == doctor.id,
                    onClick = { viewModel.selectDoctor(doctor) }
                )
            }

            // Hospitals Section
            item {
                Spacer(modifier = Modifier.height(Dimensions.Space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hospitals",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = { showAddHospitalDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add New")
                    }
                }
            }

            items(filteredHospitals.take(5)) { hospital ->
                HospitalCard(
                    hospital = hospital,
                    selected = uiState.selectedHospital?.id == hospital.id,
                    onClick = { viewModel.selectHospital(hospital) }
                )
            }

            // Fallback option
            item {
                Spacer(modifier = Modifier.height(Dimensions.Space16))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Space16)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(Dimensions.Space8))
                            Text(
                                text = "Cannot find your doctor or hospital?",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.height(Dimensions.Space8))
                        Text(
                            text = "You can add custom medical providers or proceed now and assign them later.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(Dimensions.Space12))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Dimensions.Space8)
                        ) {
                            OutlinedButton(
                                onClick = { showAddDoctorDialog = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add Doctor")
                            }
                            OutlinedButton(
                                onClick = { showAddHospitalDialog = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add Hospital")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        // Continue Button - Always enabled so users can proceed
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Text(if (uiState.selectedDoctor != null || uiState.selectedHospital != null) "Continue" else "Continue (Assign Later)")
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }

    // Add Hospital Dialog
    if (showAddHospitalDialog) {
        AddHospitalDialog(
            onDismiss = { showAddHospitalDialog = false },
            onAdd = { hospital ->
                showAddHospitalDialog = false
                // Add hospital and auto-select it
                coroutineScope.launch {
                    val result = viewModel.addCustomHospital(hospital)
                    if (result.isSuccess) {
                        result.getOrNull()?.let { viewModel.selectHospital(it) }
                    }
                }
            }
        )
    }

    // Add Doctor Dialog
    if (showAddDoctorDialog) {
        AddDoctorDialog(
            hospitals = uiState.hospitals,
            onDismiss = { showAddDoctorDialog = false },
            onAdd = { doctor ->
                showAddDoctorDialog = false
                // Add doctor and auto-select it
                coroutineScope.launch {
                    val result = viewModel.addCustomDoctor(doctor)
                    if (result.isSuccess) {
                        result.getOrNull()?.let { viewModel.selectDoctor(it) }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectedDoctorCard(
    doctor: Doctor,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(Dimensions.Space16)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(Dimensions.Space12))
                    Column {
                        Text(
                            text = "Selected Doctor for Assignment",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = doctor.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "${if (!doctor.doctorCode.isNullOrBlank()) "Code: ${doctor.doctorCode} • " else ""}${doctor.specialization} • ${doctor.hospitalName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
                IconButton(onClick = onRemove) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
            ) {
                Text(
                    text = "ℹ️ A pending connection request will be sent to the doctor. They will review and accept access to patient metrics.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectedHospitalCard(
    hospital: Hospital,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(Dimensions.Space12))
                Column {
                    Text(
                        text = "Assigned Hospital",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = hospital.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = hospital.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DoctorCard(
    doctor: Doctor,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
                Spacer(modifier = Modifier.width(Dimensions.Space12))
                Column {
                    Text(
                        text = doctor.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (selected) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                    Text(
                        text = doctor.specialization,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selected) {
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        }
                    )
                    Text(
                        text = doctor.hospitalName,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selected) {
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        }
                    )
                }
            }
            if (selected) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HospitalCard(
    hospital: Hospital,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Default.LocalHospital,
                    contentDescription = null,
                    tint = if (selected) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
                Spacer(modifier = Modifier.width(Dimensions.Space12))
                Column {
                    Text(
                        text = hospital.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (selected) {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                    Text(
                        text = hospital.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selected) {
                            MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        }
                    )
                }
            }
            if (selected) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddHospitalDialog(
    onDismiss: () -> Unit,
    onAdd: (Hospital) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Hospital") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Hospital Name *") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("District / Location *") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && address.isNotBlank()) {
                        onAdd(
                            Hospital(
                                name = name,
                                address = address,
                                phone = phone
                            )
                        )
                    }
                },
                enabled = name.isNotBlank() && address.isNotBlank()
            ) {
                Text("Add Hospital")
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
private fun AddDoctorDialog(
    hospitals: List<Hospital>,
    onDismiss: () -> Unit,
    onAdd: (Doctor) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var specialization by remember { mutableStateOf("Neurologist") }
    var hospitalName by remember { mutableStateOf(hospitals.firstOrNull()?.name ?: "Guwahati Medical College") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Doctor") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Doctor Name *") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = specialization,
                    onValueChange = { specialization = it },
                    label = { Text("Specialization *") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = hospitalName,
                    onValueChange = { hospitalName = it },
                    label = { Text("Hospital / Clinic *") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && specialization.isNotBlank()) {
                        onAdd(
                            Doctor(
                                name = name,
                                specialization = specialization,
                                hospitalName = hospitalName,
                                phone = phone
                            )
                        )
                    }
                },
                enabled = name.isNotBlank() && specialization.isNotBlank()
            ) {
                Text("Add Doctor")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
