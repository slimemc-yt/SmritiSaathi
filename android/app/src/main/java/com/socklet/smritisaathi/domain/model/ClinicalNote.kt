package com.socklet.smritisaathi.domain.model

import java.util.Date

data class ClinicalNote(
    val id: String = "",
    val doctorId: String = "",
    val doctorName: String = "",
    val patientId: String = "",
    val note: String = "",
    val priorityCognitiveDomains: List<String> = emptyList(), // e.g. "Visual Memory", "Auditory Recall"
    val guidanceForFamily: String = "", // Guidance pushed to family app
    val createdAt: Date = Date(),
    val isAcknowledgedByFamily: Boolean = false
)
