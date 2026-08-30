package com.socklet.smritisaathi.ui.family.onboarding

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socklet.smritisaathi.domain.model.*
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.data.seeder.DatabaseSeeder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class PendingMedicalReport(
    val id: String = java.util.UUID.randomUUID().toString(),
    val uri: Uri,
    val fileName: String,
    val type: String
)

data class PatientOnboardingUiState(
    val currentStep: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isComplete: Boolean = false,

    // Step 1: Basic Details
    val patientName: String = "",
    val patientAge: String = "",
    val patientGender: Gender = Gender.MALE,
    val patientPhotoUri: Uri? = null,
    val patientPhotoUrl: String? = null,

    // Step 2: Dementia Stage
    val dementiaStage: DementiaStage = DementiaStage.MILD,

    // Step 3: Medical Reports
    val medicalReports: List<MedicalReport> = emptyList(),
    val pendingMedicalReports: List<PendingMedicalReport> = emptyList(),
    val selectedReportType: String = "",

    // Step 4: Family Contacts
    val familyContacts: List<FamilyContact> = emptyList(),
    val editingContactIndex: Int = -1,
    val contactName: String = "",
    val contactRelationship: String = "",
    val contactPhone: String = "",
    val contactPhotoUri: Uri? = null,

    // Step 5: Doctor/Hospital
    val doctors: List<Doctor> = emptyList(),
    val hospitals: List<Hospital> = emptyList(),
    val registeredDoctors: List<User> = emptyList(),
    val isSearchingDoctors: Boolean = false,
    val selectedDoctor: Doctor? = null,
    val selectedHospital: Hospital? = null,
    val showAddHospitalDialog: Boolean = false,
    val showAddDoctorDialog: Boolean = false,
    val searchQuery: String = "",

    // Step 6: Daily Routine
    val wakeTime: String = "07:00",
    val sleepTime: String = "22:00",
    val napTimes: List<NapTime> = emptyList(),
    val mealTimes: List<MealTime> = emptyList(),
    val medicineTimes: List<MedicineTime> = emptyList(),
    val editingNapIndex: Int = -1,
    val editingMealIndex: Int = -1,
    val editingMedicineIndex: Int = -1,

    // Step 7: Emergency Contact
    val emergencyContactName: String = "Ramesh Baruah",
    val emergencyContactPhone: String = "+919876543210",
    val emergencyContactRelationship: String = "Son",
    val sosNumber: String = "112",

    // Step 8: Enhanced Support
    val enhancedSupportEnabled: Boolean = false,
    val screenPinningPermissionGranted: Boolean = false,

    // Created patient ID
    val createdPatientId: String? = null
) {
    val canProceed: Boolean
        get() = when (currentStep) {
            0 -> patientName.isNotBlank() && patientAge.isNotBlank() && patientAge.toIntOrNull()?.let { it > 0 } == true
            1 -> true
            2 -> true // Medical reports are optional
            3 -> true // Family contacts can proceed or use pre-filled
            4 -> true // Doctor & Hospital is optional (can assign now or assign later)
            5 -> true // Routine has default wake & sleep times
            6 -> true // Emergency contact has default SOS number
            7 -> true // Enhanced support is optional, shown only for severe
            else -> true
        }

    val showEnhancedSupportStep: Boolean
        get() = dementiaStage == DementiaStage.SEVERE && currentStep == 7

    val totalSteps: Int
        get() = if (dementiaStage == DementiaStage.SEVERE) 8 else 7
}

