package com.socklet.smritisaathi.ui.family

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.AlertSeverity
import com.socklet.smritisaathi.domain.model.BehavioralAlert
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.ui.theme.Dimensions
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BehavioralAlertsInboxScreen(
    patientId: String,
    repository: PatientRepository,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val alerts by repository.getBehavioralAlertsFlow(patientId).collectAsStateWithLifecycle(initialValue = emptyList())
    val patient by repository.getPatientFlow(patientId).collectAsStateWithLifecycle(initialValue = null)

    val dateFormat = remember { SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                text = "Behavioral Alerts Inbox",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        if (alerts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "All Clear! No Active Alerts",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Cognitive scores and routines are in good range.",
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
                items(alerts) { alert ->
                    val isUrgent = alert.severity == AlertSeverity.URGENT || alert.severity == AlertSeverity.HIGH

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (!alert.acknowledged && isUrgent) Color(0xFFFFEBEE)
                            else if (!alert.acknowledged) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                            else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(Dimensions.CardCornerRadius)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isUrgent) Icons.Default.Emergency else Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = if (isUrgent) Color(0xFFC62828) else MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = alert.severity.displayName,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isUrgent) Color(0xFFC62828) else MaterialTheme.colorScheme.primary,
                                        fontSize = 13.sp
                                    )
                                }

                                Text(
                                    text = dateFormat.format(alert.timestamp),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = alert.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = alert.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        val phone = patient?.emergencyContact?.phoneNumber ?: ""
                                        if (phone.isNotBlank()) {
                                            context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Call Patient", fontSize = 12.sp)
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                if (!alert.acknowledged) {
                                    OutlinedButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                repository.acknowledgeAlert(patientId, alert.id, "caregiver")
                                            }
                                        }
                                    ) {
                                        Text("Acknowledge", fontSize = 12.sp)
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
