package com.socklet.smritisaathi.wearos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    MaterialTheme {
        val listState = rememberScalingLazyListState()
        var medicineStatus by remember { mutableStateOf<String?>(null) }
        var sosTriggered by remember { mutableStateOf(false) }

        Scaffold(
            timeText = { TimeText() },
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
            positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
        ) {
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(horizontal = 8.dp),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Header Title
                item {
                    Text(
                        text = "স্মৃতিসাথী",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF60A5FA),
                        textAlign = TextAlign.Center
                    )
                }

                // Next Routine Reminder Card
                item {
                    Card(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        backgroundPainter = CardDefaults.cardBackgroundPainter(
                            startBackgroundColor = Color(0xFF1E293B),
                            endBackgroundColor = Color(0xFF0F172A)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "💊 Evening Medicine",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Scheduled at 8:00 PM",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }

                // Action Buttons (Taken / Remind Later)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = { medicineStatus = "Taken ✅" },
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF059669)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Taken ✅", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        Button(
                            onClick = { medicineStatus = "Snoozed ⏰" },
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF475569)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("15m ⏰", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }

                if (medicineStatus != null) {
                    item {
                        Text(
                            text = medicineStatus ?: "",
                            fontSize = 12.sp,
                            color = Color(0xFF34D399),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Large SOS Emergency Button
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = { sosTriggered = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFDC2626)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (sosTriggered) "SOS ALERT SENT 🚨" else "🆘 SOS HELP",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
