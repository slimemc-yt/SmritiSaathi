package com.socklet.smritisaathi.domain.model

import java.util.Date

enum class LifeStage(val displayName: String, val order: Int) {
    CHILDHOOD("Childhood & Early Days", 1),
    SCHOOL_COLLEGE("School & Youth", 2),
    CAREER("First Job & Career", 3),
    MARRIAGE("Wedding & Relationships", 4),
    FAMILY_CHILDREN("Family & Children", 5),
    PRESENT_DAY("Golden Years & Present", 6)
}

enum class ReminiscenceContentType {
    PHOTO,
    VOICE_NOTE,
    PHOTO_WITH_VOICE
}

data class ReminiscenceContent(
    val id: String = "",
    val patientId: String = "",
    val type: ReminiscenceContentType = ReminiscenceContentType.PHOTO,
    val lifeStage: LifeStage = LifeStage.FAMILY_CHILDREN,
    val title: String = "",
    val description: String = "",
    val photoUrl: String? = null,
    val audioUrl: String? = null,
    val personTag: String = "",
    val uploadedBy: String = "", // Uploader name / relation (e.g. "Grandson Aarav")
    val yearApprox: String? = null,
    val location: String? = null,
    val createdAt: Date = Date()
)
