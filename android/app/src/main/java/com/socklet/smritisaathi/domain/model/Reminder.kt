package com.socklet.smritisaathi.domain.model

import java.util.Date

enum class ReminderType(val displayName: String) {
    MEDICATION("Medication"),
    MEAL("Meal"),
    APPOINTMENT("Doctor Appointment"),
    ACTIVITY("Cognitive Activity"),
    HYDRATION("Water & Rest")
}

enum class ReminderStatus(val displayName: String) {
    PENDING("Pending"),
    TAKEN("Taken"),
    SKIPPED("Skipped"),
    SNOOZED("Remind Later"),
    HELP_REQUESTED("Help Requested")
}

data class Reminder(
    val id: String = "",
    val patientId: String = "",
    val type: ReminderType = ReminderType.MEDICATION,
    val title: String = "",
    val description: String = "",
    val scheduledTime: String = "", // HH:mm format
    val scheduledDate: Date = Date(),
    val status: ReminderStatus = ReminderStatus.PENDING,
    val respondedAt: Date? = null,
    val medicineName: String? = null,
    val dosage: String? = null,
    val voicePromptText: String = "",
    val isRecurring: Boolean = true
)
