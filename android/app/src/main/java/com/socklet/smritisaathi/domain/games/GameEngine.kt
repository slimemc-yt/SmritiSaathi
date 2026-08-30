package com.socklet.smritisaathi.domain.games

import com.socklet.smritisaathi.domain.model.GamePerformance
import kotlinx.coroutines.flow.Flow

/**
 * Reusable game session manager for tracking rounds, scores, and progression
 */
interface GameSessionManager {
    val currentLevel: Int
    val currentRound: Int
    val totalRounds: Int
    val sessionScore: Int
    val isSessionComplete: Boolean
    
    fun startNewSession(totalRounds: Int = 3)
    fun completeRound(score: Int, accuracy: Float, timeSpentSeconds: Int)
    fun advanceToNextRound(): Boolean
    fun endSession(): GameSessionSummary
    fun shouldIncreaseDifficulty(): Boolean
    fun shouldDecreaseDifficulty(): Boolean
}

data class GameSessionSummary(
    val totalRounds: Int,
    val completedRounds: Int,
    val averageScore: Int,
    val averageAccuracy: Float,
    val totalTimeSeconds: Int,
    val trend: String, // "improving", "stable", "declining"
    val encouragement: String
)

/**
 * Adaptive difficulty controller
 */
interface DifficultyController {
    val currentDifficulty: Int
    
    fun updateDifficulty(accuracy: Float, mistakes: Int, consecutiveSuccess: Int)
    fun getRecommendedDifficulty(): Int
    fun resetDifficulty()
}

/**
 * Content selector to avoid repetition
 */
interface ContentSelector<T> {
    fun selectContent(count: Int, excludeRecent: Int = 10): List<T>
    fun markAsUsed(content: T)
    fun getRecentlyUsed(): List<T>
    fun reset()
}

/**
 * Hint system for gentle assistance
 */
interface HintSystem {
    val hintsUsed: Int
    val maxHints: Int
    
    fun shouldOfferHint(consecutiveMistakes: Int): Boolean
    fun useHint(): Hint?
    fun resetHints()
}

data class Hint(
    val type: HintType,
    val message: String,
    val data: Map<String, Any> = emptyMap()
)

enum class HintType {
    HIGHLIGHT,
    REVEAL,
    SIMPLIFY,
    ENCOURAGEMENT
}

/**
 * Performance tracker for patient progress
 */
interface PerformanceTracker {
    fun recordPerformance(performance: GamePerformance)
    fun getRecentPerformances(gameType: String, limit: Int = 20): Flow<List<GamePerformance>>
    fun getCurrentLevel(gameType: String): Int
    fun getHighestLevel(gameType: String): Int
    fun getAverageAccuracy(gameType: String, lastNSessions: Int = 5): Float
}
