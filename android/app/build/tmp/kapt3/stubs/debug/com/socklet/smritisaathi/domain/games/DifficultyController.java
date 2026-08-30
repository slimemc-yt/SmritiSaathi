package com.socklet.smritisaathi.domain.games;

import com.socklet.smritisaathi.domain.model.GamePerformance;
import kotlinx.coroutines.flow.Flow;

/**
 * Adaptive difficulty controller
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\bH&J \u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u000e"}, d2 = {"Lcom/socklet/smritisaathi/domain/games/DifficultyController;", "", "currentDifficulty", "", "getCurrentDifficulty", "()I", "getRecommendedDifficulty", "resetDifficulty", "", "updateDifficulty", "accuracy", "", "mistakes", "consecutiveSuccess", "app_debug"})
public abstract interface DifficultyController {
    
    public abstract int getCurrentDifficulty();
    
    public abstract void updateDifficulty(float accuracy, int mistakes, int consecutiveSuccess);
    
    public abstract int getRecommendedDifficulty();
    
    public abstract void resetDifficulty();
}