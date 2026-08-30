package com.socklet.smritisaathi.domain.model

import java.util.Date

enum class AlertSeverity(val displayName: String, val level: Int) {
    INFO("Informational", 1),
    LOW("Low", 2),
    MEDIUM("Medium", 3),
    HIGH("High", 4),
    URGENT("Urgent / SOS", 5)
}

enum class AlertType(val displayName: String) {
    SOS_TRIGGERED("SOS Emergency Button Pressed"),
    MEDICATION_HELP_REQUESTED("Help Requested on Medication"),
    MEDICATION_MISSED("Medication Not Taken"),
    SIGNIFICANT_SCORE_DROP("Sudden Score Drop Detected"),
    CONFUSION_DISTRESS_DETECTED("Repeated Errors / Distress Mode Triggered"),
    PROLONGED_INACTIVITY("No App Interaction Detected"),
    ROUTINE_DEVIATION("Routine Time Deviated")
}

data class BehavioralAlert(
    val id: String = "",
    val patientId: String = "",
    val patientName: String = "",
    val type: AlertType = AlertType.CONFUSION_DISTRESS_DETECTED,
    val severity: AlertSeverity = AlertSeverity.MEDIUM,
    val title: String = "",
    val description: String = "",
    val timestamp: Date = Date(),
    val acknowledged: Boolean = false,
    val acknowledgedBy: String? = null,
    val actionTaken: String? = null
)
