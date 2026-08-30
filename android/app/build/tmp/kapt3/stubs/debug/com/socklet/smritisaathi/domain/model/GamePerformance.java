package com.socklet.smritisaathi.domain.model;

import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\b\u0018\u0000 52\u00020\u0001:\u00015B\u007f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0011\u00a2\u0006\u0002\u0010\u0012J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u000fH\u00c6\u0003J\u0015\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0011H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0007H\u00c6\u0003J\t\u0010)\u001a\u00020\u0007H\u00c6\u0003J\t\u0010*\u001a\u00020\u0007H\u00c6\u0003J\t\u0010+\u001a\u00020\u0007H\u00c6\u0003J\t\u0010,\u001a\u00020\fH\u00c6\u0003J\t\u0010-\u001a\u00020\u0007H\u00c6\u0003J\u0083\u0001\u0010.\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0011H\u00c6\u0001J\u0013\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00102\u001a\u00020\u0007H\u00d6\u0001J\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0011J\t\u00104\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u001d\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\r\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0018R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018\u00a8\u00066"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/GamePerformance;", "", "id", "", "patientId", "gameType", "score", "", "maxScore", "timeSpentSeconds", "difficultyLevel", "accuracy", "", "mistakes", "completedAt", "Ljava/util/Date;", "metadata", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIIFILjava/util/Date;Ljava/util/Map;)V", "getAccuracy", "()F", "getCompletedAt", "()Ljava/util/Date;", "getDifficultyLevel", "()I", "getGameType", "()Ljava/lang/String;", "getId", "getMaxScore", "getMetadata", "()Ljava/util/Map;", "getMistakes", "getPatientId", "getScore", "getTimeSpentSeconds", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toMap", "toString", "Companion", "app_debug"})
public final class GamePerformance {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String gameType = null;
    private final int score = 0;
    private final int maxScore = 0;
    private final int timeSpentSeconds = 0;
    private final int difficultyLevel = 0;
    private final float accuracy = 0.0F;
    private final int mistakes = 0;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date completedAt = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Map<java.lang.String, java.lang.Object> metadata = null;
    @org.jetbrains.annotations.NotNull
    public static final com.socklet.smritisaathi.domain.model.GamePerformance.Companion Companion = null;
    
    public GamePerformance(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    java.lang.String gameType, int score, int maxScore, int timeSpentSeconds, int difficultyLevel, float accuracy, int mistakes, @org.jetbrains.annotations.NotNull
    java.util.Date completedAt, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends java.lang.Object> metadata) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPatientId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGameType() {
        return null;
    }
    
    public final int getScore() {
        return 0;
    }
    
    public final int getMaxScore() {
        return 0;
    }
    
    public final int getTimeSpentSeconds() {
        return 0;
    }
    
    public final int getDifficultyLevel() {
        return 0;
    }
    
    public final float getAccuracy() {
        return 0.0F;
    }
    
    public final int getMistakes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getCompletedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Object> getMetadata() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Object> toMap() {
        return null;
    }
    
    public GamePerformance() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Object> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.GamePerformance copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    java.lang.String gameType, int score, int maxScore, int timeSpentSeconds, int difficultyLevel, float accuracy, int mistakes, @org.jetbrains.annotations.NotNull
    java.util.Date completedAt, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, ? extends java.lang.Object> metadata) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006\u00a8\u0006\b"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/GamePerformance$Companion;", "", "()V", "fromMap", "Lcom/socklet/smritisaathi/domain/model/GamePerformance;", "map", "", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.socklet.smritisaathi.domain.model.GamePerformance fromMap(@org.jetbrains.annotations.NotNull
        java.util.Map<java.lang.String, ? extends java.lang.Object> map) {
            return null;
        }
    }
}