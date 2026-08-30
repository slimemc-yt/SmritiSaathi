package com.socklet.smritisaathi.domain.model;

import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b*\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\b\u0086\b\u0018\u0000 E2\u00020\u0001:\u0001EB\u00a9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0014\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0017J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0012H\u00c6\u0003J\t\u00102\u001a\u00020\u0014H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0014H\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0007H\u00c6\u0003J\t\u00108\u001a\u00020\tH\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\u00ad\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010A\u001a\u00020\u0007H\u00d6\u0001J\u0014\u0010B\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00010CJ\t\u0010D\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0019R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0019R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010)R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,\u00a8\u0006F"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/DoctorPatientRequest;", "", "id", "", "patientId", "patientName", "patientAge", "", "dementiaStage", "Lcom/socklet/smritisaathi/domain/model/DementiaStage;", "familyId", "familyName", "familyRelationship", "doctorId", "doctorName", "doctorCode", "hospitalName", "status", "Lcom/socklet/smritisaathi/domain/model/DoctorRequestStatus;", "requestedAt", "Ljava/util/Date;", "respondedAt", "declineReason", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/socklet/smritisaathi/domain/model/DementiaStage;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/DoctorRequestStatus;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;)V", "getDeclineReason", "()Ljava/lang/String;", "getDementiaStage", "()Lcom/socklet/smritisaathi/domain/model/DementiaStage;", "getDoctorCode", "getDoctorId", "getDoctorName", "getFamilyId", "getFamilyName", "getFamilyRelationship", "getHospitalName", "getId", "getPatientAge", "()I", "getPatientId", "getPatientName", "getRequestedAt", "()Ljava/util/Date;", "getRespondedAt", "getStatus", "()Lcom/socklet/smritisaathi/domain/model/DoctorRequestStatus;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toMap", "", "toString", "Companion", "app_debug"})
public final class DoctorPatientRequest {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientName = null;
    private final int patientAge = 0;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.DementiaStage dementiaStage = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String familyId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String familyName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String familyRelationship = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String doctorId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String doctorName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String doctorCode = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String hospitalName = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.DoctorRequestStatus status = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date requestedAt = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.Date respondedAt = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String declineReason = null;
    @org.jetbrains.annotations.NotNull
    public static final com.socklet.smritisaathi.domain.model.DoctorPatientRequest.Companion Companion = null;
    
    public DoctorPatientRequest(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    java.lang.String patientName, int patientAge, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DementiaStage dementiaStage, @org.jetbrains.annotations.NotNull
    java.lang.String familyId, @org.jetbrains.annotations.NotNull
    java.lang.String familyName, @org.jetbrains.annotations.NotNull
    java.lang.String familyRelationship, @org.jetbrains.annotations.NotNull
    java.lang.String doctorId, @org.jetbrains.annotations.NotNull
    java.lang.String doctorName, @org.jetbrains.annotations.NotNull
    java.lang.String doctorCode, @org.jetbrains.annotations.NotNull
    java.lang.String hospitalName, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DoctorRequestStatus status, @org.jetbrains.annotations.NotNull
    java.util.Date requestedAt, @org.jetbrains.annotations.Nullable
    java.util.Date respondedAt, @org.jetbrains.annotations.Nullable
    java.lang.String declineReason) {
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
    
    public final int getPatientAge() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DementiaStage getDementiaStage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFamilyId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFamilyName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFamilyRelationship() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDoctorId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDoctorName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDoctorCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getHospitalName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DoctorRequestStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getRequestedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date getRespondedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDeclineReason() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Object> toMap() {
        return null;
    }
    
    public DoctorPatientRequest() {
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
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DoctorRequestStatus component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component16() {
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
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DementiaStage component5() {
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
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DoctorPatientRequest copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    java.lang.String patientName, int patientAge, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DementiaStage dementiaStage, @org.jetbrains.annotations.NotNull
    java.lang.String familyId, @org.jetbrains.annotations.NotNull
    java.lang.String familyName, @org.jetbrains.annotations.NotNull
    java.lang.String familyRelationship, @org.jetbrains.annotations.NotNull
    java.lang.String doctorId, @org.jetbrains.annotations.NotNull
    java.lang.String doctorName, @org.jetbrains.annotations.NotNull
    java.lang.String doctorCode, @org.jetbrains.annotations.NotNull
    java.lang.String hospitalName, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DoctorRequestStatus status, @org.jetbrains.annotations.NotNull
    java.util.Date requestedAt, @org.jetbrains.annotations.Nullable
    java.util.Date respondedAt, @org.jetbrains.annotations.Nullable
    java.lang.String declineReason) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006\u00a8\u0006\b"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/DoctorPatientRequest$Companion;", "", "()V", "fromMap", "Lcom/socklet/smritisaathi/domain/model/DoctorPatientRequest;", "map", "", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.socklet.smritisaathi.domain.model.DoctorPatientRequest fromMap(@org.jetbrains.annotations.NotNull
        java.util.Map<java.lang.String, ? extends java.lang.Object> map) {
            return null;
        }
    }
}