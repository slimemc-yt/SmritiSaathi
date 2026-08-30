package com.socklet.smritisaathi.ui.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.socklet.smritisaathi.data.datastore.DataStoreManager
import com.socklet.smritisaathi.ui.patient.PatientContainerViewModel
import com.socklet.smritisaathi.ui.theme.Dimensions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(
    onNavigateToLanguage: () -> Unit,
    onNavigateToPatientHome: () -> Unit,
    onNavigateToFamilyHome: () -> Unit,
    onNavigateToDoctorHome: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dataStoreManager = hiltViewModel<PatientContainerViewModel>().dataStoreManager
    var startAnimation by remember { mutableStateOf(false) }

    val scaleAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "scale"
    )

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "alpha"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1200) // Fast smooth splash UX

        // Smart routing: Check real Firebase authentication and paired session
        val currentFirebaseUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
        val isAnonymous = currentFirebaseUser?.isAnonymous == true
        val pairedPatientId = dataStoreManager.pairedPatientIdFlow.first()
        val cachedRole = dataStoreManager.userRoleFlow.first()

        android.util.Log.d("SplashScreen", "Startup Auth Check: uid=${currentFirebaseUser?.uid}, isAnonymous=$isAnonymous, cachedRole=$cachedRole, pairedPatientId=$pairedPatientId")

        if (currentFirebaseUser != null && !isAnonymous) {
            // Real authenticated account (Google/Phone)
            val userProfile = viewModel.loadUserProfile(currentFirebaseUser.uid).getOrNull()
            val effectiveRole = userProfile?.role ?: try {
                if (!cachedRole.isNullOrBlank()) com.socklet.smritisaathi.domain.model.UserRole.valueOf(cachedRole) else com.socklet.smritisaathi.domain.model.UserRole.FAMILY
            } catch (_: Exception) { com.socklet.smritisaathi.domain.model.UserRole.FAMILY }

            android.util.Log.d("SplashScreen", "✓ Authenticated Real User: role=$effectiveRole, profileCompleted=${userProfile?.profileCompleted}")

            when (effectiveRole) {
                com.socklet.smritisaathi.domain.model.UserRole.DOCTOR -> {
                    onNavigateToDoctorHome()
                }
                com.socklet.smritisaathi.domain.model.UserRole.FAMILY -> {
                    onNavigateToFamilyHome()
                }
                com.socklet.smritisaathi.domain.model.UserRole.PATIENT -> {
                    onNavigateToPatientHome()
                }
            }
        } else if (!pairedPatientId.isNullOrBlank()) {
            // Paired patient device
            android.util.Log.d("SplashScreen", "✓ Paired Patient Session found: $pairedPatientId")
            onNavigateToPatientHome()
        } else {
            // No authenticated session
            android.util.Log.d("SplashScreen", "No existing session, routing to language/role selection")
            onNavigateToLanguage()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .scale(scaleAnimation)
                .alpha(alphaAnimation)
        ) {
            // App Logo/Icon would go here
            Text(
                text = "🧠",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(bottom = Dimensions.Space24)
            )

            Text(
                text = "SmritiSaathi",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimensions.Space16))

            Text(
                text = "Your Memory Companion",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}
