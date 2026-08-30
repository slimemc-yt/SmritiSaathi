package com.socklet.smritisaathi.domain.games

import com.socklet.smritisaathi.domain.model.GamePerformance
import com.socklet.smritisaathi.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSessionManagerImpl @Inject constructor() : GameSessionManager {
    private var _currentLevel = 1
    private var _currentRound = 0
    private var _totalRounds = 3
    private var _sessionScore = 0
    private var _roundScores = mutableListOf<Int>()
    private var _roundAccuracies = mutableListOf<Float>()
    
    override val currentLevel: Int get() = _currentLevel
    override val currentRound: Int get() = _currentRound
    override val totalRounds: Int get() = _totalRounds
    override val sessionScore: Int get() = _sessionScore
    override val isSessionComplete: Boolean get() = _currentRound >= _totalRounds
    
    override fun startNewSession(totalRounds: Int) {
        _totalRounds = totalRounds
        _currentRound = 0
        _sessionScore = 0
        _roundScores.clear()
        _roundAccuracies.clear()
    }
    
    override fun completeRound(score: Int, accuracy: Float, timeSpentSeconds: Int) {
        _currentRound++
        _sessionScore += score
        _roundScores.add(score)
        _roundAccuracies.add(accuracy)
    }
    
    override fun advanceToNextRound(): Boolean {
        return !isSessionComplete
    }
    
    override fun endSession(): GameSessionSummary {
        val avgScore = if (_roundScores.isEmpty()) 0 else _roundScores.average().toInt()
        val avgAccuracy = if (_roundAccuracies.isEmpty()) 0f else _roundAccuracies.average().toFloat()
        
        val trend = when {
            _roundAccuracies.size >= 2 && _roundAccuracies.last() > _roundAccuracies.first() + 0.1f -> "improving"
            _roundAccuracies.size >= 2 && _roundAccuracies.last() < _roundAccuracies.first() - 0.1f -> "declining"
            else -> "stable"
        }
        
        val encouragement = when {
            avgAccuracy >= 0.9f -> "Excellent work! Your memory is very sharp! 🌟"
            avgAccuracy >= 0.7f -> "Great job! You're doing wonderfully! 👏"
            avgAccuracy >= 0.5f -> "Good effort! Keep practicing! 💪"
            else -> "Thank you for playing! Every bit of practice helps! 🌸"
        }
        
        return GameSessionSummary(
            totalRounds = _totalRounds,
            completedRounds = _currentRound,
            averageScore = avgScore,
            averageAccuracy = avgAccuracy,
            totalTimeSeconds = 0, // Will be calculated by caller
            trend = trend,
            encouragement = encouragement
        )
    }
    
    override fun shouldIncreaseDifficulty(): Boolean {
        if (_roundAccuracies.size < 2) return false
        val recentAccuracy = _roundAccuracies.takeLast(2).average()
        return recentAccuracy >= 0.85f
    }
    
    override fun shouldDecreaseDifficulty(): Boolean {
        if (_roundAccuracies.size < 2) return false
        val recentAccuracy = _roundAccuracies.takeLast(2).average()
        return recentAccuracy < 0.5f
    }
}

@Singleton
class DifficultyControllerImpl @Inject constructor() : DifficultyController {
    private var _currentDifficulty = 1
    private var _consecutiveSuccess = 0
    
    override val currentDifficulty: Int get() = _currentDifficulty
    
    override fun updateDifficulty(accuracy: Float, mistakes: Int, consecutiveSuccess: Int) {
        _consecutiveSuccess = consecutiveSuccess
        
        when {
            accuracy >= 0.9f && consecutiveSuccess >= 2 -> {
                _currentDifficulty = (_currentDifficulty + 1).coerceAtMost(10)
            }
            accuracy < 0.5f || mistakes >= 5 -> {
                _currentDifficulty = (_currentDifficulty - 1).coerceAtLeast(1)
            }
        }
    }
    
    override fun getRecommendedDifficulty(): Int = _currentDifficulty
    
    override fun resetDifficulty() {
        _currentDifficulty = 1
        _consecutiveSuccess = 0
    }
}

@Singleton
class ContentSelectorImpl<T> @Inject constructor() : ContentSelector<T> {
    private val allContent = mutableListOf<T>()
    private val recentlyUsed = mutableListOf<T>()
    private val maxRecentHistory = 20
    
    fun initializeContent(content: List<T>) {
        allContent.clear()
        allContent.addAll(content)
    }
    
    override fun selectContent(count: Int, excludeRecent: Int): List<T> {
        val recentToExclude = recentlyUsed.takeLast(excludeRecent)
        val available = allContent.filter { it !in recentToExclude }
        
        return if (available.size >= count) {
            available.shuffled().take(count)
        } else {
            // If not enough non-recent content, allow some repetition
            allContent.shuffled().take(count)
        }
    }
    
    override fun markAsUsed(content: T) {
        recentlyUsed.add(content)
        if (recentlyUsed.size > maxRecentHistory) {
            recentlyUsed.removeAt(0)
        }
    }
    
    override fun getRecentlyUsed(): List<T> = recentlyUsed.toList()
    
    override fun reset() {
        recentlyUsed.clear()
    }
}

@Singleton
class HintSystemImpl @Inject constructor() : HintSystem {
    private var _hintsUsed = 0
    private val _maxHints = 3
    
    override val hintsUsed: Int get() = _hintsUsed
    override val maxHints: Int get() = _maxHints
    
    override fun shouldOfferHint(consecutiveMistakes: Int): Boolean {
        return consecutiveMistakes >= 3 && _hintsUsed < _maxHints
    }
    
    override fun useHint(): Hint {
        _hintsUsed++
        return Hint(
            type = HintType.ENCOURAGEMENT,
            message = "Here's a helpful hint! You're doing great! 💡"
        )
    }
    
    override fun resetHints() {
        _hintsUsed = 0
    }
}

@Singleton
class PerformanceTrackerImpl @Inject constructor(
    private val repository: PatientRepository
) : PerformanceTracker {
    
    override fun recordPerformance(performance: GamePerformance) {
        // Save to repository (fire and forget)
        kotlinx.coroutines.MainScope().launch {
            repository.saveGamePerformance(performance.patientId, performance)
        }
    }
    
    override fun getRecentPerformances(gameType: String, limit: Int): Flow<List<GamePerformance>> {
        // This would need to be implemented in repository
        // For now, return empty flow
        return kotlinx.coroutines.flow.flowOf(emptyList())
    }
    
    override fun getCurrentLevel(gameType: String): Int {
        // Would load from DataStore/Firestore
        return 1
    }
    
    override fun getHighestLevel(gameType: String): Int {
        // Would load from DataStore/Firestore
        return 1
    }
    
    override fun getAverageAccuracy(gameType: String, lastNSessions: Int): Float {
        // Would calculate from recent performances
        return 0.7f
    }
}
