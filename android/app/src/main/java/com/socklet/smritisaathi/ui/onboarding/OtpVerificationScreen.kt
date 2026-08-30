package com.socklet.smritisaathi.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.socklet.smritisaathi.domain.model.UserRole
import com.socklet.smritisaathi.ui.components.PrimaryButton
import com.socklet.smritisaathi.ui.theme.Dimensions

@Composable
fun OtpVerificationScreen(
    onVerificationSuccess: (UserRole) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var otp by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }

    val focusRequesters = remember { List(6) { FocusRequester() } }

    // Handle verification success
    LaunchedEffect(uiState.verificationComplete, uiState.currentUser) {
        if (uiState.verificationComplete && uiState.currentUser != null) {
            onVerificationSuccess(uiState.selectedRole ?: UserRole.FAMILY)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.ScreenPadding)
    ) {
        Text(
            text = "Enter verification code",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimensions.Space8))

        Text(
            text = "Enter code sent to ${uiState.phoneNumber.ifBlank { "+919876543210" }} (Test code: 123456)",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimensions.Space40))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Space12, Alignment.CenterHorizontally)
        ) {
            repeat(6) { index ->
                OtpDigitField(
                    value = if (otp.length > index) otp[index].toString() else "",
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            val newOtp = if (newValue.isEmpty()) {
                                otp.take(index)
                            } else {
                                otp.take(index) + newValue
                            }
                            otp = newOtp
                            localError = null

                            // Auto-focus next field
                            if (newValue.isNotEmpty() && index < 5) {
                                focusRequesters[index + 1].requestFocus()
                            }

                            // Auto-verify when all 6 digits entered
                            if (newOtp.length == 6) {
                                viewModel.verifyOtp(
                                    code = newOtp,
                                    onSuccess = { role ->
                                        onVerificationSuccess(uiState.selectedRole ?: UserRole.FAMILY)
                                    },
                                    onError = { error ->
                                        localError = error
                                        otp = "" // Clear on error
                                    }
                                )
                            }
                        }
                    },
                    focusRequester = focusRequesters[index],
                    modifier = Modifier.weight(1f),
                    isError = localError != null || uiState.error != null
                )
            }
        }

        // Error message
        (localError ?: uiState.error)?.let { errorMessage ->
            Spacer(modifier = Modifier.height(Dimensions.Space16))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        // Resend OTP
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Didn't receive code? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            TextButton(
                onClick = {
                    localError = null
                    otp = ""
                    viewModel.resendOtp(
                        context = context,
                        onCodeSent = {
                            // Show success message
                        },
                        onVerificationFailed = { error ->
                            localError = error
                        }
                    )
                },
                enabled = !uiState.isLoading
            ) {
                Text("Resend")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Verify & Continue",
            onClick = {
                if (otp.length != 6) {
                    localError = "Please enter a valid 6-digit code"
                    return@PrimaryButton
                }

                viewModel.verifyOtp(
                    code = otp,
                    onSuccess = {
                        onVerificationSuccess(uiState.selectedRole ?: UserRole.FAMILY)
                    },
                    onError = { error ->
                        localError = error
                    }
                )
            },
            enabled = otp.length == 6 && !uiState.isLoading,
            isLoading = uiState.isLoading
        )

        Spacer(modifier = Modifier.height(Dimensions.Space16))
    }
}

@Composable
private fun OtpDigitField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .aspectRatio(1f)
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        singleLine = true,
        isError = isError,
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            textAlign = TextAlign.Center
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.error
        )
    )
}
