package com.socklet.smritisaathi.ui.family

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.*
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.ui.theme.Dimensions
import kotlinx.coroutines.launch

@Composable
fun FamilyDashboardScreen(
    patientId: String,
    repository: PatientRepository,
    onNavigateToPlayInvite: () -> Unit,
    onNavigateToAlerts: () -> Unit,
    onNavigateToReminders: () -> Unit,
    onNavigateToReminiscence: () -> Unit,
    onNavigateToReports: () -> Unit,
    onSignOut: () -> Unit = {}
) {
    // Sign out + debug state from the same VM
    val authViewModel: com.socklet.smritisaathi.ui.onboarding.AuthViewModel = hiltViewModel()
    val authState by authViewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    val patient by repository.getPatientFlow(patientId).collectAsStateWithLifecycle(initialValue = null)
    val doctorRequest by repository.getDoctorRequestForPatient(patientId).collectAsStateWithLifecycle(initialValue = null)
    val gameResults by repository.getGameResultsFlow(patientId).collectAsStateWithLifecycle(initialValue = emptyList())
    val alerts by repository.getBehavioralAlertsFlow(patientId).collectAsStateWithLifecycle(initialValue = emptyList())
    val reminders by repository.getRemindersFlow(patientId).collectAsStateWithLifecycle(initialValue = emptyList())

    val unacknowledgedAlerts = remember(alerts) { alerts.filter { !it.acknowledged } }
    val averageScore = remember(gameResults) {
        if (gameResults.isEmpty()) 0 else gameResults.take(10).map { it.score }.average().toInt()
    }
    val adherenceRate = remember(reminders) {
        if (reminders.isEmpty()) 0 else {
            val taken = reminders.count { it.status == ReminderStatus.TAKEN }
            ((taken.toFloat() / reminders.size) * 100).toInt()
        }
    }

    var showNudgeDialog by remember { mutableStateOf(false) }
    var nudgeMessage by remember { mutableStateOf("") }
    var actionStatusSnackbar by remember { mutableStateOf<String?>(null) }
    var showSignOutConfirm by remember { mutableStateOf(false) }
    var showAccountMenu by remember { mutableStateOf(false) }
    var showDeleteAccountConfirm by remember { mutableStateOf(false) }
    var showDoctorConnectDialog by remember { mutableStateOf(false) }
    var doctorSearchQuery by remember { mutableStateOf("") }
    var doctorSearchResults by remember { mutableStateOf<List<User>>(emptyList()) }
    var isSearchingDoctor by remember { mutableStateOf(false) }
    var showUnlinkDoctorConfirm by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            actionStatusSnackbar?.let { msg ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { actionStatusSnackbar = null }) {
                            Text("OK", color = Color.White)
                        }
                    }
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
                .padding(Dimensions.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space16)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Caregiver Command Center",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = patient?.name?.ifBlank { null } ?: if (patientId.isBlank()) "No Patient Selected" else "Loading...",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Account Menu Button
                    Box {
                        IconButton(onClick = { showAccountMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Account Menu", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        
                        DropdownMenu(
                            expanded = showAccountMenu,
                            onDismissRequest = { showAccountMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Account Info") },
                                onClick = {
                                    showAccountMenu = false
                                    // TODO: Navigate to account info screen
                                },
                                leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) }
                            )
                            DropdownMenuItem(
                                text = { Text("Sign Out") },
                                onClick = {
                                    showAccountMenu = false
                                    showSignOutConfirm = true
                                },
                                leadingIcon = { Icon(Icons.Default.Logout, contentDescription = null) }
                            )
                            Divider()
                            DropdownMenuItem(
                                text = { Text("Delete Account", color = MaterialTheme.colorScheme.error) },
                                onClick = {
                                    showAccountMenu = false
                                    showDeleteAccountConfirm = true
                                },
                                leadingIcon = { Icon(Icons.Default.DeleteForever, contentDescription = null, tint = MaterialTheme.colorScheme.error) }
                            )
                        }
                    }

                    // Urgency Badge
                    if (unacknowledgedAlerts.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFC62828),
                            modifier = Modifier.clickable(onClick = onNavigateToAlerts)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${unacknowledgedAlerts.size} Alerts",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            // Patient Linking & Access Codes Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f)
                    ),
                    shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Device Pairing & Family Sync",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Icon(Icons.Default.Share, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Patient Device Code:", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    text = patient?.pairingCode?.ifBlank { "123456" } ?: "123456",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 4.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Column {
                                Text("Family Invite Code:", style = MaterialTheme.typography.bodySmall)
                                Text(
                                    text = patient?.familyInviteCode?.ifBlank { "FAM-DEMO" } ?: "FAM-DEMO",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 2.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }

            // Doctor Assignment & Clinical Oversight Card
            item {
                val hasAssignedDoctor = !patient?.assignedDoctorId.isNullOrBlank()
                val isRequestPending = doctorRequest?.status == DoctorRequestStatus.PENDING
                val isRequestDeclined = doctorRequest?.status == DoctorRequestStatus.DECLINED

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            hasAssignedDoctor -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            isRequestPending -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                            isRequestDeclined -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),
                    shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.MedicalServices,
                                    contentDescription = null,
                                    tint = when {
                                        hasAssignedDoctor -> MaterialTheme.colorScheme.primary
                                        isRequestPending -> MaterialTheme.colorScheme.tertiary
                                        isRequestDeclined -> MaterialTheme.colorScheme.error
                                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Clinical Oversight & Doctor",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Status Badge
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = when {
                                    hasAssignedDoctor -> MaterialTheme.colorScheme.primary
                                    isRequestPending -> MaterialTheme.colorScheme.tertiary
                                    isRequestDeclined -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.outline
                                }
                            ) {
                                Text(
                                    text = when {
                                        hasAssignedDoctor -> "CONNECTED"
                                        isRequestPending -> "PENDING APPROVAL"
                                        isRequestDeclined -> "DECLINED"
                                        else -> "NOT ASSIGNED"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        when {
                            hasAssignedDoctor -> {
                                Text(
                                    text = patient?.assignedDoctor?.name?.ifBlank { null }
                                        ?: doctorRequest?.doctorName?.ifBlank { null }
                                        ?: "Dr. Assigned Clinician",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${patient?.assignedDoctor?.specialization ?: "Neurologist / Dementia Specialist"} • ${patient?.assignedDoctor?.hospitalName ?: "Medical Center"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    OutlinedButton(
                                        onClick = { showUnlinkDoctorConfirm = true },
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = MaterialTheme.colorScheme.error
                                        )
                                    ) {
                                        Icon(Icons.Default.LinkOff, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Change / Unlink Doctor")
                                    }
                                }
                            }
                            isRequestPending -> {
                                Text(
                                    text = "Request sent to ${doctorRequest?.doctorName?.ifBlank { "Doctor" } ?: "Doctor"} (${doctorRequest?.doctorCode ?: "DR-XXXX"}).",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Awaiting the doctor's review and approval before clinical records are shared.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            val reqId = doctorRequest?.id ?: ""
                                            coroutineScope.launch {
                                                val res = repository.cancelDoctorRequest(
                                                    requestId = reqId,
                                                    patientId = patientId,
                                                    doctorId = doctorRequest?.doctorId ?: "",
                                                    doctorCode = doctorRequest?.doctorCode ?: ""
                                                )
                                                actionStatusSnackbar = if (res.isSuccess) "Assignment request cancelled" else "Failed to cancel request"
                                            }
                                        }
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Cancel Request")
                                    }
                                }
                            }
                            isRequestDeclined -> {
                                Text(
                                    text = "The doctor was unable to accept this patient assignment at this time.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(onClick = {
                                        showDoctorConnectDialog = true
                                        coroutineScope.launch {
                                            isSearchingDoctor = true
                                            doctorSearchResults = authViewModel.searchDoctors("")
                                            isSearchingDoctor = false
                                        }
                                    }) {
                                        Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Select Another Doctor")
                                    }
                                }
                            }
                            else -> {
                                Text(
                                    text = "Connect with a neurologist or clinical specialist using their Doctor Code (e.g. DR-8821) or by searching their name.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    FilledTonalButton(onClick = {
                                        showDoctorConnectDialog = true
                                        coroutineScope.launch {
                                            isSearchingDoctor = true
                                            doctorSearchResults = authViewModel.searchDoctors("")
                                            isSearchingDoctor = false
                                        }
                                    }) {
                                        Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Connect Doctor")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Quick Stats Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Space12)
                ) {
                    // Stat 1: Cognitive Score
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Cognitive Score", style = MaterialTheme.typography.labelMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$averageScore%",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text("Stable trend", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    // Stat 2: Routine Adherence
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Med Adherence", style = MaterialTheme.typography.labelMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$adherenceRate%",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text("Last 7 days", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Remote Controls & Quick Actions
            item {
                Text(
                    text = "Remote Caregiver Controls",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Space8)
                ) {
                    // Remote Action 1: Play with Grandpa / Game Invite
                    Button(
                        onClick = onNavigateToPlayInvite,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.SportsEsports, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Play Invite", fontSize = 13.sp)
                    }

                    // Remote Action 2: Send Voice Nudge
                    FilledTonalButton(
                        onClick = { showNudgeDialog = true },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.RecordVoiceOver, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Send Nudge", fontSize = 13.sp)
                    }

                    // Remote Action 3: Trigger Calming
                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch {
                                repository.sendPendingAction(
                                    patientId,
                                    PendingAction(
                                        patientId = patientId,
                                        type = ActionType.TRIGGER_CALMING,
                                        senderName = "Family Caregiver",
                                        message = "Triggered reassuring family screen"
                                    )
                                )
                                actionStatusSnackbar = "Calming screen triggered on patient device 🌸"
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Spa, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Calm Mode", fontSize = 13.sp)
                    }
                }
            }

            // Behavioral Alerts Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Behavioral Alerts",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = onNavigateToAlerts) {
                        Text("View All")
                    }
                }
            }

            if (alerts.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("No behavioral alerts. Routine is proceeding calmly.")
                        }
                    }
                }
            } else {
                items(alerts.take(3)) { alert ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (!alert.acknowledged) Color(0xFFFFEBEE) else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Icon(
                                    imageVector = when (alert.severity) {
                                        AlertSeverity.URGENT, AlertSeverity.HIGH -> Icons.Default.Emergency
                                        else -> Icons.Default.Info
                                    },
                                    contentDescription = null,
                                    tint = if (!alert.acknowledged) Color(0xFFC62828) else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = alert.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = alert.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            if (!alert.acknowledged) {
                                TextButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            repository.acknowledgeAlert(patientId, alert.id, "caregiver")
                                            actionStatusSnackbar = "Alert marked as acknowledged."
                                        }
                                    }
                                ) {
                                    Text("Dismiss", color = Color(0xFFC62828))
                                }
                            }
                        }
                    }
                }
            }

            // Quick Navigation Hub
            item {
                Text(
                    text = "Care Management Hub",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onNavigateToReminiscence),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Family Reminiscence & Voice Album", fontWeight = FontWeight.Bold)
                                    Text("Upload family photos & voice notes for recall games", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onNavigateToReminders),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Routine & Medication Schedule", fontWeight = FontWeight.Bold)
                                    Text("Set nap, meal, and medicine times remotely", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onNavigateToReports),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Assessment, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Cognitive Reports & Doctor Share", fontWeight = FontWeight.Bold)
                                    Text("Generate weekly PDF reports for assigned doctor", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }
                }
            }
        }
    }

    // Send Nudge Dialog
    if (showNudgeDialog) {
        AlertDialog(
            onDismissRequest = { showNudgeDialog = false },
            title = { Text("Send Voice/Text Nudge") },
            text = {
                Column {
                    Text(
                        "This will display and speak your warm message on the patient's device immediately.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = nudgeMessage,
                        onValueChange = { nudgeMessage = it },
                        label = { Text("Message (e.g. 'Good morning Dad! Remember to drink water')") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nudgeMessage.isNotBlank()) {
                            coroutineScope.launch {
                                repository.sendPendingAction(
                                    patientId,
                                    PendingAction(
                                        patientId = patientId,
                                        type = ActionType.NUDGE,
                                        senderName = "Family Member",
                                        message = nudgeMessage
                                    )
                                )
                                showNudgeDialog = false
                                nudgeMessage = ""
                                actionStatusSnackbar = "Nudge sent to patient! 📢"
                            }
                        }
                    },
                    enabled = nudgeMessage.isNotBlank()
                ) {
                    Text("Send Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNudgeDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showSignOutConfirm) {
        AlertDialog(
            onDismissRequest = { showSignOutConfirm = false },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        authViewModel.signOut()
                        showSignOutConfirm = false
                        onSignOut()
                    }
                }) {
                    Text("Sign Out", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSignOutConfirm = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Sign Out") },
            text = { Text("Are you sure you want to sign out? Your data remains safe in Firestore.") }
        )
    }

    if (showDeleteAccountConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteAccountConfirm = false },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        showDeleteAccountConfirm = false
                        val result = authViewModel.deleteAccount()
                        if (result.isSuccess) {
                            onSignOut()
                        } else {
                            actionStatusSnackbar = "Delete failed: ${result.exceptionOrNull()?.message}"
                        }
                    }
                }) {
                    Text("Delete Permanently", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAccountConfirm = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Delete Account?", color = MaterialTheme.colorScheme.error) },
            text = {
                Column {
                    Text("This will permanently delete:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Your account and profile")
                    Text("• All patient data you created")
                    Text("• All relationships and links")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("This cannot be undone.", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }

    if (showUnlinkDoctorConfirm) {
        AlertDialog(
            onDismissRequest = { showUnlinkDoctorConfirm = false },
            title = { Text("Unlink Doctor?") },
            text = { Text("Are you sure you want to remove the assigned doctor? Remote clinical data sharing with this doctor will be paused.") },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        showUnlinkDoctorConfirm = false
                        val res = repository.unlinkDoctorFromPatient(patientId)
                        actionStatusSnackbar = if (res.isSuccess) "Doctor unlinked successfully" else "Failed to unlink doctor"
                    }
                }) {
                    Text("Unlink Doctor", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showUnlinkDoctorConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showDoctorConnectDialog) {
        AlertDialog(
            onDismissRequest = { showDoctorConnectDialog = false },
            title = {
                Text("Connect with Doctor", style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Enter Doctor Code (e.g. DR-8821) or search by doctor name:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = doctorSearchQuery,
                        onValueChange = { query ->
                            doctorSearchQuery = query
                            coroutineScope.launch {
                                isSearchingDoctor = true
                                doctorSearchResults = authViewModel.searchDoctors(query)
                                isSearchingDoctor = false
                            }
                        },
                        label = { Text("Search Doctor Name or Code") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (isSearchingDoctor) {
                        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    } else if (doctorSearchResults.isEmpty()) {
                        Text(
                            text = "No doctors found matching '$doctorSearchQuery'.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.padding(8.dp)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 240.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(doctorSearchResults) { docUser ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            coroutineScope.launch {
                                                val req = DoctorPatientRequest(
                                                    patientId = patientId,
                                                    patientName = patient?.name ?: "Patient",
                                                    patientAge = patient?.age ?: 70,
                                                    dementiaStage = patient?.dementiaStage ?: DementiaStage.MILD,
                                                    familyId = patient?.createdBy ?: authState.currentUser?.id ?: "",
                                                    familyName = authState.currentUser?.name ?: "Family Caregiver",
                                                    familyRelationship = "Primary Caregiver",
                                                    doctorId = docUser.id,
                                                    doctorName = docUser.name,
                                                    doctorCode = docUser.doctorCode ?: "",
                                                    hospitalName = docUser.hospitalName ?: "Medical Center",
                                                    status = DoctorRequestStatus.PENDING,
                                                    requestedAt = java.util.Date()
                                                )
                                                val res = repository.sendDoctorRequest(req)
                                                showDoctorConnectDialog = false
                                                actionStatusSnackbar = if (res.isSuccess) {
                                                    "Request sent to ${docUser.name}! 📨"
                                                } else {
                                                    "Failed to send request: ${res.exceptionOrNull()?.message}"
                                                }
                                            }
                                        },
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.MedicalServices, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(docUser.name, fontWeight = FontWeight.Bold)
                                            Text(
                                                "Code: ${docUser.doctorCode ?: "DR-XXXX"} • ${docUser.specialty ?: "Neurologist"}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                docUser.hospitalName ?: "Medical Center",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Icon(Icons.Default.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showDoctorConnectDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
