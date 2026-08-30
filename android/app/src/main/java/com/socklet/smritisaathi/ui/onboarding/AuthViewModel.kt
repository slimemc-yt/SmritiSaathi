package com.socklet.smritisaathi.ui.onboarding

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.socklet.smritisaathi.data.datastore.DataStoreManager
import com.socklet.smritisaathi.domain.model.Patient
import com.socklet.smritisaathi.domain.model.User
import com.socklet.smritisaathi.domain.model.UserRole
import com.socklet.smritisaathi.domain.repository.AuthRepository
import com.socklet.smritisaathi.domain.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val phoneNumber: String = "",
    val verificationId: String? = null,
    val resendToken: PhoneAuthProvider.ForceResendingToken? = null,
    val otpSent: Boolean = false,
    val isLoggedIn: Boolean = false,
    val currentUser: User? = null,
    val selectedRole: UserRole? = null,
    val pairedPatient: Patient? = null,
    val verificationComplete: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val patientRepository: PatientRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val exceptionHandler = kotlinx.coroutines.CoroutineExceptionHandler { _, throwable ->
        _uiState.update { it.copy(isLoading = false, error = throwable.message ?: "An unexpected error occurred") }
    }

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        checkCurrentUser()
        viewModelScope.launch(exceptionHandler) {
            authRepository.syncAllExistingDoctors()
        }
    }

    private fun checkCurrentUser() {
        viewModelScope.launch(exceptionHandler) {
            val user = authRepository.getCurrentUser()
            if (user != null) {
                // First check DataStore for cached role to ensure fast accurate role resolution
                val cachedRole = dataStoreManager.userRoleFlow.first()
                val initialRole = try {
                    if (!cachedRole.isNullOrBlank()) UserRole.valueOf(cachedRole) else user.role
                } catch (_: Exception) {
                    user.role
                }

                _uiState.update {
                    it.copy(
                        isLoggedIn = true,
                        currentUser = user.copy(role = initialRole),
                        selectedRole = initialRole
                    )
                }

                // Try to get updated user with correct role from Firestore
                val fullUser = authRepository.getUser(user.id).getOrNull()
                if (fullUser != null) {
                    dataStoreManager.saveUserRole(fullUser.role.name)
                    _uiState.update {
                        it.copy(
                            currentUser = fullUser,
                            selectedRole = fullUser.role
                        )
                    }
                }
            }
        }
    }

    fun setSelectedRole(role: UserRole) {
        _uiState.update { it.copy(selectedRole = role) }
        viewModelScope.launch(exceptionHandler) {
            dataStoreManager.saveUserRole(role.name)
        }
    }

    fun setPhoneNumber(phone: String) {
        _uiState.update { it.copy(phoneNumber = phone, error = null) }
    }

    /**
     * Sign In using Google ID token
     */
    fun signInWithGoogle(
        idToken: String,
        role: UserRole = _uiState.value.selectedRole ?: UserRole.FAMILY,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch(exceptionHandler) {
            val result = authRepository.signInWithGoogle(idToken, role)
            if (result.isSuccess) {
                val user = result.getOrThrow()
                dataStoreManager.saveUserRole(user.role.name)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        currentUser = user,
                        selectedRole = user.role
                    )
                }
                onSuccess(user)
            } else {
                val err = result.exceptionOrNull()?.message ?: "Google Sign-In failed"
                _uiState.update { it.copy(isLoading = false, error = err) }
                onError(err)
            }
        }
    }

    /**
     * Pair Patient device using 6-digit code
     */
    fun pairPatientDevice(
        pairingCode: String,
        onSuccess: (Patient) -> Unit,
        onError: (String) -> Unit
    ) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch(exceptionHandler) {
            try {
                val patientResult = patientRepository.getPatientByPairingCode(pairingCode)
                if (patientResult.isFailure) {
                    val msg = patientResult.exceptionOrNull()?.message ?: "Invalid pairing code"
                    _uiState.update { it.copy(isLoading = false, error = msg) }
                    onError(msg)
                    return@launch
                }

                val patient = patientResult.getOrThrow()

                // Anonymous Auth for patient device identity
                val anonResult = authRepository.signInAnonymously()
                val deviceUid = anonResult.getOrDefault("anon_${System.currentTimeMillis()}")

                // Bind device in Firestore
                patientRepository.bindPatientDevice(patient.id, deviceUid)

                // Save permanently in DataStore
                dataStoreManager.savePatientPairing(
                    patientId = patient.id,
                    patientName = patient.name,
                    dementiaTier = patient.dementiaStage.tier
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        pairedPatient = patient,
                        selectedRole = UserRole.PATIENT
                    )
                }
                onSuccess(patient)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
                onError(e.message ?: "Failed to pair device")
            }
        }
    }

    /**
     * Start Instant Demo Patient Session (for fast emulator/hackathon judging testing)
     */
    fun startDemoPatientSession(
        onSuccess: (Patient) -> Unit,
        onError: (String) -> Unit
    ) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch(exceptionHandler) {
            try {
                val patientResult = patientRepository.createOrGetDemoPatient()
                val patient = patientResult.getOrThrow()

                val anonResult = authRepository.signInAnonymously()
                val deviceUid = anonResult.getOrDefault("anon_demo_device")
                patientRepository.bindPatientDevice(patient.id, deviceUid)

                dataStoreManager.savePatientPairing(
                    patientId = patient.id,
                    patientName = patient.name,
                    dementiaTier = patient.dementiaStage.tier
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        pairedPatient = patient,
                        selectedRole = UserRole.PATIENT
                    )
                }
                onSuccess(patient)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
                onError(e.message ?: "Demo initialization failed")
            }
        }
    }

    /**
     * Quick Demo Sign In for Family / Doctor roles
     */
    fun quickDemoLogin(
        role: UserRole,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch(exceptionHandler) {
            val result = authRepository.signInWithDemoAccount(role)
            if (result.isSuccess) {
                val user = result.getOrThrow()
                dataStoreManager.saveUserRole(user.role.name)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        currentUser = user,
                        selectedRole = user.role
                    )
                }
                onSuccess(user)
            } else {
                val err = result.exceptionOrNull()?.message ?: "Login failed"
                _uiState.update { it.copy(isLoading = false, error = err) }
                onError(err)
            }
        }
    }

    /**
     * Send OTP to phone number (Secondary fallback)
     */
    fun sendOtp(
        context: Context,
        phoneNumber: String,
        onCodeSent: () -> Unit = {},
        onVerificationFailed: (String) -> Unit = {}
    ) {
        if (phoneNumber.isBlank() || phoneNumber.length < 10) {
            _uiState.update { it.copy(error = "Please enter a valid phone number") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null, phoneNumber = phoneNumber) }

        val activity = context as? Activity
        if (activity == null) {
            _uiState.update { it.copy(isLoading = false, error = "Activity context required") }
            onVerificationFailed("Activity context required")
            return
        }

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                verifyCredential(credential, onVerificationFailed)
            }

            override fun onVerificationFailed(e: com.google.firebase.FirebaseException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Verification failed. Check phone number."
                    )
                }
                onVerificationFailed(e.message ?: "Verification failed")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        verificationId = verificationId,
                        resendToken = token,
                        otpSent = true,
                        error = null
                    )
                }
                onCodeSent()
            }
        }

        try {
            val phoneAuthOptions = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to send OTP"
                )
            }
            onVerificationFailed(e.message ?: "Failed to send OTP")
        }
    }

    /**
     * Resend OTP to phone number (Secondary fallback)
     */
    fun resendOtp(
        context: Context,
        onCodeSent: () -> Unit = {},
        onVerificationFailed: (String) -> Unit = {}
    ) {
        val phoneNumber = _uiState.value.phoneNumber
        val resendToken = _uiState.value.resendToken

        if (phoneNumber.isBlank()) {
            _uiState.update { it.copy(error = "Phone number missing") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        val activity = context as? Activity
        if (activity == null) {
            _uiState.update { it.copy(isLoading = false, error = "Activity context required") }
            onVerificationFailed("Activity context required")
            return
        }

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                verifyCredential(credential, onVerificationFailed)
            }

            override fun onVerificationFailed(e: com.google.firebase.FirebaseException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Verification failed"
                    )
                }
                onVerificationFailed(e.message ?: "Verification failed")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        verificationId = verificationId,
                        resendToken = token,
                        otpSent = true,
                        error = null
                    )
                }
                onCodeSent()
            }
        }

        try {
            val optionsBuilder = PhoneAuthOptions.newBuilder()
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)

            if (resendToken != null) {
                optionsBuilder.setForceResendingToken(resendToken)
            }

            PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to resend OTP"
                )
            }
            onVerificationFailed(e.message ?: "Failed to resend OTP")
        }
    }

    fun verifyOtp(
        code: String,
        onSuccess: (User) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        if (code.length != 6) {
            _uiState.update { it.copy(error = "Please enter a valid 6-digit code") }
            onError("Please enter a valid 6-digit code")
            return
        }

        val verificationId = _uiState.value.verificationId
        if (verificationId == null) {
            _uiState.update { it.copy(error = "Verification ID missing. Please resend OTP.") }
            onError("Verification ID missing. Please resend OTP.")
            return
        }

        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        verifyCredential(credential, onError, onSuccess)
    }

    private fun verifyCredential(
        credential: PhoneAuthCredential,
        onError: (String) -> Unit = {},
        onSuccess: (User) -> Unit = {}
    ) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch(exceptionHandler) {
            val result = authRepository.signInWithCredential(credential)
            if (result.isSuccess) {
                val user = result.getOrThrow()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        currentUser = user,
                        verificationComplete = true
                    )
                }
                onSuccess(user)
            } else {
                val error = result.exceptionOrNull()?.message ?: "Authentication failed"
                _uiState.update { it.copy(isLoading = false, error = error) }
                onError(error)
            }
        }
    }

    /**
     * Load a user profile from Firestore (used to resolve linked patients / role).
     */
    suspend fun loadUserProfile(userId: String): Result<com.socklet.smritisaathi.domain.model.User?> {
        return authRepository.getUser(userId)
    }

    fun signOut() {
        viewModelScope.launch(exceptionHandler) {
            // Clear UID-scoped local session state (NOT cloud data, NOT the
            // device-level language preference — clearUserSession is enough).
            dataStoreManager.clearUserSession(authRepository.getCurrentUserId())
            authRepository.signOut()
            _uiState.update {
                AuthUiState(
                    isLoggedIn = false,
                    currentUser = null,
                    pairedPatient = null
                )
            }
        }
    }

    suspend fun deleteAccount(): Result<Unit> {
        return try {
            val userId = authRepository.getCurrentUserId()
            if (userId == null) {
                return Result.failure(Exception("No user logged in"))
            }
            
            android.util.Log.d("AuthViewModel", "Deleting account for user: $userId")
            val result = authRepository.deleteAccount(userId)
            
            if (result.isSuccess) {
                android.util.Log.d("AuthViewModel", "✓ Account deleted successfully")
                _uiState.update {
                    AuthUiState(
                        isLoggedIn = false,
                        currentUser = null,
                        pairedPatient = null
                    )
                }
            } else {
                android.util.Log.e("AuthViewModel", "✗ Account deletion failed: ${result.exceptionOrNull()?.message}")
            }
            
            result
        } catch (e: Exception) {
            android.util.Log.e("AuthViewModel", "✗ Account deletion error: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun searchDoctors(query: String): List<User> {
        return authRepository.searchDoctors(query).getOrDefault(emptyList())
    }

    fun syncDoctorDirectory(user: User) {
        viewModelScope.launch {
            authRepository.syncDoctorToPublicDirectory(user)
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
