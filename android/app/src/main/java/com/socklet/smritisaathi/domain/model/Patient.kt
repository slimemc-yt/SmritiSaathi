package com.socklet.smritisaathi.domain.model

import android.net.Uri
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

enum class DementiaStage(val tier: Int, val displayName: String) {
    MILD(1, "Mild"),
    MODERATE(2, "Moderate"),
    SEVERE(3, "Severe")
}

enum class Gender(val displayName: String) {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other")
}

data class FamilyContact(
    val id: String = "",
    val name: String = "",
    val relationship: String = "",
    val phoneNumber: String = "",
    val photoUrl: String? = null
)

data class Hospital(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val doctors: List<String> = emptyList()
)

data class Doctor(
    val id: String = "",
    val name: String = "",
    val specialization: String = "",
    val hospitalId: String = "",
    val hospitalName: String = "",
    val phone: String = "",
    val email: String = "",
    val doctorCode: String? = null
)

data class NapTime(
    val id: String = "",
    val startTime: String = "", // HH:mm format
    val endTime: String = ""
)

data class MealTime(
    val id: String = "",
    val type: String = "", // Breakfast, Lunch, Dinner, Snacks
    val time: String = "" // HH:mm format
)

data class MedicineTime(
    val id: String = "",
    val name: String = "",
    val dosage: String = "",
    val time: String = "", // HH:mm format
    val notes: String? = null
)

data class DailyRoutine(
    val wakeTime: String = "", // HH:mm format
    val sleepTime: String = "",
    val napTimes: List<NapTime> = emptyList(),
    val mealTimes: List<MealTime> = emptyList(),
    val medicineTimes: List<MedicineTime> = emptyList()
)

data class EmergencyContact(
    val name: String = "",
    val phoneNumber: String = "",
    val relationship: String = ""
)

data class MedicalReport(
    val id: String = "",
    val fileName: String = "",
    val fileUrl: String = "",
    val uploadedAt: Date = Date(),
    val type: String = "" // Lab report, Prescription, Scan, etc.
)

data class Patient(
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: Gender = Gender.MALE,
    val photoUrl: String? = null,
    val dementiaStage: DementiaStage = DementiaStage.MILD,
    val preferredLanguage: String = "as",
    val pairingCode: String = "",
    val familyInviteCode: String = "",
    val familyContacts: List<FamilyContact> = emptyList(),
    val assignedDoctorId: String? = null,
    val assignedDoctor: Doctor? = null,
    val assignedHospitalId: String? = null,
    val assignedHospital: Hospital? = null,
    val dailyRoutine: DailyRoutine = DailyRoutine(),
    val emergencyContact: EmergencyContact = EmergencyContact(),
    val sosNumber: String = "",
    val medicalReports: List<MedicalReport> = emptyList(),
    val enhancedSupportEnabled: Boolean = false,
    val createdBy: String = "", // Family member UID
    val pairedDeviceId: String? = null, // Paired patient device UID
    val pendingDoctorRequest: DoctorPatientRequest? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

// Onboarding state to track progress
data class PatientOnboardingState(
    val currentStep: Int = 0,
    val patient: Patient = Patient(),
    val isComplete: Boolean = false
) {
    companion object {
        const val TOTAL_STEPS = 8

        // Step indices
        const val STEP_BASIC_DETAILS = 0
        const val STEP_DEMENTIA_STAGE = 1
        const val STEP_MEDICAL_REPORTS = 2
        const val STEP_FAMILY_CONTACTS = 3
        const val STEP_DOCTOR_ASSIGNMENT = 4
        const val STEP_DAILY_ROUTINE = 5
        const val STEP_EMERGENCY_CONTACT = 6
        const val STEP_ENHANCED_SUPPORT = 7
    }
}
