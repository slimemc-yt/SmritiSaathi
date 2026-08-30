package com.socklet.smritisaathi.domain.model;

import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b#\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u008d\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u0014J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0013H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0006H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u000bH\u00c6\u0003J\t\u00101\u001a\u00020\rH\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u0091\u0001\u00103\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u00c6\u0001J\u0013\u00104\u001a\u00020\u00132\b\u00105\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00106\u001a\u000207H\u00d6\u0001J\t\u00108\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0019R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0016\u00a8\u00069"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/Reminder;", "", "id", "", "patientId", "type", "Lcom/socklet/smritisaathi/domain/model/ReminderType;", "title", "description", "scheduledTime", "scheduledDate", "Ljava/util/Date;", "status", "Lcom/socklet/smritisaathi/domain/model/ReminderStatus;", "respondedAt", "medicineName", "dosage", "voicePromptText", "isRecurring", "", "(Ljava/lang/String;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/ReminderType;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Lcom/socklet/smritisaathi/domain/model/ReminderStatus;Ljava/util/Date;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getDescription", "()Ljava/lang/String;", "getDosage", "getId", "()Z", "getMedicineName", "getPatientId", "getRespondedAt", "()Ljava/util/Date;", "getScheduledDate", "getScheduledTime", "getStatus", "()Lcom/socklet/smritisaathi/domain/model/ReminderStatus;", "getTitle", "getType", "()Lcom/socklet/smritisaathi/domain/model/ReminderType;", "getVoicePromptText", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class Reminder {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientId = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.ReminderType type = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String scheduledTime = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date scheduledDate = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.ReminderStatus status = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.Date respondedAt = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String medicineName = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String dosage = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String voicePromptText = null;
    private final boolean isRecurring = false;
    
    public Reminder(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.ReminderType type, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String scheduledTime, @org.jetbrains.annotations.NotNull
    java.util.Date scheduledDate, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.ReminderStatus status, @org.jetbrains.annotations.Nullable
    java.util.Date respondedAt, @org.jetbrains.annotations.Nullable
    java.lang.String medicineName, @org.jetbrains.annotations.Nullable
    java.lang.String dosage, @org.jetbrains.annotations.NotNull
    java.lang.String voicePromptText, boolean isRecurring) {
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
    public final com.socklet.smritisaathi.domain.model.ReminderType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getScheduledTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getScheduledDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.ReminderStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date getRespondedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getMedicineName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDosage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getVoicePromptText() {
        return null;
    }
    
    public final boolean isRecurring() {
        return false;
    }
    
    public Reminder() {
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
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component12() {
        return null;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.ReminderType component3() {
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
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.ReminderStatus component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.Reminder copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.ReminderType type, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String scheduledTime, @org.jetbrains.annotations.NotNull
    java.util.Date scheduledDate, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.ReminderStatus status, @org.jetbrains.annotations.Nullable
    java.util.Date respondedAt, @org.jetbrains.annotations.Nullable
    java.lang.String medicineName, @org.jetbrains.annotations.Nullable
    java.lang.String dosage, @org.jetbrains.annotations.NotNull
    java.lang.String voicePromptText, boolean isRecurring) {
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