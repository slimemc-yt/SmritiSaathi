package com.socklet.smritisaathi.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.ui.components.PrimaryButton
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientPairingScreen(
    onPairingSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var pairingCode by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Link Patient Device") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimensions.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.VpnKey,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(Dimensions.Space16))

                Text(
                    text = "Enter 6-Digit Pairing Code",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimensions.Space8))

                Text(
                    text = "Ask your family caregiver for the 6-digit device code shown on their SmritiSaathi app.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimensions.Space32))

                OutlinedTextField(
                    value = pairingCode,
                    onValueChange = {
                        if (it.length <= 6 && it.all { c -> c.isDigit() }) {
                            pairingCode = it
                            localError = null
                        }
                    },
                    label = { Text("6-Digit Code (e.g. 842913)") },
                    textStyle = MaterialTheme.typography.headlineMedium.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 8.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    isError = localError != null || uiState.error != null,
                    supportingText = {
                        localError?.let { Text(it) }
                        uiState.error?.let { Text(it) }
                    }
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
            ) {
                PrimaryButton(
                    text = "Connect Device ✅",
                    onClick = {
                        if (pairingCode.length != 6) {
                            localError = "Please enter all 6 digits"
                            return@PrimaryButton
                        }
                        viewModel.pairPatientDevice(
                            pairingCode = pairingCode,
                            onSuccess = { onPairingSuccess() },
                            onError = { localError = it }
                        )
                    },
                    enabled = pairingCode.length == 6 && !uiState.isLoading,
                    isLoading = uiState.isLoading
                )

                OutlinedButton(
                    onClick = {
                        viewModel.startDemoPatientSession(
                            onSuccess = { onPairingSuccess() },
                            onError = { localError = it }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimensions.ButtonHeight),
                    shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
                ) {
                    Text("⚡ Quick Start Demo Patient (No Code)", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
