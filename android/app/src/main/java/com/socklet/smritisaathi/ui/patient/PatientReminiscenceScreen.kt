package com.socklet.smritisaathi.ui.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.socklet.smritisaathi.domain.model.LifeStage
import com.socklet.smritisaathi.domain.model.ReminiscenceContent
import com.socklet.smritisaathi.ui.theme.Dimensions
import com.socklet.smritisaathi.util.VoiceAssistantManager

@Composable
fun PatientReminiscenceScreen(
    memories: List<ReminiscenceContent>,
    voiceAssistant: VoiceAssistantManager,
    onBack: () -> Unit
) {
    val sampleMemories = remember(memories) {
        memories.ifEmpty {
            listOf(
                ReminiscenceContent(
                    id = "1",
                    title = "Family Picnic at Brahmaputra Riverside",
                    description = "Whole family gathered for tea, pitha, and laughter by the sunset riverbank.",
                    personTag = "Aarav, Sunita, Rajesh",
                    lifeStage = LifeStage.FAMILY_CHILDREN,
                    uploadedBy = "Sunita (Daughter)"
                ),
                ReminiscenceContent(
                    id = "2",
                    title = "Aarav's 5th Birthday Celebration",
                    description = "Cutting mango cake with grandfather singing birthday songs together.",
                    personTag = "Aarav (Grandson)",
                    lifeStage = LifeStage.PRESENT_DAY,
                    uploadedBy = "Aarav (Grandson)"
                )
            )
        }
    }

    var selectedMemoryIndex by remember { mutableStateOf(0) }
    val currentMemory = sampleMemories.getOrNull(selectedMemoryIndex % sampleMemories.size)

    LaunchedEffect(selectedMemoryIndex) {
        currentMemory?.let {
            voiceAssistant.speak("${it.title}. ${it.description}")
        }
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
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(32.dp))
            }
            Text(
                text = "Memory Lane 🌸",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = {
                currentMemory?.let {
                    voiceAssistant.speak("${it.title}. ${it.description}")
                }
            }) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Read Aloud", modifier = Modifier.size(32.dp))
            }
        }

        // Main Memory Showcase Card
        if (currentMemory != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = Dimensions.Space12),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (!currentMemory.photoUrl.isNullOrBlank()) {
                            AsyncImage(
                                model = currentMemory.photoUrl,
                                contentDescription = currentMemory.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(68.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentMemory.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = currentMemory.description,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        lineHeight = 26.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "❤️ Shared by ${currentMemory.uploadedBy}",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Navigation Row (Previous / Next Photo)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalButton(
                onClick = {
                    if (selectedMemoryIndex > 0) selectedMemoryIndex--
                },
                enabled = selectedMemoryIndex > 0,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Previous")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (selectedMemoryIndex < sampleMemories.size - 1) selectedMemoryIndex++
                },
                enabled = selectedMemoryIndex < sampleMemories.size - 1,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
            ) {
                Text("Next Memory")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }
    }
}
