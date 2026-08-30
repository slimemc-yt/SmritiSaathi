package com.socklet.smritisaathi.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.socklet.smritisaathi.domain.model.Language
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageButton(
    language: Language,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensions.ButtonHeightLarge),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        border = if (isSelected) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else null,
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else Dimensions.CardElevation
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimensions.Space20),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = language.nativeName,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Start,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    if (isSelected) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Dimensions.Space4))
                Text(
                    text = language.nameInEnglish,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(
                onClick = onSpeakerClick,
                modifier = Modifier.size(Dimensions.IconLarge)
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Play ${language.nameInEnglish} sample",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(Dimensions.IconMedium)
                )
            }
        }
    }
}
