package com.socklet.smritisaathi.ui.family.onboarding

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.DementiaStage
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DementiaStageScreen(
    viewModel: PatientOnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                text = "Dementia Stage",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        Text(
            text = "Select the current stage of dementia. This determines the level of support features.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space16)
        ) {
            DementiaStage.values().forEach { stage ->
                DementiaStageCard(
                    stage = stage,
                    selected = uiState.dementiaStage == stage,
                    onClick = { viewModel.setDementiaStage(stage) }
                )
            }

            // Info card about tiers
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(Dimensions.Space16),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(Dimensions.Space12))
                    Text(
                        text = "Stage determines the Tier level:\n" +
                                "• Tier 1 (Mild): Basic reminders, games\n" +
                                "• Tier 2 (Moderate): Enhanced monitoring, simplified UI\n" +
                                "• Tier 3 (Severe): Kiosk mode, full-time assistance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        // Continue Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Text("Continue")
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
private fun DementiaStageCard(
    stage: DementiaStage,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(
                if (selected) {
                    Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space20),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon based on stage
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .then(
                        if (selected) {
                            Modifier.border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            )
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (stage) {
                        DementiaStage.MILD -> Icons.Default.SentimentSatisfied
                        DementiaStage.MODERATE -> Icons.Default.SentimentNeutral
                        DementiaStage.SEVERE -> Icons.Default.SentimentDissatisfied
                    },
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = when (stage) {
                        DementiaStage.MILD -> MaterialTheme.colorScheme.tertiary
                        DementiaStage.MODERATE -> MaterialTheme.colorScheme.secondary
                        DementiaStage.SEVERE -> MaterialTheme.colorScheme.error
                    }
                )
            }

            Spacer(modifier = Modifier.width(Dimensions.Space16))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stage.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tier ${stage.tier}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = getStageDescription(stage),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (selected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

private fun getStageDescription(stage: DementiaStage): String {
    return when (stage) {
        DementiaStage.MILD -> "Memory lapses, still independent. Benefits from reminders and cognitive exercises."
        DementiaStage.MODERATE -> "Noticeable memory loss, needs assistance. Simplified interface and monitoring."
        DementiaStage.SEVERE -> "Significant cognitive decline. Requires kiosk mode and constant support."
    }
}
