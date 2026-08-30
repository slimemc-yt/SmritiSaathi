package com.socklet.smritisaathi.domain.model

import java.util.Date

data class GamePerformance(
    val id: String = "",
    val patientId: String = "",
    val gameType: String = "",
    val score: Int = 0,
    val maxScore: Int = 100,
    val timeSpentSeconds: Int = 0,
    val difficultyLevel: Int = 1,
    val accuracy: Float = 0f, // 0.0 to 1.0
    val mistakes: Int = 0,
    val completedAt: Date = Date(),
    val metadata: Map<String, Any> = emptyMap() // Game-specific data
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "patientId" to patientId,
            "gameType" to gameType,
            "score" to score,
            "maxScore" to maxScore,
            "timeSpentSeconds" to timeSpentSeconds,
            "difficultyLevel" to difficultyLevel,
            "accuracy" to accuracy,
            "mistakes" to mistakes,
            "completedAt" to completedAt,
            "metadata" to metadata
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): GamePerformance {
            @Suppress("UNCHECKED_CAST")
            return GamePerformance(
                id = map["id"] as? String ?: "",
                patientId = map["patientId"] as? String ?: "",
                gameType = map["gameType"] as? String ?: "",
                score = (map["score"] as? Long)?.toInt() ?: 0,
                maxScore = (map["maxScore"] as? Long)?.toInt() ?: 100,
                timeSpentSeconds = (map["timeSpentSeconds"] as? Long)?.toInt() ?: 0,
                difficultyLevel = (map["difficultyLevel"] as? Long)?.toInt() ?: 1,
                accuracy = (map["accuracy"] as? Double)?.toFloat() ?: 0f,
                mistakes = (map["mistakes"] as? Long)?.toInt() ?: 0,
                completedAt = map["completedAt"] as? Date ?: Date(),
                metadata = map["metadata"] as? Map<String, Any> ?: emptyMap()
            )
        }
    }
}

data class CognitiveAssessment(
    val patientId: String = "",
    val overallScore: Int = 0, // 0-100
    val memoryScore: Int = 0,
    val attentionScore: Int = 0,
    val problemSolvingScore: Int = 0,
    val reactionTimeScore: Int = 0,
    val trend: String = "stable", // "improving", "stable", "declining"
    val recommendations: List<String> = emptyList(),
    val assessedAt: Date = Date(),
    val period: String = "daily" // "daily", "weekly", "monthly"
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "patientId" to patientId,
            "overallScore" to overallScore,
            "memoryScore" to memoryScore,
            "attentionScore" to attentionScore,
            "problemSolvingScore" to problemSolvingScore,
            "reactionTimeScore" to reactionTimeScore,
            "trend" to trend,
            "recommendations" to recommendations,
            "assessedAt" to assessedAt,
            "period" to period
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any?>): CognitiveAssessment {
            @Suppress("UNCHECKED_CAST")
            return CognitiveAssessment(
                patientId = map["patientId"] as? String ?: "",
                overallScore = (map["overallScore"] as? Long)?.toInt() ?: 0,
                memoryScore = (map["memoryScore"] as? Long)?.toInt() ?: 0,
                attentionScore = (map["attentionScore"] as? Long)?.toInt() ?: 0,
                problemSolvingScore = (map["problemSolvingScore"] as? Long)?.toInt() ?: 0,
                reactionTimeScore = (map["reactionTimeScore"] as? Long)?.toInt() ?: 0,
                trend = map["trend"] as? String ?: "stable",
                recommendations = map["recommendations"] as? List<String> ?: emptyList(),
                assessedAt = map["assessedAt"] as? Date ?: Date(),
                period = map["period"] as? String ?: "daily"
            )
        }
    }
}
