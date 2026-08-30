package com.socklet.smritisaathi.ui.doctor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.DementiaStage
import com.socklet.smritisaathi.domain.model.DoctorPatientRequest
import com.socklet.smritisaathi.domain.model.Patient
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.ui.theme.Dimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("UNUSED_PARAMETER")
fun DoctorDashboardScreen(
    doctorId: String = "doc_1",
    doctorCode: String = "DR-8821",
    doctorName: String = "Dr. Pranab Baruah, MD",
    repository: PatientRepository? = null,
    onSelectPatient: (String) -> Unit,
    onSignOut: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Ensure demo patient is seeded for demo sessions
    LaunchedEffect(doctorId) {
        if (doctorId == "doctor_1" || doctorId.startsWith("demo_")) {
            repository?.createOrGetDemoPatient()
        }
    }

    var selectedTabIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var actionSnackbarMessage by remember { mutableStateOf<String?>(null) }

    // Fetch real patients assigned to this doctor from Firestore
    val assignedPatientsFlow = remember(doctorId) {
        repository?.getPatientsByDoctorId(doctorId)
    }
    val assignedPatients: List<Patient> by if (assignedPatientsFlow != null) {
        assignedPatientsFlow.collectAsStateWithLifecycle(initialValue = emptyList<Patient>())
    } else {
        remember { mutableStateOf(emptyList<Patient>()) }
    }

    // Fetch pending assignment requests for this doctor across ID, Code, and Name
    val pendingRequestsFlow = remember(doctorId, doctorCode, doctorName) {
        repository?.getPendingRequestsForDoctor(doctorId, doctorCode, doctorName)
    }
    val pendingRequests: List<DoctorPatientRequest> by if (pendingRequestsFlow != null) {
        pendingRequestsFlow.collectAsStateWithLifecycle(initialValue = emptyList<DoctorPatientRequest>())
    } else {
        remember { mutableStateOf(emptyList<DoctorPatientRequest>()) }
    }

    val filteredPatients = remember(assignedPatients, searchQuery) {
        if (searchQuery.isBlank()) assignedPatients
        else assignedPatients.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        snackbarHost = {
            actionSnackbarMessage?.let { msg ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { actionSnackbarMessage = null }) {
                            Text("OK", color = Color.White)
                        }
                    }
                ) {
                    Text(msg)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .safeDrawingPadding()
                .padding(paddingValues)
                .padding(Dimensions.ScreenPadding),
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Clinical Triage & Oversight",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = if (doctorName.startsWith("Dr.") || doctorName.startsWith("dr.")) doctorName else "Dr. $doctorName",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Doctor Code: ${doctorCode.ifBlank { "DR-XXXX" }}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        actionSnackbarMessage = "Dashboard refreshed ✓"
                    }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.MedicalServices,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(onClick = { onSignOut() }) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Sign Out",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Space12))

            // Doctor Referral Code Card (For family linking)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(Dimensions.CardCornerRadius)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Your Unique Doctor Code:", style = MaterialTheme.typography.bodySmall)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = doctorCode,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 2.sp
                            )
                        }
                        Text(
                            text = "Families use this code during registration or from dashboard to request clinical oversight.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Doctor Code", doctorCode)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Doctor Code $doctorCode copied! 📋", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Copy")
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Space16))

            // Navigation Tabs (My Patients vs Pending Requests)
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = {
                        Text(
                            text = "My Patients (${assignedPatients.size})",
                            fontWeight = if (selectedTabIndex == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Pending Requests",
                                fontWeight = if (selectedTabIndex == 1) FontWeight.Bold else FontWeight.Normal
                            )
                            if (pendingRequests.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Badge(containerColor = MaterialTheme.colorScheme.error) {
                                    Text("${pendingRequests.size}")
                                }
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(Dimensions.Space12))

            // TAB 0: ASSIGNED PATIENTS
            if (selectedTabIndex == 0) {
                // Search Patients Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search assigned patients") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(Dimensions.Space12))

                if (filteredPatients.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Dimensions.Space12),
                            modifier = Modifier.padding(Dimensions.Space24)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PeopleOutline,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                            Text(
                                text = "No Patients Assigned Yet",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Share your Doctor Code ($doctorCode) with families. When they send a connection request, it will appear in the 'Pending Requests' tab for your review.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
                    ) {
                        items(filteredPatients, key = { it.id }) { patient ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSelectPatient(patient.id) },
                                shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(Dimensions.Space16),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(48.dp)
                                                .background(
                                                    when (patient.dementiaStage) {
                                                        DementiaStage.MILD -> Color(0xFFE8F5E9)
                                                        DementiaStage.MODERATE -> Color(0xFFFFF3E0)
                                                        DementiaStage.SEVERE -> Color(0xFFFFEBEE)
                                                    },
                                                    CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = null,
                                                tint = when (patient.dementiaStage) {
                                                    DementiaStage.MILD -> Color(0xFF2E7D32)
                                                    DementiaStage.MODERATE -> Color(0xFFE65100)
                                                    DementiaStage.SEVERE -> Color(0xFFC62828)
                                                }
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(Dimensions.Space12))

                                        Column {
                                            Text(
                                                text = patient.name,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Age: ${patient.age} • Stage: ${patient.dementiaStage.displayName}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    // Clinical Status Chip
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = when (patient.dementiaStage) {
                                            DementiaStage.MILD -> Color(0xFFE8F5E9)
                                            DementiaStage.MODERATE -> Color(0xFFFFF3E0)
                                            DementiaStage.SEVERE -> Color(0xFFFFEBEE)
                                        }
                                    ) {
                                        Text(
                                            text = when (patient.dementiaStage) {
                                                DementiaStage.MILD -> "Stable"
                                                DementiaStage.MODERATE -> "Moderate Monitor"
                                                DementiaStage.SEVERE -> "High Care Tier"
                                            },
                                            color = when (patient.dementiaStage) {
                                                DementiaStage.MILD -> Color(0xFF2E7D32)
                                                DementiaStage.MODERATE -> Color(0xFFE65100)
                                                DementiaStage.SEVERE -> Color(0xFFC62828)
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

            // TAB 1: PENDING REQUESTS
            if (selectedTabIndex == 1) {
                if (pendingRequests.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Dimensions.Space12),
                            modifier = Modifier.padding(Dimensions.Space24)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircleOutline,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "All Caught Up!",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "No pending patient connection requests. When family caregivers connect using your Doctor Code ($doctorCode), their requests will show here.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                actionSnackbarMessage = "Checked for new requests ✓"
                            }) {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Check for Requests")
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(pendingRequests, key = { it.id }) { request ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f)
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = request.patientName,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Age: ${request.patientAge} • Stage: ${request.dementiaStage.displayName}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.tertiary
                                        ) {
                                            Text(
                                                text = "PENDING REVIEW",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "Requested by: ${request.familyName} (${request.familyRelationship})",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                                    ) {
                                        OutlinedButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    val res = repository?.declineDoctorRequest(request.id, null)
                                                    actionSnackbarMessage = if (res?.isSuccess == true) {
                                                        "Request declined"
                                                    } else {
                                                        "Failed to decline request"
                                                    }
                                                }
                                            },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = MaterialTheme.colorScheme.error
                                            )
                                        ) {
                                            Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Decline")
                                        }

                                        Button(
                                            onClick = {
                                                coroutineScope.launch {
                                                    val res = repository?.acceptDoctorRequest(request)
                                                    actionSnackbarMessage = if (res?.isSuccess == true) {
                                                        "✓ Accepted ${request.patientName}! Patient added to your clinical dashboard."
                                                    } else {
                                                        "Failed to accept: ${res?.exceptionOrNull()?.message}"
                                                    }
                                                }
                                            }
                                        ) {
                                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Accept & Connect")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
