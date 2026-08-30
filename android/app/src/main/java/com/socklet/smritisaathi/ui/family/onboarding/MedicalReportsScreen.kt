package com.socklet.smritisaathi.ui.family.onboarding

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.MedicalReport
import com.socklet.smritisaathi.ui.theme.Dimensions
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalReportsScreen(
    viewModel: PatientOnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddReportDialog by remember { mutableStateOf(false) }
    var selectedReportType by remember { mutableStateOf("") }
    var isUploading by remember { mutableStateOf(false) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Extract filename from URI
            val fileName = it.lastPathSegment ?: "medical_report_${System.currentTimeMillis()}"
            // Add to pending reports list (will be uploaded when patient is created)
            viewModel.addPendingMedicalReport(it, fileName, selectedReportType)
            selectedReportType = ""
            showAddReportDialog = false
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
                text = "Medical Reports",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        Text(
            text = "Upload medical reports, prescriptions, and scan results. These will be securely stored and shared with the assigned doctor.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        if (uiState.medicalReports.isEmpty() && uiState.pendingMedicalReports.isEmpty()) {
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
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(Dimensions.Space16))
                    Text(
                        text = "No reports uploaded yet",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(Dimensions.Space8))
                    Text(
                        text = "You can skip this step or add reports now",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            // List of uploaded and pending reports
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
            ) {
                // Show pending reports (to be uploaded)
                items(
                    items = uiState.pendingMedicalReports,
                    key = { it.id }
                ) { pending ->
                    PendingMedicalReportCard(
                        fileName = pending.fileName,
                        reportType = pending.type,
                        onRemove = { viewModel.removePendingMedicalReport(pending.id) }
                    )
                }
                // Show uploaded reports
                items(
                    items = uiState.medicalReports,
                    key = { it.id }
                ) { report ->
                    MedicalReportCard(
                        report = report,
                        onRemove = { viewModel.removeMedicalReport(report.id) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        // Add Report Button
        OutlinedButton(
            onClick = { showAddReportDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Text(if (uiState.pendingMedicalReports.isEmpty()) "Add Medical Report" else "Add Another Report")
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        // Continue Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            val totalReports = uiState.medicalReports.size + uiState.pendingMedicalReports.size
            if (totalReports == 0) {
                Text("Skip & Continue")
            } else {
                Text("Continue ($totalReports ${if (totalReports == 1) "report" else "reports"})")
            }
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }

    // Add Report Dialog
    if (showAddReportDialog) {
        AddReportDialog(
            onDismiss = { showAddReportDialog = false },
            onReportTypeSelected = { type ->
                selectedReportType = type
                filePickerLauncher.launch("*/*")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PendingMedicalReportCard(
    fileName: String,
    reportType: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CloudUpload,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(Dimensions.Space16))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = fileName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$reportType • Recorded (free tier - metadata only)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddReportDialog(
    onDismiss: () -> Unit,
    onReportTypeSelected: (String) -> Unit
) {
    val reportTypes = listOf(
        "Lab Report" to Icons.Default.Science,
        "Prescription" to Icons.Default.Receipt,
        "Scan/MRI" to Icons.Default.MedicalServices,
        "Doctor's Note" to Icons.Default.Note,
        "Other" to Icons.Default.InsertDriveFile
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Report Type") },
        text = {
            Column {
                reportTypes.forEach { (type, icon) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onReportTypeSelected(type) }
                            .padding(vertical = Dimensions.Space16),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(Dimensions.Space16))
                        Text(type, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MedicalReportCard(
    report: MedicalReport,
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
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(Dimensions.Space16))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = report.fileName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${report.type} • ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(report.uploadedAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
