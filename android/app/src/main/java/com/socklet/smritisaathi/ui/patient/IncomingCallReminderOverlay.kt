package com.socklet.smritisaathi.ui.patient

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.CognitiveTask
import com.socklet.smritisaathi.domain.model.TaskType

@Composable
fun IncomingCallReminderOverlay(
    task: CognitiveTask,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D253A),
                        Color(0xFF1E3C72),
                        Color(0xFF2A5298)
                    )
                )
            )
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Call Banner
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (task.type) {
                                TaskType.GRANDCHILD_INVITE -> Icons.Default.VideogameAsset
                                TaskType.MEDICATION_REMINDER -> Icons.Default.Medication
                                else -> Icons.Default.PhoneInTalk
                            },
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (task.type == TaskType.GRANDCHILD_INVITE) "Game Invitation" else "Scheduled Reminder",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Text(
                    text = task.title,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = task.subtitle,
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

            // Center Ringing Avatar Icon
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.size(110.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = when (task.type) {
                                TaskType.GRANDCHILD_INVITE -> Icons.Default.SportsEsports
                                TaskType.MEDICATION_REMINDER -> Icons.Default.MedicalServices
                                else -> Icons.Default.NotificationsActive
                            },
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(54.dp)
                        )
                    }
                }
            }

            // Call Action Controls (Decline / Answer)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = "Tap to answer 📞",
                    color = Color.White.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Snooze / Later
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        FilledIconButton(
                            onClick = onDecline,
                            modifier = Modifier.size(72.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFC62828))
                        ) {
                            Icon(
                                imageVector = Icons.Default.CallEnd,
                                contentDescription = "Later",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Later", color = Color.White, style = MaterialTheme.typography.labelLarge)
                    }

                    // Answer
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        FilledIconButton(
                            onClick = onAccept,
                            modifier = Modifier.size(88.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFF2E7D32))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Answer",
                                tint = Color.White,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Answer", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}
