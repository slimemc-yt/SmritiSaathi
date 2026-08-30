package com.socklet.smritisaathi.domain.games;

import com.socklet.smritisaathi.domain.model.GamePerformance;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J$\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\nH\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/socklet/smritisaathi/domain/games/PerformanceTrackerImpl;", "Lcom/socklet/smritisaathi/domain/games/PerformanceTracker;", "repository", "Lcom/socklet/smritisaathi/domain/repository/PatientRepository;", "(Lcom/socklet/smritisaathi/domain/repository/PatientRepository;)V", "getAverageAccuracy", "", "gameType", "", "lastNSessions", "", "getCurrentLevel", "getHighestLevel", "getRecentPerformances", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/socklet/smritisaathi/domain/model/GamePerformance;", "limit", "recordPerformance", "", "performance", "app_debug"})
public final class PerformanceTrackerImpl implements com.socklet.smritisaathi.domain.games.PerformanceTracker {
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.repository.PatientRepository repository = null;
    
    @javax.inject.Inject
    public PerformanceTrackerImpl(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.repository.PatientRepository repository) {
        super();
    }
    
    @java.lang.Override
    public void recordPerformance(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.GamePerformance performance) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.GamePerformance>> getRecentPerformances(@org.jetbrains.annotations.NotNull
    java.lang.String gameType, int limit) {
        return null;
    }
    
    @java.lang.Override
    public int getCurrentLevel(@org.jetbrains.annotations.NotNull
    java.lang.String gameType) {
        return 0;
    }
    
    @java.lang.Override
    public int getHighestLevel(@org.jetbrains.annotations.NotNull
    java.lang.String gameType) {
        return 0;
    }
    
    @java.lang.Override
    public float getAverageAccuracy(@org.jetbrains.annotations.NotNull
    java.lang.String gameType, int lastNSessions) {
        return 0.0F;
    }
}