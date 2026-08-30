package com.socklet.smritisaathi.navigation

sealed class Screen(val route: String) {
    // Onboarding screens (shared)
    object Splash : Screen("splash")
    object LanguageSelection : Screen("language_selection")
    object RoleSelection : Screen("role_selection")
    object Login : Screen("login") {
        const val routeWithArgs = "login/{role}"
        fun createRoute(role: com.socklet.smritisaathi.domain.model.UserRole) = "login/${role.name}"
    }
    object OtpVerification : Screen("otp_verification")
    object PatientPairing : Screen("patient_pairing")

    // Patient flow
    object PatientHome : Screen("patient_home")
    object PatientGames : Screen("patient_games")
    object PatientReminders : Screen("patient_reminders")
    object PatientReminiscence : Screen("patient_reminiscence")
    object PatientSettings : Screen("patient_settings")

    // Family flow
    object FamilyHome : Screen("family_home")
    object FamilyPatientList : Screen("family_patient_list")
    object FamilyPatientDetail : Screen("family_patient_detail/{patientId}") {
        fun createRoute(patientId: String) = "family_patient_detail/$patientId"
    }
    object FamilyReminders : Screen("family_reminders")
    object FamilyAlerts : Screen("family_alerts")
    object FamilyReports : Screen("family_reports")
    object FamilySettings : Screen("family_settings")
    object FamilyPatientOnboarding : Screen("family_patient_onboarding")
    object FamilyAddPatient : Screen("family_add_patient")

    // Doctor flow
    object DoctorHome : Screen("doctor_home")
    object DoctorPatientList : Screen("doctor_patient_list")
    object DoctorPatientDetail : Screen("doctor_patient_detail/{patientId}") {
        fun createRoute(patientId: String) = "doctor_patient_detail/$patientId"
    }
    object DoctorClinicalNotes : Screen("doctor_clinical_notes")
    object DoctorReports : Screen("doctor_reports")
    object DoctorSettings : Screen("doctor_settings")
}
