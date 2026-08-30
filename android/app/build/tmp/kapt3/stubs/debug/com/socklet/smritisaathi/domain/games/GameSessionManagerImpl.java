package com.socklet.smritisaathi.domain.games;

import com.socklet.smritisaathi.domain.model.GamePerformance;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0012H\u0016J \u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u0004H\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0012H\u0016J\b\u0010!\u001a\u00020\u0012H\u0016J\u0010\u0010\"\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u000eR\u0014\u0010\u0016\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u000e\u00a8\u0006#"}, d2 = {"Lcom/socklet/smritisaathi/domain/games/GameSessionManagerImpl;", "Lcom/socklet/smritisaathi/domain/games/GameSessionManager;", "()V", "_currentLevel", "", "_currentRound", "_roundAccuracies", "", "", "_roundScores", "_sessionScore", "_totalRounds", "currentLevel", "getCurrentLevel", "()I", "currentRound", "getCurrentRound", "isSessionComplete", "", "()Z", "sessionScore", "getSessionScore", "totalRounds", "getTotalRounds", "advanceToNextRound", "completeRound", "", "score", "accuracy", "timeSpentSeconds", "endSession", "Lcom/socklet/smritisaathi/domain/games/GameSessionSummary;", "shouldDecreaseDifficulty", "shouldIncreaseDifficulty", "startNewSession", "app_debug"})
public final class GameSessionManagerImpl implements com.socklet.smritisaathi.domain.games.GameSessionManager {
    private int _currentLevel = 1;
    private int _currentRound = 0;
    private int _totalRounds = 3;
    private int _sessionScore = 0;
    @org.jetbrains.annotations.NotNull
    private java.util.List<java.lang.Integer> _roundScores;
    @org.jetbrains.annotations.NotNull
    private java.util.List<java.lang.Float> _roundAccuracies;
    
    @javax.inject.Inject
    public GameSessionManagerImpl() {
        super();
    }
    
    @java.lang.Override
    public int getCurrentLevel() {
        return 0;
    }
    
    @java.lang.Override
    public int getCurrentRound() {
        return 0;
    }
    
    @java.lang.Override
    public int getTotalRounds() {
        return 0;
    }
    
    @java.lang.Override
    public int getSessionScore() {
        return 0;
    }
    
    @java.lang.Override
    public boolean isSessionComplete() {
        return false;
    }
    
    @java.lang.Override
    public void startNewSession(int totalRounds) {
    }
    
    @java.lang.Override
    public void completeRound(int score, float accuracy, int timeSpentSeconds) {
    }
    
    @java.lang.Override
    public boolean advanceToNextRound() {
        return false;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.socklet.smritisaathi.domain.games.GameSessionSummary endSession() {
        return null;
    }
    
    @java.lang.Override
    public boolean shouldIncreaseDifficulty() {
        return false;
    }
    
    @java.lang.Override
    public boolean shouldDecreaseDifficulty() {
        return false;
    }
}