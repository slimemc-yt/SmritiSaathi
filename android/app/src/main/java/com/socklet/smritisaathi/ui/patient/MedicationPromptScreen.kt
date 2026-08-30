package com.socklet.smritisaathi.ui.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.Reminder
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

@Composable
fun MedicationPromptScreen(
    reminder: Reminder,
    voiceAssistant: VoiceAssistantManager,
    onTaken: () -> Unit,
    onRemindLater: () -> Unit,
    onNeedHelp: () -> Unit
) {
    val medicineText = reminder.medicineName ?: reminder.title
    val dosageText = reminder.dosage ?: "1 dose with water"

    LaunchedEffect(Unit) {
        voiceAssistant.speak("Hello! It is time for your medicine: $medicineText. Please take $dosageText with a glass of water.")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimensions.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = Dimensions.Space24)
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(72.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Space16))

            Text(
                text = "Medicine Time 💊",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimensions.Space8))

            Text(
                text = reminder.scheduledTime,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Center Medicine Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimensions.Space16),
            shape = RoundedCornerShape(Dimensions.CardCornerRadius),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Space20),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = medicineText,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimensions.Space8))

                Text(
                    text = "Dosage: $dosageText",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                reminder.description.takeIf { it.isNotBlank() }?.let { description ->
                    Spacer(modifier = Modifier.height(Dimensions.Space8))
                    Text(
                        text = "Note: $description",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        // 3 Action Buttons Only: Taken / Remind Later / Need Help
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimensions.Space16),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
        ) {
            // Button 1: Taken (Large Green)
            Button(
                onClick = {
                    voiceAssistant.speak("Thank you! Medicine marked as taken.")
                    onTaken()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(Dimensions.Space12))
                Text(text = "I Have Taken It ✅", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            // Button 2: Remind Later (Amber)
            FilledTonalButton(
                onClick = {
                    voiceAssistant.speak("Sure, I will remind you again in 15 minutes.")
                    onRemindLater()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(Dimensions.Space12))
                Text(text = "Remind Me in 15m ⏰", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            }

            // Button 3: I Need Help (High-contrast Red Outline)
            OutlinedButton(
                onClick = {
                    voiceAssistant.speak("Alerting your family right away. Help is coming.")
                    onNeedHelp()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC62828)),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFC62828))
            ) {
                Icon(Icons.Default.Emergency, contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(Dimensions.Space12))
                Text(text = "I Need Help 🆘", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
