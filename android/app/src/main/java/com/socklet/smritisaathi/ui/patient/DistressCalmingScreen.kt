package com.socklet.smritisaathi.ui.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

@Composable
fun DistressCalmingScreen(
    patientName: String,
    familyVoiceMessage: String = "Take a gentle deep breath. You are safe at home and we all love you so much.",
    voiceAssistant: VoiceAssistantManager,
    onRecovered: () -> Unit
) {
    LaunchedEffect(Unit) {
        voiceAssistant.speak("Hello $patientName. $familyVoiceMessage")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F9F5)) // Soothing soft mint/sage background
            .padding(Dimensions.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Soothing Top Icon
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = Dimensions.Space24)
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFE8F5E9),
                modifier = Modifier.size(80.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Space16))

            Text(
                text = "Take a Gentle Breath 🌿",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )
        }

        // Family Reassurance Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimensions.Space16),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFC8E6C9))
                        .border(3.dp, Color(0xFF81C784), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Diversity3,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(72.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "A Message from Family ❤️",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "\"$familyVoiceMessage\"",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF37474F),
                    lineHeight = 26.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {
                        voiceAssistant.speak(familyVoiceMessage)
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.VolumeUp, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Replay Family Voice")
                }
            }
        }

        // Action: I Feel Better Now (Large Green Button)
        Button(
            onClick = onRecovered,
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            shape = RoundedCornerShape(Dimensions.ButtonCornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Icon(Icons.Default.Spa, contentDescription = null, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(Dimensions.Space12))
            Text(text = "I Feel Peaceful & Ready 🌸", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
