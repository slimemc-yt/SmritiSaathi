package com.socklet.smritisaathi.domain.model

import java.util.Date

enum class ActionType {
    NUDGE,             // Friendly voice reminder / greeting
    PLAY_INVITE,       // Grandchild/Family game invite ("Aarav wants to play with you!")
    FORCE_GAME,        // Force trigger an activity on patient screen
    TRIGGER_CALMING    // Trigger soothing family audio/photo remotely
}

data class PendingAction(
    val id: String = "",
    val patientId: String = "",
    val type: ActionType = ActionType.PLAY_INVITE,
    val senderName: String = "", // e.g. "Aarav (Grandson)"
    val senderPhotoUrl: String? = null,
    val message: String = "",
    val targetGame: GameType? = GameType.CARD_MATCHING,
    val audioUrl: String? = null,
    val timestamp: Date = Date(),
    val isProcessed: Boolean = false
)
