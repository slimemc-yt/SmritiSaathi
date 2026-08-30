package com.socklet.smritisaathi.domain.model;

import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\b\u0086\b\u0018\u0000 12\u00020\u0001:\u00011Bo\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0010J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003J\t\u0010(\u001a\u00020\u000eH\u00c6\u0003Js\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020\u0005H\u00d6\u0001J\u0014\u0010.\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00010/J\t\u00100\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0014R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018\u00a8\u00062"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/CognitiveAssessment;", "", "patientId", "", "overallScore", "", "memoryScore", "attentionScore", "problemSolvingScore", "reactionTimeScore", "trend", "recommendations", "", "assessedAt", "Ljava/util/Date;", "period", "(Ljava/lang/String;IIIIILjava/lang/String;Ljava/util/List;Ljava/util/Date;Ljava/lang/String;)V", "getAssessedAt", "()Ljava/util/Date;", "getAttentionScore", "()I", "getMemoryScore", "getOverallScore", "getPatientId", "()Ljava/lang/String;", "getPeriod", "getProblemSolvingScore", "getReactionTimeScore", "getRecommendations", "()Ljava/util/List;", "getTrend", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toMap", "", "toString", "Companion", "app_debug"})
public final class CognitiveAssessment {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientId = null;
    private final int overallScore = 0;
    private final int memoryScore = 0;
    private final int attentionScore = 0;
    private final int problemSolvingScore = 0;
    private final int reactionTimeScore = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String trend = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> recommendations = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date assessedAt = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String period = null;
    @org.jetbrains.annotations.NotNull
    public static final com.socklet.smritisaathi.domain.model.CognitiveAssessment.Companion Companion = null;
    
    public CognitiveAssessment(@org.jetbrains.annotations.NotNull
    java.lang.String patientId, int overallScore, int memoryScore, int attentionScore, int problemSolvingScore, int reactionTimeScore, @org.jetbrains.annotations.NotNull
    java.lang.String trend, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> recommendations, @org.jetbrains.annotations.NotNull
    java.util.Date assessedAt, @org.jetbrains.annotations.NotNull
    java.lang.String period) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPatientId() {
        return null;
    }
    
    public final int getOverallScore() {
        return 0;
    }
    
    public final int getMemoryScore() {
        return 0;
    }
    
    public final int getAttentionScore() {
        return 0;
    }
    
    public final int getProblemSolvingScore() {
        return 0;
    }
    
    public final int getReactionTimeScore() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTrend() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getRecommendations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getAssessedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPeriod() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Object> toMap() {
        return null;
    }
    
    public CognitiveAssessment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component10() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
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
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.CognitiveAssessment copy(@org.jetbrains.annotations.NotNull
    java.lang.String patientId, int overallScore, int memoryScore, int attentionScore, int problemSolvingScore, int reactionTimeScore, @org.jetbrains.annotations.NotNull
    java.lang.String trend, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> recommendations, @org.jetbrains.annotations.NotNull
    java.util.Date assessedAt, @org.jetbrains.annotations.NotNull
    java.lang.String period) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006\u00a8\u0006\b"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/CognitiveAssessment$Companion;", "", "()V", "fromMap", "Lcom/socklet/smritisaathi/domain/model/CognitiveAssessment;", "map", "", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.socklet.smritisaathi.domain.model.CognitiveAssessment fromMap(@org.jetbrains.annotations.NotNull
        java.util.Map<java.lang.String, ? extends java.lang.Object> map) {
            return null;
        }
    }
}