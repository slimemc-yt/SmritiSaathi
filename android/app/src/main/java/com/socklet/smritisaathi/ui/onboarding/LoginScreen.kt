package com.socklet.smritisaathi.ui.onboarding

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.socklet.smritisaathi.domain.model.UserRole
import com.socklet.smritisaathi.ui.components.PrimaryButton
import com.socklet.smritisaathi.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    initialRole: UserRole? = null,
    onLoginSuccess: (UserRole, Boolean) -> Unit, // role, isNewUser
    onNavigateToOtp: (String) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val role = initialRole ?: uiState.selectedRole ?: UserRole.FAMILY

    LaunchedEffect(initialRole) {
        if (initialRole != null) {
            viewModel.setSelectedRole(initialRole)
        }
    }

    var phoneNumber by remember { mutableStateOf("9876543210") }
    var showPhoneAuth by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    // Resolve Web Client ID dynamically from resources if available
    val webClientId = remember {
        try {
            val resId = context.resources.getIdentifier("default_web_client_id", "string", context.packageName)
            val id = if (resId != 0) context.getString(resId) else null
            // Fallback to hardcoded web client ID from google-services.json for hackathon stability
            id ?: "1070465757550-t9s39te5db8omoqkmcgd5m19gkvmlukq.apps.googleusercontent.com"
        } catch (_: Exception) {
            "1070465757550-t9s39te5db8omoqkmcgd5m19gkvmlukq.apps.googleusercontent.com"
        }
    }

    val gso = remember(webClientId) {
        val builder = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
        if (!webClientId.isNullOrBlank()) {
            builder.requestIdToken(webClientId)
        }
        builder.build()
    }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Safely extract Google Sign-In result
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.getResult(ApiException::class.java)
                
                // Safely extract ID token
                val idToken = account?.idToken
                if (!idToken.isNullOrBlank()) {
                    viewModel.signInWithGoogle(
                        idToken = idToken,
                        role = role,
                        onSuccess = { user ->
                            // CRITICAL: Use the actual user role from database, not the selected role
                            // This ensures users are routed to their correct dashboard based on their registered role
                            val actualRole = user.role
                            val isNew = !user.profileCompleted && user.linkedPatientIds.isEmpty()
                            
                            if (actualRole != role && user.profileCompleted) {
                                // This shouldn't happen because AuthRepository now blocks role mismatches
                                // But as a safety net, log it
                                android.util.Log.w("LoginScreen", "Role mismatch detected: selected=$role, actual=$actualRole")
                            }
                            
                            onLoginSuccess(actualRole, isNew)
                        },
                        onError = { localError = it }
                    )
                } else {
                    // No ID token — likely OAuth config issue, fall back to demo
                    viewModel.quickDemoLogin(
                        role = role,
                        onSuccess = { onLoginSuccess(role, false) },
                        onError = { localError = it }
                    )
                }
            } catch (_: Exception) {
                android.util.Log.e("LoginScreen", "Google extraction failed, falling back to Demo")
                // Any error during credential extraction — fall back to demo login
                // Never crash on Google Sign-In; always allow user retry
                viewModel.quickDemoLogin(
                    role = role,
                    onSuccess = { onLoginSuccess(role, false) },
                    onError = { localError = it }
                )
            }
        } else {
            // User cancelled the account picker — do not crash, stay on screen
            // Clear any previous error so UI resets cleanly
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (role == UserRole.DOCTOR) "Doctor / Health Worker Sign In" else "Caregiver Sign In") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .safeDrawingPadding()
                .padding(Dimensions.ScreenPadding)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (role == UserRole.DOCTOR) Icons.Default.MedicalServices else Icons.Default.FamilyRestroom,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.height(Dimensions.Space16))

                Text(
                    text = if (role == UserRole.DOCTOR) "Welcome, Doctor" else "Welcome, Family Caregiver",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimensions.Space8))

                Text(
                    text = if (role == UserRole.DOCTOR)
                        "Sign in to oversee assigned patients, review cognitive trends, and push clinical guidance."
                    else
                        "Sign in to monitor your loved one, receive behavioral alerts, and customize daily routines.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimensions.Space32))

                // Primary Google Sign-In Button
                Button(
                    onClick = {
                        try {
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            // Clear the silent sign-in state first so the Google account
                            // CHOOSER always appears — never silently reuse the previously
                            // authorized account after a sign-out.
                            googleSignInClient.signOut().addOnCompleteListener {
                                googleSignInLauncher.launch(googleSignInClient.signInIntent)
                            }
                        } catch (e: Exception) {
                            viewModel.quickDemoLogin(
                                role = role,
                                onSuccess = { onLoginSuccess(role, false) },
                                onError = { localError = it }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimensions.ButtonHeight),
                    shape = RoundedCornerShape(Dimensions.ButtonCornerRadius),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Google",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(Dimensions.Space12))
                        Text(
                            text = "Sign in with Google",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.Space16))

                // One-Tap Instant Hackathon / Dev Login
                OutlinedButton(
                    onClick = {
                        viewModel.quickDemoLogin(
                            role = role,
                            onSuccess = { onLoginSuccess(role, false) },
                            onError = { localError = it }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimensions.ButtonHeight),
                    shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
                ) {
                    Text(
                        text = "⚡ Instant Demo Login (${if (role == UserRole.DOCTOR) "Dr. Baruah" else "Caregiver"})",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                if (localError != null || uiState.error != null) {
                    Spacer(modifier = Modifier.height(Dimensions.Space16))
                    Text(
                        text = localError ?: uiState.error ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Secondary Phone OTP Toggle
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { showPhoneAuth = !showPhoneAuth }) {
                    Text(
                        text = if (showPhoneAuth) "▲ Hide Phone Number Login" else "▼ Use Phone OTP instead",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                AnimatedVisibility(visible = showPhoneAuth) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dimensions.Space12)
                    ) {
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = {
                                if (it.length <= 10 && it.all { c -> c.isDigit() }) {
                                    phoneNumber = it
                                    localError = null
                                }
                            },
                            label = { Text("Phone Number") },
                            prefix = { Text("+91 ") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(Dimensions.Space12))

                        PrimaryButton(
                            text = "Send Phone OTP (Demo: 123456)",
                            onClick = {
                                viewModel.sendOtp(
                                    context = context,
                                    phoneNumber = "+91$phoneNumber",
                                    onCodeSent = { onNavigateToOtp("+91$phoneNumber") },
                                    onVerificationFailed = { localError = it }
                                )
                            },
                            enabled = phoneNumber.length == 10 && !uiState.isLoading,
                            isLoading = uiState.isLoading
                        )
                    }
                }
            }
        }
    }
}
