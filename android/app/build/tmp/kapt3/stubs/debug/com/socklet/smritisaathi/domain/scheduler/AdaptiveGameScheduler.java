package com.socklet.smritisaathi.domain.scheduler;

import com.socklet.smritisaathi.domain.model.GameResult;
import com.socklet.smritisaathi.domain.model.GameType;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u00a8\u0006\n"}, d2 = {"Lcom/socklet/smritisaathi/domain/scheduler/AdaptiveGameScheduler;", "", "()V", "decideNextGame", "Lcom/socklet/smritisaathi/domain/scheduler/NextGameRecommendation;", "recentResults", "", "Lcom/socklet/smritisaathi/domain/model/GameResult;", "priorityDomains", "", "app_debug"})
public final class AdaptiveGameScheduler {
    
    @javax.inject.Inject
    public AdaptiveGameScheduler() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.scheduler.NextGameRecommendation decideNextGame(@org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.GameResult> recentResults, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> priorityDomains) {
        return null;
    }
}