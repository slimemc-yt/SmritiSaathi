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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socklet.smritisaathi.domain.model.ActionType
import com.socklet.smritisaathi.domain.model.GameType
import com.socklet.smritisaathi.domain.model.PendingAction
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.ui.theme.Dimensions
import kotlinx.coroutines.launch

@Composable
fun PlayWithGrandpaScreen(
    patientId: String,
    repository: PatientRepository,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var senderName by remember { mutableStateOf("Aarav (Grandson)") }
    var customMessage by remember { mutableStateOf("Dadu, let's play a fun game together right now! 🎮") }
    var selectedGame by remember { mutableStateOf(GameType.CARD_MATCHING) }
    var inviteSentSuccess by remember { mutableStateOf(false) }

    val gamesList = listOf(
        Pair(GameType.CARD_MATCHING, "Match Memory Cards 🃏"),
        Pair(GameType.FACE_RECOGNITION, "Family Face Recognition 👨‍👩‍👧"),
        Pair(GameType.NER_FAMILIAR_IMAGES, "NER Heritage & Culture 🦏"),
        Pair(GameType.SHOPPING_BASKET, "Shopping Basket Memory 🛒"),
        Pair(GameType.MUSIC_MEMORY, "Folk Songs & Music 🎶")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimensions.ScreenPadding),
        verticalArrangement = Arrangement.SpaceBetween
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
                text = "Play With Grandpa / Grandma",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        if (inviteSentSuccess) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Game Invitation Sent! 🎉",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your loved one's screen is ringing with your invite right now!",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.ButtonHeight),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Text("Return to Dashboard", fontSize = 18.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Space16)
            ) {
                item {
                    Text(
                        text = "Send an interactive game invitation that pops up on their phone like a friendly incoming call!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                item {
                    OutlinedTextField(
                        value = senderName,
                        onValueChange = { senderName = it },
                        label = { Text("Your Name / Relation") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                item {
                    OutlinedTextField(
                        value = customMessage,
                        onValueChange = { customMessage = it },
                        label = { Text("Cheer Message") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text(
                        text = "Choose Game to Play:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(gamesList) { (gameType, label) ->
                    val isSelected = selectedGame == gameType
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedGame = gameType },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium)
                            if (isSelected) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        repository.sendPendingAction(
                            patientId,
                            PendingAction(
                                patientId = patientId,
                                type = ActionType.PLAY_INVITE,
                                senderName = senderName,
                                message = customMessage,
                                targetGame = selectedGame
                            )
                        )
                        inviteSentSuccess = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.ButtonHeight),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Send Game Invite 🚀", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
