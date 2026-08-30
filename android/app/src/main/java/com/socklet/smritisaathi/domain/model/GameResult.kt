package com.socklet.smritisaathi.domain.model

import java.util.Date

enum class GameType(val displayName: String, val cognitiveDomain: String) {
    FACE_RECOGNITION("Face Recognition", "Visual Memory & Social Recall"),
    CARD_MATCHING("Card Match", "Working Memory & Focus"),
    DAILY_ROUTINE("Daily Routine", "Executive Function & Sequencing"),
    NER_FAMILIAR_IMAGES("NER Heritage", "Long-term Memory & Cultural Grounding"),
    SHOPPING_BASKET("Shopping Basket", "Short-term Recall & Attention"),
    QUICK_RECALL("Quick Recall", "Verbal Comprehension & Retention"),
    LIFE_STAGE_MEMORY("Life Stages", "Episodic Reminiscence & Identity"),
    GUESS_WHOS_SPEAKING("Voice Recognition", "Auditory Memory"),
    MUSIC_MEMORY("Music Memory", "Emotional Recall & Pattern Recognition")
}

data class GameResult(
    val id: String = "",
    val patientId: String = "",
    val gameType: GameType = GameType.CARD_MATCHING,
    val score: Int = 0, // 0 to 100
    val maxScore: Int = 100,
    val difficultyLevel: Int = 1, // 1 (2 items), 2 (3 items), 3 (5 items)
    val responseTimeMs: Long = 0L,
    val mistakesCount: Int = 0,
    val completedSuccessfully: Boolean = true,
    val timestamp: Date = Date(),
    val notes: String? = null
)
