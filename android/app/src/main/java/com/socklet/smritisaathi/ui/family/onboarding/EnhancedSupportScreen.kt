package com.socklet.smritisaathi.ui.family.onboarding

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedSupportScreen(
    viewModel: PatientOnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val isScreenPinningEnabled = remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
                dpm.isLockTaskPermitted(context.packageName)
            } else {
                false
            }
        )
    }

    var expandedFeature by remember { mutableStateOf<String?>(null) }

    val features = listOf(
        FeatureItem(
            id = "kiosk",
            title = "Kiosk Mode",
            description = "App becomes the home screen. Patient cannot exit or access other apps.",
            icon = Icons.Default.Lock
        ),
        FeatureItem(
            id = "simplified",
            title = "Simplified Interface",
            description = "Extra-large buttons, high contrast colors, and minimal distractions.",
            icon = Icons.Default.AccessibilityNew
        ),
        FeatureItem(
            id = "monitoring",
            title = "Enhanced Monitoring",
            description = "Continuous activity tracking and more frequent location updates.",
            icon = Icons.Default.Visibility
        ),
        FeatureItem(
            id = "alerts",
            title = "Instant Alerts",
            description = "Family members receive real-time notifications for any unusual activity.",
            icon = Icons.Default.NotificationsActive
        )
    )

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
                text = "Enhanced Support Mode",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        // Tier 3 Badge
        Surface(
            color = MaterialTheme.colorScheme.error,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onError
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tier 3 - Severe Dementia",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        Text(
            text = "This mode is recommended for patients with severe dementia. It provides additional safety features and monitoring.",
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
            // Features List
            features.forEach { feature ->
                FeatureCard(
                    feature = feature,
                    expanded = expandedFeature == feature.id,
                    onClick = {
                        expandedFeature = if (expandedFeature == feature.id) null else feature.id
                    }
                )
            }

            // Warning Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(Dimensions.Space16),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(Dimensions.Space12))
                    Column {
                        Text(
                            text = "Important Information",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• Kiosk mode requires Screen Pinning permission\n" +
                                    "• Patient cannot access other apps or settings\n" +
                                    "• Family members can unlock remotely\n" +
                                    "• Can be disabled later from family dashboard",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Enable Toggle
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (uiState.enhancedSupportEnabled) {
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                ),
                border = if (uiState.enhancedSupportEnabled) {
                    androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                } else null
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Space16),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Enable Enhanced Support Mode",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Requires explicit consent to activate",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = uiState.enhancedSupportEnabled,
                        onCheckedChange = { enabled ->
                            if (enabled && !isScreenPinningEnabled.value) {
                                // Open screen pinning settings
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
                                }
                            }
                            viewModel.setEnhancedSupportEnabled(enabled)
                        }
                    )
                }
            }

            // Screen Pinning Permission Card
            if (uiState.enhancedSupportEnabled) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isScreenPinningEnabled.value) {
                            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                        } else {
                            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(Dimensions.Space16),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (isScreenPinningEnabled.value) Icons.Default.CheckCircle else Icons.Default.Error,
                            contentDescription = null,
                            tint = if (isScreenPinningEnabled.value) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(Dimensions.Space12))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Screen Pinning Permission",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = if (isScreenPinningEnabled.value) "Permission granted" else "Permission required for kiosk mode",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (!isScreenPinningEnabled.value) {
                            TextButton(onClick = {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
                                }
                            }) {
                                Text("Grant")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        // Skip Option
        TextButton(
            onClick = onSkip,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Skip for Now")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        // Continue Button
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Text(if (uiState.enhancedSupportEnabled) "Continue with Enhanced Support" else "Continue")
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

private data class FeatureItem(
    val id: String,
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
private fun FeatureCard(
    feature: FeatureItem,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (expanded) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Space16)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimensions.Space4),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = feature.icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(Dimensions.Space16))
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(Dimensions.Space12))
                Text(
                    text = feature.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
