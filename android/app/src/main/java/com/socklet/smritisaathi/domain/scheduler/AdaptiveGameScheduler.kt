package com.socklet.smritisaathi.domain.scheduler

import com.socklet.smritisaathi.domain.model.GameResult
import com.socklet.smritisaathi.domain.model.GameType
import javax.inject.Inject
import javax.inject.Singleton

data class NextGameRecommendation(
    val gameType: GameType,
    val difficultyLevel: Int,
    val reasoning: String
)

@Singleton
class AdaptiveGameScheduler @Inject constructor() {

    fun decideNextGame(
        recentResults: List<GameResult>,
        priorityDomains: List<String> = emptyList()
    ): NextGameRecommendation {
        if (recentResults.isEmpty()) {
            return NextGameRecommendation(
                gameType = GameType.CARD_MATCHING,
                difficultyLevel = 1,
                reasoning = "Initial baseline assessment: simple 2-pair card matching to establish engagement."
            )
        }

        // Calculate average score per game type
        val grouped = recentResults.groupBy { it.gameType }
        val allGames = GameType.values().toList()

        // Check if any game hasn't been played yet in the last 7 sessions
        val unplayedGames = allGames.filter { it !in grouped.keys }
        if (unplayedGames.isNotEmpty()) {
            val nextGame = unplayedGames.first()
            return NextGameRecommendation(
                gameType = nextGame,
                difficultyLevel = 1,
                reasoning = "Rotating cognitive domains: introducing ${nextGame.displayName} (${nextGame.cognitiveDomain}) for well-rounded stimulation."
            )
        }

        // Find the game type with the lowest recent performance (needs gentle reinforcement)
        // or prioritize doctor-assigned cognitive domains
        var targetGame = allGames.minByOrNull { game ->
            val results = grouped[game] ?: emptyList()
            if (results.isEmpty()) 0.0 else results.map { it.score }.average()
        } ?: GameType.CARD_MATCHING

        // Check if doctor specified priority domains
        if (priorityDomains.isNotEmpty()) {
            val matchingDoctorDomain = allGames.find { game ->
                priorityDomains.any { domain -> game.cognitiveDomain.contains(domain, ignoreCase = true) }
            }
            if (matchingDoctorDomain != null) {
                targetGame = matchingDoctorDomain
            }
        }

        val gameHistory = grouped[targetGame] ?: emptyList()
        val recentAvg = if (gameHistory.isNotEmpty()) gameHistory.take(3).map { it.score }.average() else 75.0
        val lastDifficulty = gameHistory.firstOrNull()?.difficultyLevel ?: 1

        val (nextDifficulty, reasoning) = when {
            recentAvg >= 85.0 && lastDifficulty < 3 -> {
                Pair(
                    lastDifficulty + 1,
                    "High accuracy (${recentAvg.toInt()}%) observed in ${targetGame.displayName}. Stepping up difficulty to Level ${lastDifficulty + 1}."
                )
            }
            recentAvg < 50.0 && lastDifficulty > 1 -> {
                Pair(
                    lastDifficulty - 1,
                    "Patient showed fatigue or mild confusion in ${targetGame.displayName}. Gently reducing difficulty to Level ${lastDifficulty - 1}."
                )
            }
            else -> {
                Pair(
                    lastDifficulty,
                    "Optimal challenge balance in ${targetGame.displayName}. Maintaining Level $lastDifficulty."
                )
            }
        }

        return NextGameRecommendation(
            gameType = targetGame,
            difficultyLevel = nextDifficulty,
            reasoning = reasoning
        )
    }
}
