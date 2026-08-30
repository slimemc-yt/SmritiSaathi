package com.socklet.smritisaathi.domain.model;

import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0091\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u0014J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0013H\u00c6\u0003J\t\u0010,\u001a\u00020\u0005H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u00101\u001a\u00020\fH\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\u0095\u0001\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u00c6\u0001J\u0013\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00108\u001a\u00020\fH\u00d6\u0001J\t\u00109\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0016\u00a8\u0006:"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/CognitiveTask;", "", "id", "", "type", "Lcom/socklet/smritisaathi/domain/model/TaskType;", "title", "subtitle", "voiceAnnouncement", "gameType", "Lcom/socklet/smritisaathi/domain/model/GameType;", "difficultyLevel", "", "reminderId", "scheduledTime", "senderName", "senderPhotoUrl", "autoPlayAudioUrl", "createdAt", "Ljava/util/Date;", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/TaskType;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/GameType;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;)V", "getAutoPlayAudioUrl", "()Ljava/lang/String;", "getCreatedAt", "()Ljava/util/Date;", "getDifficultyLevel", "()I", "getGameType", "()Lcom/socklet/smritisaathi/domain/model/GameType;", "getId", "getReminderId", "getScheduledTime", "getSenderName", "getSenderPhotoUrl", "getSubtitle", "getTitle", "getType", "()Lcom/socklet/smritisaathi/domain/model/TaskType;", "getVoiceAnnouncement", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class CognitiveTask {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.TaskType type = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String subtitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String voiceAnnouncement = null;
    @org.jetbrains.annotations.Nullable
    private final com.socklet.smritisaathi.domain.model.GameType gameType = null;
    private final int difficultyLevel = 0;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String reminderId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String scheduledTime = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String senderName = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String senderPhotoUrl = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String autoPlayAudioUrl = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date createdAt = null;
    
    public CognitiveTask(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.TaskType type, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull
    java.lang.String voiceAnnouncement, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.GameType gameType, int difficultyLevel, @org.jetbrains.annotations.Nullable
    java.lang.String reminderId, @org.jetbrains.annotations.NotNull
    java.lang.String scheduledTime, @org.jetbrains.annotations.Nullable
    java.lang.String senderName, @org.jetbrains.annotations.Nullable
    java.lang.String senderPhotoUrl, @org.jetbrains.annotations.Nullable
    java.lang.String autoPlayAudioUrl, @org.jetbrains.annotations.NotNull
    java.util.Date createdAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.TaskType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSubtitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getVoiceAnnouncement() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.GameType getGameType() {
        return null;
    }
    
    public final int getDifficultyLevel() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getReminderId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getScheduledTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getSenderName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getSenderPhotoUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getAutoPlayAudioUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getCreatedAt() {
        return null;
    }
    
    public CognitiveTask() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.TaskType component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.GameType component6() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.CognitiveTask copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.TaskType type, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull
    java.lang.String voiceAnnouncement, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.GameType gameType, int difficultyLevel, @org.jetbrains.annotations.Nullable
    java.lang.String reminderId, @org.jetbrains.annotations.NotNull
    java.lang.String scheduledTime, @org.jetbrains.annotations.Nullable
    java.lang.String senderName, @org.jetbrains.annotations.Nullable
    java.lang.String senderPhotoUrl, @org.jetbrains.annotations.Nullable
    java.lang.String autoPlayAudioUrl, @org.jetbrains.annotations.NotNull
    java.util.Date createdAt) {
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
}