@HiltViewModel
class PatientOnboardingViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val authRepository: com.socklet.smritisaathi.domain.repository.AuthRepository,
    private val databaseSeeder: DatabaseSeeder
) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientOnboardingUiState())
    val uiState: StateFlow<PatientOnboardingUiState> = _uiState.asStateFlow()

    init {
        loadDoctorsAndHospitals()
    }

    private fun loadDoctorsAndHospitals() {
        viewModelScope.launch {
            try {
                // Seed initial data once (no-op if already seeded)
                databaseSeeder.seedInitialData()

                val doctorsResult = patientRepository.getDoctors()
                val hospitalsResult = patientRepository.getHospitals()

                _uiState.update { state ->
                    state.copy(
                        doctors = doctorsResult.getOrNull() ?: emptyList(),
                        hospitals = hospitalsResult.getOrNull() ?: emptyList()
                    )
                }

                // Also search registered doctors from Firestore
                searchRegisteredDoctors("")
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Connectivity issue: Could not load directory") }
            }
        }
    }

    fun searchRegisteredDoctors(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSearchingDoctors = true) }
            val result = authRepository.searchDoctors(query)
            if (result.isSuccess) {
                val registered = result.getOrDefault(emptyList())
                _uiState.update { state ->
                    state.copy(
                        registeredDoctors = registered,
                        isSearchingDoctors = false
                    )
                }
            } else {
                _uiState.update { it.copy(isSearchingDoctors = false) }
            }
        }
    }

    // Navigation
    fun nextStep() {
        _uiState.update { state ->
            val nextStep = state.currentStep + 1
            // Skip enhanced support step if not severe
            val adjustedStep = if (nextStep == 7 && state.dementiaStage != DementiaStage.SEVERE) {
                8 // Complete
            } else if (nextStep >= state.totalSteps) {
                8 // Complete
            } else {
                nextStep
            }
            state.copy(currentStep = adjustedStep)
        }
    }

    fun previousStep() {
        _uiState.update { state ->
            val prevStep = (state.currentStep - 1).coerceAtLeast(0)
            state.copy(currentStep = prevStep)
        }
    }

    // Step 1: Basic Details
    fun setPatientName(name: String) {
        _uiState.update { it.copy(patientName = name) }
    }

    fun setPatientAge(age: String) {
        if (age.isEmpty() || age.all { it.isDigit() }) {
            _uiState.update { it.copy(patientAge = age) }
        }
    }

    fun setPatientGender(gender: Gender) {
        _uiState.update { it.copy(patientGender = gender) }
    }

    fun setPatientPhoto(uri: Uri?) {
        _uiState.update { it.copy(patientPhotoUri = uri) }
    }

    // Step 2: Dementia Stage
    fun setDementiaStage(stage: DementiaStage) {
        _uiState.update { it.copy(dementiaStage = stage) }
    }

    // Step 3: Medical Reports
    fun addMedicalReport(report: MedicalReport) {
        _uiState.update { it.copy(medicalReports = it.medicalReports + report) }
    }

    fun removeMedicalReport(reportId: String) {
        _uiState.update { it.copy(medicalReports = it.medicalReports.filter { r -> r.id != reportId }) }
    }

    fun addPendingMedicalReport(uri: Uri, fileName: String, reportType: String) {
        val pendingReport = PendingMedicalReport(
            uri = uri,
            fileName = fileName,
            type = reportType
        )
        _uiState.update {
            it.copy(
                pendingMedicalReports = it.pendingMedicalReports + pendingReport
            )
        }
        android.util.Log.d("PatientOnboarding", "Added pending medical report: $fileName (type: $reportType)")
    }

    fun removePendingMedicalReport(reportId: String) {
        _uiState.update {
            it.copy(
                pendingMedicalReports = it.pendingMedicalReports.filter { r -> r.id != reportId }
            )
        }
    }

    suspend fun uploadMedicalReport(uri: Uri, fileName: String, fileType: String): Result<MedicalReport> {
        val patientId = _uiState.value.createdPatientId ?: return Result.failure(Exception("Patient not created yet"))
        return patientRepository.uploadMedicalReport(patientId, uri, fileName, fileType)
    }

    // Step 4: Family Contacts
    fun addFamilyContact() {
        val state = _uiState.value
        val newContact = FamilyContact(
            id = java.util.UUID.randomUUID().toString(),
            name = state.contactName,
            relationship = state.contactRelationship,
            phoneNumber = state.contactPhone
        )
        _uiState.update {
            it.copy(
                familyContacts = it.familyContacts + newContact,
                contactName = "",
                contactRelationship = "",
                contactPhone = "",
                contactPhotoUri = null
            )
        }
    }

    fun updateFamilyContact(index: Int) {
        val state = _uiState.value
        val updatedContact = state.familyContacts[index].copy(
            name = state.contactName,
            relationship = state.contactRelationship,
            phoneNumber = state.contactPhone
        )
        _uiState.update {
            it.copy(
                familyContacts = it.familyContacts.toMutableList().apply { this[index] = updatedContact },
                editingContactIndex = -1,
                contactName = "",
                contactRelationship = "",
                contactPhone = ""
            )
        }
    }

    fun removeFamilyContact(index: Int) {
        _uiState.update { it.copy(familyContacts = it.familyContacts.toMutableList().apply { removeAt(index) }) }
    }

    fun setEditingContact(index: Int) {
        val contact = _uiState.value.familyContacts.getOrNull(index)
        _uiState.update {
            it.copy(
                editingContactIndex = index,
                contactName = contact?.name ?: "",
                contactRelationship = contact?.relationship ?: "",
                contactPhone = contact?.phoneNumber ?: ""
            )
        }
    }

    fun clearEditingContact() {
        _uiState.update {
            it.copy(
                editingContactIndex = -1,
                contactName = "",
                contactRelationship = "",
                contactPhone = ""
            )
        }
    }

    fun setContactName(name: String) { _uiState.update { it.copy(contactName = name) } }
    fun setContactRelationship(relationship: String) { _uiState.update { it.copy(contactRelationship = relationship) } }
    fun setContactPhone(phone: String) { _uiState.update { it.copy(contactPhone = phone) } }
    fun setContactPhoto(uri: Uri?) { _uiState.update { it.copy(contactPhotoUri = uri) } }

    // Step 5: Doctor/Hospital
    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectDoctor(doctor: Doctor?) {
        _uiState.update { state ->
            state.copy(
                selectedDoctor = doctor,
                selectedHospital = if (doctor != null) {
                    state.hospitals.find { it.id == doctor.hospitalId } ?: state.selectedHospital
                } else state.selectedHospital
            )
        }
    }

    fun selectRegisteredDoctor(doctorUser: User?) {
        if (doctorUser == null) {
            _uiState.update { it.copy(selectedDoctor = null) }
            return
        }
        val doc = Doctor(
            id = doctorUser.id,
            name = doctorUser.name,
            specialization = doctorUser.specialty ?: "Neurologist / Dementia Care",
            hospitalId = "",
            hospitalName = doctorUser.hospitalName ?: "Regional Medical Center",
            doctorCode = doctorUser.doctorCode
        )
        selectDoctor(doc)
    }

    fun selectHospital(hospital: Hospital?) {
        _uiState.update { it.copy(selectedHospital = hospital) }
    }

    fun setShowAddHospitalDialog(show: Boolean) {
        _uiState.update { it.copy(showAddHospitalDialog = show) }
    }

    fun setShowAddDoctorDialog(show: Boolean) {
        _uiState.update { it.copy(showAddDoctorDialog = show) }
    }

    suspend fun addCustomHospital(hospital: Hospital): Result<Hospital> {
        val result = patientRepository.addHospital(hospital)
        if (result.isSuccess) {
            loadDoctorsAndHospitals()
        }
        return result
    }

    suspend fun addCustomDoctor(doctor: Doctor): Result<Doctor> {
        val result = patientRepository.addDoctor(doctor)
        if (result.isSuccess) {
            loadDoctorsAndHospitals()
        }
        return result
    }

    // Step 6: Daily Routine
    fun setWakeTime(time: String) { _uiState.update { it.copy(wakeTime = time) } }
    fun setSleepTime(time: String) { _uiState.update { it.copy(sleepTime = time) } }

    fun addNapTime(nap: NapTime) {
        _uiState.update { it.copy(napTimes = it.napTimes + nap) }
    }

    fun removeNapTime(index: Int) {
        _uiState.update { it.copy(napTimes = it.napTimes.toMutableList().apply { removeAt(index) }) }
    }

    fun addMealTime(meal: MealTime) {
        _uiState.update { it.copy(mealTimes = it.mealTimes + meal) }
    }

    fun removeMealTime(index: Int) {
        _uiState.update { it.copy(mealTimes = it.mealTimes.toMutableList().apply { removeAt(index) }) }
    }

    fun addMedicineTime(medicine: MedicineTime) {
        _uiState.update { it.copy(medicineTimes = it.medicineTimes + medicine) }
    }

    fun removeMedicineTime(index: Int) {
        _uiState.update { it.copy(medicineTimes = it.medicineTimes.toMutableList().apply { removeAt(index) }) }
    }

    // Step 7: Emergency Contact
    fun setEmergencyContactName(name: String) { _uiState.update { it.copy(emergencyContactName = name) } }
    fun setEmergencyContactPhone(phone: String) { _uiState.update { it.copy(emergencyContactPhone = phone) } }
    fun setEmergencyContactRelationship(relationship: String) { _uiState.update { it.copy(emergencyContactRelationship = relationship) } }
    fun setSosNumber(number: String) { _uiState.update { it.copy(sosNumber = number) } }

    // Step 8: Enhanced Support
    fun setEnhancedSupportEnabled(enabled: Boolean) {
        _uiState.update { it.copy(enhancedSupportEnabled = enabled) }
    }

    fun setScreenPinningPermissionGranted(granted: Boolean) {
        _uiState.update { it.copy(screenPinningPermissionGranted = granted) }
    }

    // Save patient to Firestore
    fun savePatient(familyMemberId: String, onComplete: (String) -> Unit) {
        android.util.Log.d("PatientOnboarding", "=== SAVE PATIENT CALLED ===")
        android.util.Log.d("PatientOnboarding", "Family Member ID: $familyMemberId")
        android.util.Log.d("PatientOnboarding", "Patient Name: ${_uiState.value.patientName}")
        android.util.Log.d("PatientOnboarding", "Patient Age: ${_uiState.value.patientAge}")
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val state = _uiState.value
            val finalContacts = if (state.familyContacts.isNotEmpty()) state.familyContacts else listOf(
                FamilyContact(id = "1", name = "Aarav Baruah", relationship = "Grandson", phoneNumber = "+919876543210"),
                FamilyContact(id = "2", name = "Sunita Devi", relationship = "Daughter", phoneNumber = "+919876543211"),
                FamilyContact(id = "3", name = "Rajesh Baruah", relationship = "Son", phoneNumber = "+919876543212")
            )

            val patient = Patient(
                name = state.patientName.ifBlank { "Hemlata Baruah" },
                age = state.patientAge.toIntOrNull() ?: 72,
                gender = state.patientGender,
                photoUrl = state.patientPhotoUrl,
                dementiaStage = state.dementiaStage,
                familyContacts = finalContacts,
                assignedDoctorId = null, // Pending doctor acceptance
                assignedDoctor = null,
                assignedHospitalId = state.selectedHospital?.id ?: "hosp_1",
                assignedHospital = state.selectedHospital,
                dailyRoutine = DailyRoutine(
                    wakeTime = state.wakeTime,
                    sleepTime = state.sleepTime,
                    napTimes = state.napTimes,
                    mealTimes = state.mealTimes,
                    medicineTimes = state.medicineTimes
                ),
                emergencyContact = EmergencyContact(
                    name = state.emergencyContactName.ifBlank { "Ramesh Baruah" },
                    phoneNumber = state.emergencyContactPhone.ifBlank { "+919876543210" },
                    relationship = state.emergencyContactRelationship.ifBlank { "Son" }
                ),
                sosNumber = state.sosNumber.ifBlank { "112" },
                medicalReports = state.medicalReports,
                enhancedSupportEnabled = state.enhancedSupportEnabled,
                createdBy = familyMemberId
            )

            android.util.Log.d("PatientOnboarding", "Creating patient in Firestore...")
            val result = patientRepository.createPatient(patient)

            if (result.isSuccess) {
                val createdPatient = result.getOrThrow()
                android.util.Log.d("PatientOnboarding", "✓ Patient created with ID: ${createdPatient.id}")

                // If doctor was selected during registration, dispatch pending connection request
                val selDoc = state.selectedDoctor
                if (selDoc != null) {
                    val doctorRequest = DoctorPatientRequest(
                        patientId = createdPatient.id,
                        patientName = createdPatient.name,
                        patientAge = createdPatient.age,
                        dementiaStage = createdPatient.dementiaStage,
                        familyId = familyMemberId,
                        familyName = state.emergencyContactName.ifBlank { "Family Caregiver" },
                        familyRelationship = state.emergencyContactRelationship.ifBlank { "Caregiver" },
                        doctorId = selDoc.id,
                        doctorName = selDoc.name,
                        doctorCode = selDoc.doctorCode ?: "",
                        hospitalName = selDoc.hospitalName,
                        status = DoctorRequestStatus.PENDING,
                        requestedAt = Date()
                    )
                    try {
                        patientRepository.sendDoctorRequest(doctorRequest)
                        android.util.Log.d("PatientOnboarding", "✓ Dispatched pending request to doctor ${selDoc.name}")
                    } catch (e: Exception) {
                        android.util.Log.w("PatientOnboarding", "Doctor request dispatch skipped: ${e.message}")
                    }
                }

                // Upload photo if selected (fails gracefully if Storage not configured)
                try {
                    state.patientPhotoUri?.let { uri ->
                        patientRepository.uploadProfilePhoto(createdPatient.id, uri)
                    }
                } catch (e: Exception) {
                    android.util.Log.w("PatientOnboarding", "Photo upload skipped: ${e.message}")
                }

                // Save medical report metadata (no actual file upload - hackathon free tier optimization)
                val reportMetadata = state.pendingMedicalReports.map { pending ->
                    MedicalReport(
                        id = java.util.UUID.randomUUID().toString(),
                        fileName = pending.fileName,
                        fileUrl = "", // No actual file uploaded - metadata only
                        uploadedAt = java.util.Date(),
                        type = pending.type
                    )
                }

                if (reportMetadata.isNotEmpty()) {
                    try {
                        patientRepository.updatePatientReports(createdPatient.id, reportMetadata)
                        android.util.Log.d("PatientOnboarding", "✓ Saved ${reportMetadata.size} report metadata (files not uploaded - free tier)")
                    } catch (e: Exception) {
                        android.util.Log.e("PatientOnboarding", "✗ Failed to save report metadata: ${e.message}")
                    }
                }

                android.util.Log.d("PatientOnboarding", "Calling completeOnboarding for user $familyMemberId with patient ${createdPatient.id}")
                // CRITICAL PERSISTENCE FIX: mark the creating family user's
                // profile as complete and link this patient, so that when they
                // sign out and sign back in, they skip onboarding and land on
                // their dashboard with this patient loaded.
                val onboardingResult = authRepository.completeOnboarding(
                    userId = familyMemberId,
                    patientId = createdPatient.id
                )
                android.util.Log.d("PatientOnboarding", "completeOnboarding result: ${if (onboardingResult.isSuccess) "SUCCESS" else "FAILED: ${onboardingResult.exceptionOrNull()?.message}"}")
                
                val linkResult = authRepository.linkPatientToUser(familyMemberId, createdPatient.id)
                android.util.Log.d("PatientOnboarding", "linkPatientToUser result: ${if (linkResult.isSuccess) "SUCCESS" else "FAILED: ${linkResult.exceptionOrNull()?.message}"}")

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isComplete = true,
                        createdPatientId = createdPatient.id
                    )
                }
                android.util.Log.d("PatientOnboarding", "Calling onComplete callback with patient ID: ${createdPatient.id}")
                onComplete(createdPatient.id)
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Failed to save patient"
                android.util.Log.e("PatientOnboarding", "✗ Patient creation FAILED: $errorMsg")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = errorMsg
                    )
                }
            }
        }
    }
}
