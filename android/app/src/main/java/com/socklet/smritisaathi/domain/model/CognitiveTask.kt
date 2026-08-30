package com.socklet.smritisaathi.domain.model

import java.util.Date

enum class TaskType {
    COGNITIVE_GAME,
    MEDICATION_REMINDER,
    DAILY_CHECK_IN,
    REMINISCENCE_MOMENT,
    GRANDCHILD_INVITE,
    FAMILY_NUDGE
}

data class CognitiveTask(
    val id: String = "",
    val type: TaskType = TaskType.COGNITIVE_GAME,
    val title: String = "",
    val subtitle: String = "",
    val voiceAnnouncement: String = "",
    val gameType: GameType? = null,
    val difficultyLevel: Int = 1,
    val reminderId: String? = null,
    val scheduledTime: String = "", // e.g. "10:30 AM"
    val senderName: String? = null,
    val senderPhotoUrl: String? = null,
    val autoPlayAudioUrl: String? = null,
    val createdAt: Date = Date()
)
