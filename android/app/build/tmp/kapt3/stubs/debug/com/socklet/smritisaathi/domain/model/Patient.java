package com.socklet.smritisaathi.domain.model;

import android.net.Uri;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\bD\b\u0086\b\u0018\u00002\u00020\u0001B\u008f\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u001b\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0010\u0012\b\b\u0002\u0010\u001f\u001a\u00020 \u0012\b\b\u0002\u0010!\u001a\u00020\u0003\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$\u0012\b\b\u0002\u0010%\u001a\u00020&\u0012\b\b\u0002\u0010\'\u001a\u00020&\u00a2\u0006\u0002\u0010(J\t\u0010M\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u00c6\u0003J\u000b\u0010O\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010P\u001a\u0004\u0018\u00010\u0014H\u00c6\u0003J\u000b\u0010Q\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u0017H\u00c6\u0003J\t\u0010S\u001a\u00020\u0019H\u00c6\u0003J\t\u0010T\u001a\u00020\u001bH\u00c6\u0003J\t\u0010U\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010V\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0010H\u00c6\u0003J\t\u0010W\u001a\u00020 H\u00c6\u0003J\t\u0010X\u001a\u00020\u0003H\u00c6\u0003J\t\u0010Y\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010Z\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010[\u001a\u0004\u0018\u00010$H\u00c6\u0003J\t\u0010\\\u001a\u00020&H\u00c6\u0003J\t\u0010]\u001a\u00020&H\u00c6\u0003J\t\u0010^\u001a\u00020\u0006H\u00c6\u0003J\t\u0010_\u001a\u00020\bH\u00c6\u0003J\u000b\u0010`\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010a\u001a\u00020\u000bH\u00c6\u0003J\t\u0010b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010d\u001a\u00020\u0003H\u00c6\u0003J\u0093\u0002\u0010e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u00032\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00102\b\b\u0002\u0010\u001f\u001a\u00020 2\b\b\u0002\u0010!\u001a\u00020\u00032\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$2\b\b\u0002\u0010%\u001a\u00020&2\b\b\u0002\u0010\'\u001a\u00020&H\u00c6\u0001J\u0013\u0010f\u001a\u00020 2\b\u0010g\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010h\u001a\u00020\u0006H\u00d6\u0001J\t\u0010i\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010.R\u0011\u0010%\u001a\u00020&\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u0010!\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010.R\u0011\u0010\u0018\u001a\u00020\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0011\u0010\u001a\u001a\u00020\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0011\u0010\u001f\u001a\u00020 \u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010>R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010.R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010.R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010>R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u0010.R\u0013\u0010\"\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010.R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010.R\u0013\u0010#\u001a\u0004\u0018\u00010$\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010HR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u0010.R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u0010.R\u0011\u0010\u001c\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bK\u0010.R\u0011\u0010\'\u001a\u00020&\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u00103\u00a8\u0006j"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/Patient;", "", "id", "", "name", "age", "", "gender", "Lcom/socklet/smritisaathi/domain/model/Gender;", "photoUrl", "dementiaStage", "Lcom/socklet/smritisaathi/domain/model/DementiaStage;", "preferredLanguage", "pairingCode", "familyInviteCode", "familyContacts", "", "Lcom/socklet/smritisaathi/domain/model/FamilyContact;", "assignedDoctorId", "assignedDoctor", "Lcom/socklet/smritisaathi/domain/model/Doctor;", "assignedHospitalId", "assignedHospital", "Lcom/socklet/smritisaathi/domain/model/Hospital;", "dailyRoutine", "Lcom/socklet/smritisaathi/domain/model/DailyRoutine;", "emergencyContact", "Lcom/socklet/smritisaathi/domain/model/EmergencyContact;", "sosNumber", "medicalReports", "Lcom/socklet/smritisaathi/domain/model/MedicalReport;", "enhancedSupportEnabled", "", "createdBy", "pairedDeviceId", "pendingDoctorRequest", "Lcom/socklet/smritisaathi/domain/model/DoctorPatientRequest;", "createdAt", "Ljava/util/Date;", "updatedAt", "(Ljava/lang/String;Ljava/lang/String;ILcom/socklet/smritisaathi/domain/model/Gender;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/DementiaStage;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/Doctor;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/Hospital;Lcom/socklet/smritisaathi/domain/model/DailyRoutine;Lcom/socklet/smritisaathi/domain/model/EmergencyContact;Ljava/lang/String;Ljava/util/List;ZLjava/lang/String;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/DoctorPatientRequest;Ljava/util/Date;Ljava/util/Date;)V", "getAge", "()I", "getAssignedDoctor", "()Lcom/socklet/smritisaathi/domain/model/Doctor;", "getAssignedDoctorId", "()Ljava/lang/String;", "getAssignedHospital", "()Lcom/socklet/smritisaathi/domain/model/Hospital;", "getAssignedHospitalId", "getCreatedAt", "()Ljava/util/Date;", "getCreatedBy", "getDailyRoutine", "()Lcom/socklet/smritisaathi/domain/model/DailyRoutine;", "getDementiaStage", "()Lcom/socklet/smritisaathi/domain/model/DementiaStage;", "getEmergencyContact", "()Lcom/socklet/smritisaathi/domain/model/EmergencyContact;", "getEnhancedSupportEnabled", "()Z", "getFamilyContacts", "()Ljava/util/List;", "getFamilyInviteCode", "getGender", "()Lcom/socklet/smritisaathi/domain/model/Gender;", "getId", "getMedicalReports", "getName", "getPairedDeviceId", "getPairingCode", "getPendingDoctorRequest", "()Lcom/socklet/smritisaathi/domain/model/DoctorPatientRequest;", "getPhotoUrl", "getPreferredLanguage", "getSosNumber", "getUpdatedAt", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class Patient {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String name = null;
    private final int age = 0;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.Gender gender = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String photoUrl = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.DementiaStage dementiaStage = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String preferredLanguage = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String pairingCode = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String familyInviteCode = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> familyContacts = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String assignedDoctorId = null;
    @org.jetbrains.annotations.Nullable
    private final com.socklet.smritisaathi.domain.model.Doctor assignedDoctor = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String assignedHospitalId = null;
    @org.jetbrains.annotations.Nullable
    private final com.socklet.smritisaathi.domain.model.Hospital assignedHospital = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.DailyRoutine dailyRoutine = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.EmergencyContact emergencyContact = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String sosNumber = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> medicalReports = null;
    private final boolean enhancedSupportEnabled = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String createdBy = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String pairedDeviceId = null;
    @org.jetbrains.annotations.Nullable
    private final com.socklet.smritisaathi.domain.model.DoctorPatientRequest pendingDoctorRequest = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date createdAt = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date updatedAt = null;
    
    public Patient(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String name, int age, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Gender gender, @org.jetbrains.annotations.Nullable
    java.lang.String photoUrl, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DementiaStage dementiaStage, @org.jetbrains.annotations.NotNull
    java.lang.String preferredLanguage, @org.jetbrains.annotations.NotNull
    java.lang.String pairingCode, @org.jetbrains.annotations.NotNull
    java.lang.String familyInviteCode, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> familyContacts, @org.jetbrains.annotations.Nullable
    java.lang.String assignedDoctorId, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Doctor assignedDoctor, @org.jetbrains.annotations.Nullable
    java.lang.String assignedHospitalId, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Hospital assignedHospital, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DailyRoutine dailyRoutine, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.EmergencyContact emergencyContact, @org.jetbrains.annotations.NotNull
    java.lang.String sosNumber, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> medicalReports, boolean enhancedSupportEnabled, @org.jetbrains.annotations.NotNull
    java.lang.String createdBy, @org.jetbrains.annotations.Nullable
    java.lang.String pairedDeviceId, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.DoctorPatientRequest pendingDoctorRequest, @org.jetbrains.annotations.NotNull
    java.util.Date createdAt, @org.jetbrains.annotations.NotNull
    java.util.Date updatedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getName() {
        return null;
    }
    
    public final int getAge() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.Gender getGender() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPhotoUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DementiaStage getDementiaStage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPreferredLanguage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPairingCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFamilyInviteCode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> getFamilyContacts() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getAssignedDoctorId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Doctor getAssignedDoctor() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getAssignedHospitalId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Hospital getAssignedHospital() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DailyRoutine getDailyRoutine() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.EmergencyContact getEmergencyContact() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSosNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> getMedicalReports() {
        return null;
    }
    
    public final boolean getEnhancedSupportEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCreatedBy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPairedDeviceId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.DoctorPatientRequest getPendingDoctorRequest() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getUpdatedAt() {
        return null;
    }
    
    public Patient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Doctor component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Hospital component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DailyRoutine component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.EmergencyContact component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> component18() {
        return null;
    }
    
    public final boolean component19() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.DoctorPatientRequest component22() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component23() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component24() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.Gender component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DementiaStage component6() {
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
    public final com.socklet.smritisaathi.domain.model.Patient copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String name, int age, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Gender gender, @org.jetbrains.annotations.Nullable
    java.lang.String photoUrl, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DementiaStage dementiaStage, @org.jetbrains.annotations.NotNull
    java.lang.String preferredLanguage, @org.jetbrains.annotations.NotNull
    java.lang.String pairingCode, @org.jetbrains.annotations.NotNull
    java.lang.String familyInviteCode, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> familyContacts, @org.jetbrains.annotations.Nullable
    java.lang.String assignedDoctorId, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Doctor assignedDoctor, @org.jetbrains.annotations.Nullable
    java.lang.String assignedHospitalId, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Hospital assignedHospital, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DailyRoutine dailyRoutine, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.EmergencyContact emergencyContact, @org.jetbrains.annotations.NotNull
    java.lang.String sosNumber, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> medicalReports, boolean enhancedSupportEnabled, @org.jetbrains.annotations.NotNull
    java.lang.String createdBy, @org.jetbrains.annotations.Nullable
    java.lang.String pairedDeviceId, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.DoctorPatientRequest pendingDoctorRequest, @org.jetbrains.annotations.NotNull
    java.util.Date createdAt, @org.jetbrains.annotations.NotNull
    java.util.Date updatedAt) {
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