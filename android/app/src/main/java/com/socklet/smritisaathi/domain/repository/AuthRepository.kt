package com.socklet.smritisaathi.domain.repository

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.socklet.smritisaathi.domain.model.User
import com.socklet.smritisaathi.domain.model.UserRole
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

data class OtpSendResult(
    val verificationId: String,
    val resendToken: PhoneAuthProvider.ForceResendingToken?
)

@Singleton
class AuthRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    private val usersCollection = firestore.collection("users")

    /**
     * Sign in with Google ID token
     */
    suspend fun signInWithGoogle(idToken: String, selectedRole: UserRole): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("Firebase user is null"))

            Log.d("AuthRepository", "=== GOOGLE SIGN-IN START ===")
            Log.d("AuthRepository", "Firebase UID: ${firebaseUser.uid}")
            Log.d("AuthRepository", "Email: ${firebaseUser.email}")
            Log.d("AuthRepository", "Display Name: ${firebaseUser.displayName}")

            val userId = firebaseUser.uid
            Log.d("AuthRepository", "Looking up user doc in Firestore at /users/$userId")
            var existingUser = getUser(userId).getOrNull()

            Log.d("AuthRepository", "Existing user from Firestore: ${if (existingUser != null) "FOUND" else "NULL"}")
            if (existingUser != null) {
                Log.d("AuthRepository", "  - profileCompleted: ${existingUser.profileCompleted}")
                Log.d("AuthRepository", "  - linkedPatientIds: ${existingUser.linkedPatientIds}")
                Log.d("AuthRepository", "  - role: ${existingUser.role}")
                Log.d("AuthRepository", "  - name: ${existingUser.name}")
                
                // CRITICAL: Check if the selected role matches the existing user's role
                // Prevent same Google account from being used for different roles if profile is already completed with linked patients
                if (existingUser.profileCompleted && existingUser.linkedPatientIds.isNotEmpty() && existingUser.role != selectedRole) {
                    Log.w("AuthRepository", "ROLE MISMATCH: Account registered as ${existingUser.role}, but tried to sign in as $selectedRole")
                    return Result.failure(Exception(
                        "This Google account is already registered as a ${existingUser.role.displayName} account. " +
                        "Please use a different Google account for a ${selectedRole.displayName} account, " +
                        "or sign in as a ${existingUser.role.displayName}."
                    ))
                }
            }

            // Recovery: accounts that completed onboarding BEFORE the profileCompleted /
            // linkedPatientIds linkage existed have orphaned patients (createdBy = uid)
            // that were never linked to their user doc. Auto-link the first one so the
            // user returns straight to their dashboard instead of re-registering.
            if (existingUser != null && !existingUser.profileCompleted && existingUser.linkedPatientIds.isEmpty()) {
                try {
                    val orphanSnapshot = firestore.collection("patients")
                        .whereEqualTo("createdBy", userId)
                        .limit(1)
                        .get()
                        .await()
                    val orphanId = orphanSnapshot.documents.firstOrNull()?.id
                    if (orphanId != null) {
                        existingUser = existingUser.copy(
                            profileCompleted = true,
                            linkedPatientIds = listOf(orphanId)
                        )
                        Log.d("AuthRepository", "Recovered orphaned patient $orphanId for UID $userId")
                    }
                } catch (e: Exception) {
                    Log.w("AuthRepository", "Orphan patient recovery skipped: ${e.message}")
                }
            }

            val user = if (existingUser != null) {
                // If the account's profile was not fully completed with patients, or if signing in as DOCTOR, update role to selectedRole
                val hasPatients = existingUser.linkedPatientIds.isNotEmpty()
                val isCompleted = existingUser.profileCompleted || hasPatients
                val effectiveRole = if (!hasPatients || selectedRole == UserRole.DOCTOR) selectedRole else existingUser.role
                val effectiveDoctorCode = if (effectiveRole == UserRole.DOCTOR) {
                    existingUser.doctorCode ?: generateDoctorCode()
                } else {
                    existingUser.doctorCode
                }

                existingUser.copy(
                    role = effectiveRole,
                    doctorCode = effectiveDoctorCode,
                    specialty = if (effectiveRole == UserRole.DOCTOR) existingUser.specialty ?: "Neurology / Cognitive Specialist" else existingUser.specialty,
                    hospitalName = if (effectiveRole == UserRole.DOCTOR) existingUser.hospitalName ?: "Regional Medical Center" else existingUser.hospitalName,
                    lastLoginAt = Date(),
                    name = if (existingUser.name.isBlank()) firebaseUser.displayName ?: "" else existingUser.name,
                    email = existingUser.email ?: firebaseUser.email,
                    profilePhotoUrl = existingUser.profilePhotoUrl ?: firebaseUser.photoUrl?.toString(),
                    profileCompleted = isCompleted || (effectiveRole == UserRole.DOCTOR)
                )
            } else {
                User(
                    id = userId,
                    phoneNumber = firebaseUser.phoneNumber ?: "",
                    role = selectedRole,
                    name = firebaseUser.displayName ?: (if (selectedRole == UserRole.DOCTOR) "Dr. Medical Officer" else ""),
                    email = firebaseUser.email,
                    profilePhotoUrl = firebaseUser.photoUrl?.toString(),
                    doctorCode = if (selectedRole == UserRole.DOCTOR) generateDoctorCode() else null,
                    specialty = if (selectedRole == UserRole.DOCTOR) "Neurology / Cognitive Specialist" else null,
                    hospitalName = if (selectedRole == UserRole.DOCTOR) "Regional Medical Center" else null,
                    profileCompleted = (selectedRole == UserRole.DOCTOR),
                    createdAt = Date(),
                    lastLoginAt = Date()
                )
            }

            saveUser(user).getOrThrow()
            Log.d("AuthRepository", "=== SIGN-IN RESULT ===")
            Log.d("AuthRepository", "Returning user with:")
            Log.d("AuthRepository", "  - profileCompleted: ${user.profileCompleted}")
            Log.d("AuthRepository", "  - linkedPatientIds: ${user.linkedPatientIds}")
            Log.d("AuthRepository", "  - isNew will be: ${!user.profileCompleted && user.linkedPatientIds.isEmpty()}")
            Log.d("AuthRepository", "=== GOOGLE SIGN-IN END ===")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sign in anonymously for Patient device pairing (zero passwords/Google accounts needed for elderly)
     */
    suspend fun signInAnonymously(): Result<String> {
        return try {
            val currentAuth = auth.currentUser
            if (currentAuth != null && currentAuth.isAnonymous) {
                return Result.success(currentAuth.uid)
            }
            val authResult = auth.signInAnonymously().await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("Anonymous user is null"))
            Result.success(firebaseUser.uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Send OTP to phone number (retained as secondary fallback)
     */
    suspend fun sendOtp(
        phoneNumber: String,
        options: PhoneAuthOptions.Builder,
        onCodeSent: (String, PhoneAuthProvider.ForceResendingToken) -> Unit,
        onVerificationCompleted: (PhoneAuthCredential) -> Unit,
        onVerificationFailed: (Exception) -> Unit
    ): Result<Unit> {
        return try {
            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    onVerificationCompleted(credential)
                }

                override fun onVerificationFailed(e: com.google.firebase.FirebaseException) {
                    onVerificationFailed(e)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    onCodeSent(verificationId, token)
                }
            }

            val phoneAuthOptions = options
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sign in with Phone Auth credential
     */
    suspend fun signInWithCredential(credential: PhoneAuthCredential): Result<User> {
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("User is null"))

            val phoneNumber = firebaseUser.phoneNumber ?: ""
            val userId = firebaseUser.uid

            val existingUser = getUser(userId).getOrNull()

            val user = if (existingUser != null) {
                existingUser.copy(lastLoginAt = Date())
            } else {
                User(
                    id = userId,
                    phoneNumber = phoneNumber,
                    role = UserRole.FAMILY,
                    createdAt = Date(),
                    lastLoginAt = Date()
                )
            }

            saveUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Generate unique permanent Doctor Code (e.g. "DR-8821")
     */
    private fun generateDoctorCode(): String {
        val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789" // Non-ambiguous chars
        val suffix = (1..4).map { chars.random() }.joinToString("")
        return "DR-$suffix"
    }

    /**
     * Instant Demo / Dev Sign In (Bypasses SMS verification if Firebase Phone Auth is pending configuration)
     */
    suspend fun signInWithDemoAccount(role: UserRole, phoneNumber: String = "+919876543210"): Result<User> {
        Log.w("AuthRepository", "Falling back to DEMO account login for role: ${role.name}")
        return try {
            // Demo must ALWAYS run on an anonymous session so it never reads or
            // overwrites a real Google account's Firestore profile.
            val current = auth.currentUser
            val userId = if (current != null && current.isAnonymous) {
                current.uid
            } else {
                if (current != null) auth.signOut()
                auth.signInAnonymously().await().user?.uid ?: "demo_${role.name.lowercase()}"
            }

            val existingUser = getUser(userId).getOrNull()
            val user = existingUser?.copy(
                lastLoginAt = Date(),
                role = role,
                doctorCode = if (role == UserRole.DOCTOR) existingUser.doctorCode ?: "DR-8821" else existingUser.doctorCode,
                specialty = if (role == UserRole.DOCTOR) existingUser.specialty ?: "Neurologist & Memory Specialist" else existingUser.specialty,
                hospitalName = if (role == UserRole.DOCTOR) existingUser.hospitalName ?: "GNRC Medical Institute, Guwahati" else existingUser.hospitalName,
                profileCompleted = if (role == UserRole.DOCTOR) true else existingUser.profileCompleted
            ) ?: User(
                id = userId,
                phoneNumber = phoneNumber,
                role = role,
                name = if (role == UserRole.DOCTOR) "Dr. Pranab Baruah" else "Ramesh Devi",
                doctorCode = if (role == UserRole.DOCTOR) "DR-8821" else null,
                specialty = if (role == UserRole.DOCTOR) "Neurologist & Memory Specialist" else null,
                hospitalName = if (role == UserRole.DOCTOR) "GNRC Medical Institute, Guwahati" else null,
                profileCompleted = (role == UserRole.DOCTOR),
                createdAt = Date(),
                lastLoginAt = Date()
            )

            saveUser(user)
            Result.success(user)
        } catch (e: Exception) {
            // Local offline fallback user
            val fallbackUser = User(
                id = "demo_user_${role.name.lowercase()}",
                phoneNumber = phoneNumber,
                role = role,
                name = if (role == UserRole.DOCTOR) "Dr. Pranab Baruah" else "Ramesh Devi",
                doctorCode = if (role == UserRole.DOCTOR) "DR-8821" else null,
                specialty = if (role == UserRole.DOCTOR) "Neurologist & Memory Specialist" else null,
                hospitalName = if (role == UserRole.DOCTOR) "GNRC Medical Institute, Guwahati" else null,
                profileCompleted = (role == UserRole.DOCTOR),
                createdAt = Date(),
                lastLoginAt = Date()
            )
            Result.success(fallbackUser)
        }
    }

    // ==================== DOCTOR DIRECTORY & SEARCH ====================

    /**
     * Helper for normalizing doctor codes into standard formats:
     * e.g. "8821" -> "DR-8821", "dr-8821" -> "DR-8821", "DR8821" -> "DR-8821"
     */
    fun normalizeDoctorCode(input: String): String {
        val trimmed = input.trim().uppercase()
        val stripped = trimmed.replace(" ", "").replace("-", "").removePrefix("DR")
        return if (stripped.isNotBlank()) "DR-$stripped" else ""
    }

    /**
     * Synchronize a registered doctor's profile to public Firestore collections
     * (/doctors and /doctorCodes) so they are immediately and publicly searchable
     * by all family members and caregivers regardless of user-specific collection rules.
     */
    suspend fun syncDoctorToPublicDirectory(user: User) {
        if (user.role != UserRole.DOCTOR) return
        try {
            val docCode = (user.doctorCode?.ifBlank { null } ?: generateDoctorCode()).trim().uppercase()
            val doctorData = mapOf(
                "id" to user.id,
                "name" to user.name.ifBlank { "Dr. Medical Specialist" },
                "specialization" to (user.specialty ?: "Neurologist & Cognitive Specialist"),
                "hospitalName" to (user.hospitalName ?: "Regional Medical Center"),
                "hospitalId" to "",
                "phone" to user.phoneNumber,
                "email" to (user.email ?: ""),
                "doctorCode" to docCode,
                "profilePhotoUrl" to user.profilePhotoUrl,
                "role" to "DOCTOR",
                "updatedAt" to Date()
            )

            // 1. Write to public /doctors/{userId}
            firestore.collection("doctors").document(user.id)
                .set(doctorData, com.google.firebase.firestore.SetOptions.merge())
                .await()

            // 2. Write to public /doctorCodes/{docCode} for instant zero-latency code lookup
            firestore.collection("doctorCodes").document(docCode)
                .set(
                    mapOf(
                        "doctorId" to user.id,
                        "doctorCode" to docCode,
                        "name" to user.name.ifBlank { "Dr. Medical Specialist" },
                        "specialty" to (user.specialty ?: "Neurologist & Cognitive Specialist"),
                        "hospitalName" to (user.hospitalName ?: "Regional Medical Center"),
                        "updatedAt" to Date()
                    ),
                    com.google.firebase.firestore.SetOptions.merge()
                ).await()

            Log.d("AuthRepository", "✓ Successfully published doctor profile & code $docCode to /doctors and /doctorCodes")
        } catch (e: Exception) {
            Log.w("AuthRepository", "Failed to sync doctor to public directory: ${e.message}")
        }
    }

    /**
     * Migration & Auto-Sync: Scans existing accounts and public collections to ensure
     * that all previously registered Doctors (even those created before this update)
     * are assigned unique permanent Doctor Codes and published to /doctors and /doctorCodes.
     */
    suspend fun syncAllExistingDoctors() {
        try {
            // 1. Check all users in /users with role == DOCTOR
            try {
                val usersSnap = usersCollection.whereEqualTo("role", UserRole.DOCTOR.name).get().await()
                for (doc in usersSnap.documents) {
                    val user = doc.data?.let { User.fromMap(it) } ?: continue
                    val docCode = user.doctorCode?.ifBlank { null } ?: generateDoctorCode()
                    val updatedUser = user.copy(doctorCode = docCode, profileCompleted = true)
                    if (user.doctorCode != docCode) {
                        usersCollection.document(user.id).update("doctorCode", docCode, "profileCompleted", true).await()
                    }
                    syncDoctorToPublicDirectory(updatedUser)
                }
            } catch (e: Exception) {
                Log.w("AuthRepository", "syncAllExistingDoctors /users query notice: ${e.message}")
            }

            // 2. Also check any entries in /doctors and ensure they have doctorCodes and are indexed
            try {
                val doctorsSnap = firestore.collection("doctors").get().await()
                for (doc in doctorsSnap.documents) {
                    val data = doc.data ?: continue
                    val existingCode = (data["doctorCode"] as? String) ?: (data["code"] as? String)
                    if (existingCode.isNullOrBlank()) {
                        val newCode = generateDoctorCode()
                        firestore.collection("doctors").document(doc.id).update(
                            mapOf(
                                "doctorCode" to newCode,
                                "updatedAt" to Date()
                            )
                        ).await()
                        firestore.collection("doctorCodes").document(newCode).set(
                            mapOf(
                                "doctorId" to doc.id,
                                "doctorCode" to newCode,
                                "name" to (data["name"] as? String ?: "Dr. Medical Specialist"),
                                "specialty" to (data["specialization"] as? String ?: "Neurologist"),
                                "hospitalName" to (data["hospitalName"] as? String ?: "Medical Center"),
                                "updatedAt" to Date()
                            ), com.google.firebase.firestore.SetOptions.merge()
                        ).await()
                    } else {
                        // Ensure it's in /doctorCodes
                        firestore.collection("doctorCodes").document(existingCode.trim().uppercase()).set(
                            mapOf(
                                "doctorId" to doc.id,
                                "doctorCode" to existingCode.trim().uppercase(),
                                "name" to (data["name"] as? String ?: "Dr. Medical Specialist"),
                                "specialty" to (data["specialization"] as? String ?: "Neurologist"),
                                "hospitalName" to (data["hospitalName"] as? String ?: "Medical Center"),
                                "updatedAt" to Date()
                            ), com.google.firebase.firestore.SetOptions.merge()
                        ).await()
                    }
                }
            } catch (e: Exception) {
                Log.w("AuthRepository", "syncAllExistingDoctors /doctors scan notice: ${e.message}")
            }
        } catch (e: Exception) {
            Log.w("AuthRepository", "syncAllExistingDoctors overall error: ${e.message}")
        }
    }

    /**
     * Search registered doctors by code (e.g. "DR-8821", "dr-8821", "8821") or by name/hospital/specialty.
     * Queries both public /doctors, /doctorCodes, and /users collections with complete fault-tolerance.
     */
    suspend fun searchDoctors(query: String): Result<List<User>> {
        return try {
            val cleanQuery = query.trim()
            val upperQuery = cleanQuery.uppercase()
            val normalizedCode = normalizeDoctorCode(cleanQuery)
            val strippedQuery = upperQuery.replace(" ", "").replace("-", "").removePrefix("DR")

            val candidateDoctors = mutableListOf<User>()

            // Source 1: Query the public /doctorCodes collection directly if query looks like a code
            if (normalizedCode.isNotBlank()) {
                try {
                    val codeDoc = firestore.collection("doctorCodes").document(normalizedCode).get().await()
                    if (codeDoc.exists()) {
                        val docId = codeDoc.getString("doctorId") ?: codeDoc.id
                        val name = codeDoc.getString("name") ?: "Dr. Medical Specialist"
                        val spec = codeDoc.getString("specialty") ?: "Neurologist"
                        val hosp = codeDoc.getString("hospitalName") ?: "Medical Center"
                        candidateDoctors.add(
                            User(
                                id = docId,
                                name = name,
                                role = UserRole.DOCTOR,
                                doctorCode = normalizedCode,
                                specialty = spec,
                                hospitalName = hosp,
                                profileCompleted = true
                            )
                        )
                    }
                } catch (e: Exception) {
                    Log.w("AuthRepository", "doctorCodes query fallback: ${e.message}")
                }
            }

            // Source 2: Query public /doctors collection
            try {
                val doctorsSnapshot = firestore.collection("doctors").limit(100).get().await()
                doctorsSnapshot.documents.forEach { doc ->
                    val data = doc.data ?: return@forEach
                    val id = doc.id
                    val name = data["name"] as? String ?: ""
                    val code = (data["doctorCode"] as? String) ?: (data["code"] as? String)
                    val spec = (data["specialization"] as? String) ?: (data["specialty"] as? String)
                    val hosp = data["hospitalName"] as? String
                    val phone = data["phone"] as? String ?: ""
                    val email = data["email"] as? String
                    val photo = data["profilePhotoUrl"] as? String
                    val u = User(
                        id = id,
                        name = name,
                        role = UserRole.DOCTOR,
                        doctorCode = code,
                        specialty = spec,
                        hospitalName = hosp,
                        phoneNumber = phone,
                        email = email,
                        profilePhotoUrl = photo,
                        profileCompleted = true
                    )
                    if (candidateDoctors.none { it.id == u.id }) {
                        candidateDoctors.add(u)
                    }
                }
            } catch (e: Exception) {
                Log.w("AuthRepository", "public /doctors query fallback: ${e.message}")
            }

            // Source 3: Query /users collection where role == DOCTOR
            try {
                val usersSnapshot = usersCollection
                    .whereEqualTo("role", UserRole.DOCTOR.name)
                    .limit(100)
                    .get()
                    .await()
                usersSnapshot.documents.mapNotNull { it.data?.let { m -> User.fromMap(m) } }.forEach { u ->
                    val existingIndex = candidateDoctors.indexOfFirst { it.id == u.id }
                    if (existingIndex >= 0) {
                        // Merge richer profile info
                        val existing = candidateDoctors[existingIndex]
                        candidateDoctors[existingIndex] = u.copy(
                            doctorCode = u.doctorCode ?: existing.doctorCode,
                            specialty = u.specialty ?: existing.specialty,
                            hospitalName = u.hospitalName ?: existing.hospitalName
                        )
                    } else {
                        candidateDoctors.add(u)
                    }
                }
            } catch (e: Exception) {
                Log.w("AuthRepository", "usersCollection query fallback: ${e.message}")
            }

            // If cleanQuery is blank, return all discovered registered doctors
            if (cleanQuery.isBlank()) {
                return Result.success(candidateDoctors)
            }

            // 4. Robust filter and score matching across all collected doctors
            val filteredDoctors = candidateDoctors.filter { doctor ->
                val docCode = (doctor.doctorCode ?: "").trim().uppercase()
                val docCodeStripped = docCode.replace(" ", "").replace("-", "").removePrefix("DR")

                val isCodeMatch = docCode == upperQuery ||
                        (normalizedCode.isNotBlank() && docCode == normalizedCode) ||
                        (strippedQuery.isNotBlank() && docCodeStripped == strippedQuery) ||
                        (strippedQuery.isNotBlank() && docCode.contains(strippedQuery))

                val isNameMatch = doctor.name.contains(cleanQuery, ignoreCase = true)
                val isSpecialtyMatch = doctor.specialty?.contains(cleanQuery, ignoreCase = true) == true
                val isHospitalMatch = doctor.hospitalName?.contains(cleanQuery, ignoreCase = true) == true

                isCodeMatch || isNameMatch || isSpecialtyMatch || isHospitalMatch
            }.sortedWith(compareByDescending<User> { doctor ->
                val docCode = (doctor.doctorCode ?: "").trim().uppercase()
                val docCodeStripped = docCode.replace(" ", "").replace("-", "").removePrefix("DR")
                when {
                    docCode == upperQuery || docCode == normalizedCode -> 3 // Exact code match
                    docCodeStripped == strippedQuery -> 2 // Suffix code match
                    doctor.name.equals(cleanQuery, ignoreCase = true) -> 2 // Exact name match
                    else -> 1
                }
            })

            // Fallback for demo doctor if query matches and network returned empty
            if (filteredDoctors.isEmpty()) {
                val demoDocCode = "DR-8821"
                if (demoDocCode == upperQuery || demoDocCode == normalizedCode || "8821" == strippedQuery ||
                    "Pranab".contains(cleanQuery, ignoreCase = true) || "Baruah".contains(cleanQuery, ignoreCase = true)) {
                    val demoDoctor = User(
                        id = "doctor_1",
                        name = "Dr. Pranab Baruah, MD",
                        role = UserRole.DOCTOR,
                        doctorCode = "DR-8821",
                        specialty = "Neurologist & Memory Specialist",
                        hospitalName = "GNRC Medical Institute, Guwahati",
                        profileCompleted = true
                    )
                    return Result.success(listOf(demoDoctor))
                }
            }

            Result.success(filteredDoctors)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error searching doctors: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Look up a single registered doctor by exact or normalized Doctor Code (e.g. "DR-8821", "8821")
     */
    suspend fun getDoctorByCode(code: String): Result<User?> {
        return try {
            val clean = code.trim()
            if (clean.isBlank()) return Result.success(null)

            val results = searchDoctors(clean).getOrNull()
            val matched = results?.firstOrNull()
            Result.success(matched)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error getting doctor by code: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Get current logged in user
     */
    fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser ?: return null
        return User(
            id = firebaseUser.uid,
            phoneNumber = firebaseUser.phoneNumber ?: "",
            role = UserRole.FAMILY, // Default to FAMILY, should be updated from Firestore later
            name = firebaseUser.displayName ?: "",
            email = firebaseUser.email,
            profilePhotoUrl = firebaseUser.photoUrl?.toString()
        )
    }

    /**
     * Check if user is logged in
     */
    fun isLoggedIn(): Boolean = auth.currentUser != null

    /**
     * Get current user ID
     */
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    /**
     * Get user from Firestore
     */
    suspend fun getUser(userId: String): Result<User?> {
        return try {
            val document = usersCollection.document(userId).get().await()
            if (document.exists()) {
                var user = User.fromMap(document.data ?: emptyMap())
                if (user.role == UserRole.DOCTOR) {
                    if (user.doctorCode.isNullOrBlank()) {
                        val newCode = generateDoctorCode()
                        user = user.copy(doctorCode = newCode, profileCompleted = true)
                        usersCollection.document(userId).update("doctorCode", newCode, "profileCompleted", true).await()
                    }
                    syncDoctorToPublicDirectory(user)
                }
                Result.success(user)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Save or update user in Firestore
     */
    suspend fun saveUser(user: User): Result<Unit> {
        Log.d("AuthRepository", "Saving user to Firestore /users/${user.id}")
        Log.d("AuthRepository", "  - profileCompleted: ${user.profileCompleted}")
        Log.d("AuthRepository", "  - linkedPatientIds: ${user.linkedPatientIds}")
        return try {
            usersCollection.document(user.id).set(user.toMap()).await()
            Log.d("AuthRepository", "✓ User saved successfully")
            if (user.role == UserRole.DOCTOR) {
                syncDoctorToPublicDirectory(user)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepository", "✗ User save FAILED: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Update user role
     */
    suspend fun updateUserRole(userId: String, role: UserRole): Result<Unit> {
        return try {
            usersCollection.document(userId).update("role", role.name).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Link patient to user
     */
    suspend fun linkPatientToUser(userId: String, patientId: String): Result<Unit> {
        return try {
            val user = getUser(userId).getOrNull()
            if (user != null) {
                val updatedList = (user.linkedPatientIds + patientId).distinct()
                usersCollection.document(userId).update("linkedPatientIds", updatedList).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mark a family/doctor user's profile as complete after onboarding,
     * and link their first patient. This is what makes "returning accounts
     * skip onboarding" work — without it the user doc stays profileCompleted=false.
     */
    suspend fun completeOnboarding(userId: String, patientId: String? = null): Result<Unit> {
        Log.d("AuthRepository", "=== COMPLETE ONBOARDING CALLED ===")
        Log.d("AuthRepository", "User ID: $userId")
        Log.d("AuthRepository", "Patient ID: $patientId")
        return try {
            val updates = mutableMapOf<String, Any>(
                "profileCompleted" to true,
                "updatedAt" to Date()
            )
            if (patientId != null) {
                val user = getUser(userId).getOrNull()
                val existing = user?.linkedPatientIds.orEmpty()
                updates["linkedPatientIds"] = (existing + patientId).distinct()
                Log.d("AuthRepository", "Updated linkedPatientIds: ${updates["linkedPatientIds"]}")
            }
            Log.d("AuthRepository", "Writing to Firestore /users/$userId with merge: $updates")
            usersCollection.document(userId).set(
                updates,
                com.google.firebase.firestore.SetOptions.merge()
            ).await()
            Log.d("AuthRepository", "✓ Onboarding completion saved successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepository", "✗ Onboarding completion FAILED: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Sign out. Clears the Firebase session AND the Google Sign-In client state so
     * the next sign-in shows the account chooser instead of silently reusing the
     * previously authorized account. Cloud account/data is never deleted.
     */
    fun signOut() {
        auth.signOut()
        try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            GoogleSignIn.getClient(appContext, gso).signOut()
        } catch (e: Exception) {
            Log.w("AuthRepository", "Google client sign-out skipped: ${e.message}")
        }
    }

    /**
     * Delete user account - removes user doc, owned patients, and Firebase Auth account.
     * Follows safe deletion policy: only deletes patients if user is sole owner.
     */
    suspend fun deleteAccount(userId: String): Result<Unit> {
        Log.d("AuthRepository", "=== DELETE ACCOUNT START ===")
        Log.d("AuthRepository", "User ID: $userId")
        
        return try {
            // Step 1: Get user data to find linked patients
            val user = getUser(userId).getOrNull()
            Log.d("AuthRepository", "User found: ${user != null}")
            
            if (user != null) {
                // Step 2: Delete linked patients (only if user is sole owner)
                for (patientId in user.linkedPatientIds) {
                    try {
                        Log.d("AuthRepository", "Checking patient $patientId ownership...")
                        val patientDoc = firestore.collection("patients").document(patientId).get().await()
                        val createdBy = patientDoc.getString("createdBy")
                        
                        if (createdBy == userId) {
                            Log.d("AuthRepository", "Deleting patient $patientId (owned by user)")
                            // Delete patient subcollections first
                            val subcollections = listOf(
                                "familyLinks", "gameResults", "reminders", 
                                "behavioralAlerts", "reminiscenceContent", 
                                "pendingActions", "clinicalNotes"
                            )
                            for (subcollection in subcollections) {
                                try {
                                    val docs = firestore.collection("patients")
                                        .document(patientId)
                                        .collection(subcollection)
                                        .get()
                                        .await()
                                    for (doc in docs.documents) {
                                        doc.reference.delete().await()
                                    }
                                    Log.d("AuthRepository", "✓ Deleted subcollection: $subcollection")
                                } catch (e: Exception) {
                                    Log.w("AuthRepository", "Subcollection $subcollection cleanup skipped: ${e.message}")
                                }
                            }
                            // Delete patient document
                            firestore.collection("patients").document(patientId).delete().await()
                            Log.d("AuthRepository", "✓ Patient deleted: $patientId")
                        } else {
                            Log.d("AuthRepository", "Patient $patientId not owned by user, skipping")
                        }
                    } catch (e: Exception) {
                        Log.w("AuthRepository", "Patient $patientId deletion skipped: ${e.message}")
                    }
                }
            }
            
            // Step 3: Delete user document from Firestore
            Log.d("AuthRepository", "Deleting user document...")
            usersCollection.document(userId).delete().await()
            Log.d("AuthRepository", "✓ User document deleted")
            
            // Step 4: Delete Firebase Auth account
            Log.d("AuthRepository", "Deleting Firebase Auth account...")
            try {
                auth.currentUser?.delete()?.await()
                Log.d("AuthRepository", "✓ Firebase Auth account deleted")
            } catch (e: Exception) {
                // Reauthentication may be required for sensitive operations
                Log.w("AuthRepository", "Auth deletion requires reauthentication: ${e.message}")
                // Still sign out even if auth deletion fails
                auth.signOut()
            }
            
            Log.d("AuthRepository", "=== DELETE ACCOUNT COMPLETE ===")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepository", "✗ DELETE ACCOUNT FAILED: ${e.message}", e)
            Result.failure(e)
        }
    }
}
