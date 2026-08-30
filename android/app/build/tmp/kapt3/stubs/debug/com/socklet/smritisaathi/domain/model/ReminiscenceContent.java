package com.socklet.smritisaathi.domain.model;

import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b!\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u008f\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0012H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0006H\u00c6\u0003J\t\u0010,\u001a\u00020\bH\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\u0093\u0001\u00102\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001J\u0013\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00106\u001a\u000207H\u00d6\u0001J\t\u00108\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0015\u00a8\u00069"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/ReminiscenceContent;", "", "id", "", "patientId", "type", "Lcom/socklet/smritisaathi/domain/model/ReminiscenceContentType;", "lifeStage", "Lcom/socklet/smritisaathi/domain/model/LifeStage;", "title", "description", "photoUrl", "audioUrl", "personTag", "uploadedBy", "yearApprox", "location", "createdAt", "Ljava/util/Date;", "(Ljava/lang/String;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/ReminiscenceContentType;Lcom/socklet/smritisaathi/domain/model/LifeStage;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;)V", "getAudioUrl", "()Ljava/lang/String;", "getCreatedAt", "()Ljava/util/Date;", "getDescription", "getId", "getLifeStage", "()Lcom/socklet/smritisaathi/domain/model/LifeStage;", "getLocation", "getPatientId", "getPersonTag", "getPhotoUrl", "getTitle", "getType", "()Lcom/socklet/smritisaathi/domain/model/ReminiscenceContentType;", "getUploadedBy", "getYearApprox", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class ReminiscenceContent {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientId = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.ReminiscenceContentType type = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.LifeStage lifeStage = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String description = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String photoUrl = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String audioUrl = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String personTag = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String uploadedBy = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String yearApprox = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String location = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date createdAt = null;
    
    public ReminiscenceContent(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.ReminiscenceContentType type, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.LifeStage lifeStage, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.Nullable
    java.lang.String photoUrl, @org.jetbrains.annotations.Nullable
    java.lang.String audioUrl, @org.jetbrains.annotations.NotNull
    java.lang.String personTag, @org.jetbrains.annotations.NotNull
    java.lang.String uploadedBy, @org.jetbrains.annotations.Nullable
    java.lang.String yearApprox, @org.jetbrains.annotations.Nullable
    java.lang.String location, @org.jetbrains.annotations.NotNull
    java.util.Date createdAt) {
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
    public final com.socklet.smritisaathi.domain.model.ReminiscenceContentType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.LifeStage getLifeStage() {
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
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPhotoUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getAudioUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPersonTag() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUploadedBy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getYearApprox() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getCreatedAt() {
        return null;
    }
    
    public ReminiscenceContent() {
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
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.ReminiscenceContentType component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.LifeStage component4() {
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
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component7() {
        return null;
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
    public final com.socklet.smritisaathi.domain.model.ReminiscenceContent copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.ReminiscenceContentType type, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.LifeStage lifeStage, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.Nullable
    java.lang.String photoUrl, @org.jetbrains.annotations.Nullable
    java.lang.String audioUrl, @org.jetbrains.annotations.NotNull
    java.lang.String personTag, @org.jetbrains.annotations.NotNull
    java.lang.String uploadedBy, @org.jetbrains.annotations.Nullable
    java.lang.String yearApprox, @org.jetbrains.annotations.Nullable
    java.lang.String location, @org.jetbrains.annotations.NotNull
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