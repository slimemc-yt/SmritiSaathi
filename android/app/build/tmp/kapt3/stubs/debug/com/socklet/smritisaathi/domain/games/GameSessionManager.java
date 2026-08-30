package com.socklet.smritisaathi.domain.games;

import com.socklet.smritisaathi.domain.model.GamePerformance;
import kotlinx.coroutines.flow.Flow;

/**
 * Reusable game session manager for tracking rounds, scores, and progression
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\b\u0010\u000f\u001a\u00020\tH&J \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0003H&J\b\u0010\u0016\u001a\u00020\u0017H&J\b\u0010\u0018\u001a\u00020\tH&J\b\u0010\u0019\u001a\u00020\tH&J\u0012\u0010\u001a\u001a\u00020\u00112\b\b\u0002\u0010\r\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0012\u0010\u000b\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u0005R\u0012\u0010\r\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u0005\u00a8\u0006\u001b"}, d2 = {"Lcom/socklet/smritisaathi/domain/games/GameSessionManager;", "", "currentLevel", "", "getCurrentLevel", "()I", "currentRound", "getCurrentRound", "isSessionComplete", "", "()Z", "sessionScore", "getSessionScore", "totalRounds", "getTotalRounds", "advanceToNextRound", "completeRound", "", "score", "accuracy", "", "timeSpentSeconds", "endSession", "Lcom/socklet/smritisaathi/domain/games/GameSessionSummary;", "shouldDecreaseDifficulty", "shouldIncreaseDifficulty", "startNewSession", "app_debug"})
public abstract interface GameSessionManager {
    
    public abstract int getCurrentLevel();
    
    public abstract int getCurrentRound();
    
    public abstract int getTotalRounds();
    
    public abstract int getSessionScore();
    
    public abstract boolean isSessionComplete();
    
    public abstract void startNewSession(int totalRounds);
    
    public abstract void completeRound(int score, float accuracy, int timeSpentSeconds);
    
    public abstract boolean advanceToNextRound();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.socklet.smritisaathi.domain.games.GameSessionSummary endSession();
    
    public abstract boolean shouldIncreaseDifficulty();
    
    public abstract boolean shouldDecreaseDifficulty();
    
    /**
     * Reusable game session manager for tracking rounds, scores, and progression
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}