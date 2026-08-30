package com.socklet.smritisaathi.domain.model;

import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\"\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bw\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0012J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0007H\u00c6\u0003J\t\u0010)\u001a\u00020\tH\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\rH\u00c6\u0003J\t\u0010-\u001a\u00020\u000fH\u00c6\u0003J{\u0010.\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010/\u001a\u00020\u000f2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u000202H\u00d6\u0001J\t\u00103\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"\u00a8\u00064"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/BehavioralAlert;", "", "id", "", "patientId", "patientName", "type", "Lcom/socklet/smritisaathi/domain/model/AlertType;", "severity", "Lcom/socklet/smritisaathi/domain/model/AlertSeverity;", "title", "description", "timestamp", "Ljava/util/Date;", "acknowledged", "", "acknowledgedBy", "actionTaken", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/AlertType;Lcom/socklet/smritisaathi/domain/model/AlertSeverity;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;ZLjava/lang/String;Ljava/lang/String;)V", "getAcknowledged", "()Z", "getAcknowledgedBy", "()Ljava/lang/String;", "getActionTaken", "getDescription", "getId", "getPatientId", "getPatientName", "getSeverity", "()Lcom/socklet/smritisaathi/domain/model/AlertSeverity;", "getTimestamp", "()Ljava/util/Date;", "getTitle", "getType", "()Lcom/socklet/smritisaathi/domain/model/AlertType;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class BehavioralAlert {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientName = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.AlertType type = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.AlertSeverity severity = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date timestamp = null;
    private final boolean acknowledged = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String acknowledgedBy = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String actionTaken = null;
    
    public BehavioralAlert(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    java.lang.String patientName, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.AlertType type, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.AlertSeverity severity, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.util.Date timestamp, boolean acknowledged, @org.jetbrains.annotations.Nullable
    java.lang.String acknowledgedBy, @org.jetbrains.annotations.Nullable
    java.lang.String actionTaken) {
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
    public final java.lang.String getPatientName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.AlertType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.AlertSeverity getSeverity() {
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
    public final java.util.Date getTimestamp() {
        return null;
    }
    
    public final boolean getAcknowledged() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getAcknowledgedBy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getActionTaken() {
        return null;
    }
    
    public BehavioralAlert() {
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
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.AlertType component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.AlertSeverity component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component8() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.BehavioralAlert copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    java.lang.String patientName, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.AlertType type, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.AlertSeverity severity, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.util.Date timestamp, boolean acknowledged, @org.jetbrains.annotations.Nullable
    java.lang.String acknowledgedBy, @org.jetbrains.annotations.Nullable
    java.lang.String actionTaken) {
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