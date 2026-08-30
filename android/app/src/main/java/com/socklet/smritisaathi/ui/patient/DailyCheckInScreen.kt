package com.socklet.smritisaathi.ui.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

@Composable
fun DailyCheckInScreen(
    patientName: String,
    voiceAssistant: VoiceAssistantManager,
    onAnswerSubmitted: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val options = listOf(
        Pair("Warm Rice & Dal 🍲", "Had warm rice and lentils"),
        Pair("Roti & Fresh Veggies 🥗", "Had roti and vegetables"),
        Pair("Khichdi & Curd 🥣", "Had light khichdi with curd"),
        Pair("Tea & Light Snacks ☕", "Had tea and snacks"),
        Pair("Haven't eaten yet ⏳", "Not eaten yet")
    )

    LaunchedEffect(Unit) {
        voiceAssistant.speak("Hello $patientName! What did you have for your meal today? Tap an option below.")
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(32.dp))
            }
            Text(
                text = "Daily Meal Check-in",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = {
                voiceAssistant.speak("What did you have for your meal today?")
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Voice", modifier = Modifier.size(32.dp))
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = Dimensions.Space16)
        ) {
            Icon(
                imageVector = Icons.Default.Restaurant,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(Dimensions.Space12))
            Text(
                text = "What did you eat today, $patientName? 🍽️",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        // Response Options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
        ) {
            options.forEach { (displayLabel, answerValue) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clickable {
                            voiceAssistant.speak("Thank you! Glad to know.")
                            onAnswerSubmitted(answerValue)
                        },
                    shape = RoundedCornerShape(Dimensions.CardCornerRadius),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimensions.Space20),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = displayLabel,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))
    }
}
