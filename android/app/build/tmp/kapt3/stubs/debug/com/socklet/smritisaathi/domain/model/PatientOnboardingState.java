package com.socklet.smritisaathi.domain.model;

import android.net.Uri;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0018"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/PatientOnboardingState;", "", "currentStep", "", "patient", "Lcom/socklet/smritisaathi/domain/model/Patient;", "isComplete", "", "(ILcom/socklet/smritisaathi/domain/model/Patient;Z)V", "getCurrentStep", "()I", "()Z", "getPatient", "()Lcom/socklet/smritisaathi/domain/model/Patient;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "toString", "", "Companion", "app_debug"})
public final class PatientOnboardingState {
    private final int currentStep = 0;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.Patient patient = null;
    private final boolean isComplete = false;
    public static final int TOTAL_STEPS = 8;
    public static final int STEP_BASIC_DETAILS = 0;
    public static final int STEP_DEMENTIA_STAGE = 1;
    public static final int STEP_MEDICAL_REPORTS = 2;
    public static final int STEP_FAMILY_CONTACTS = 3;
    public static final int STEP_DOCTOR_ASSIGNMENT = 4;
    public static final int STEP_DAILY_ROUTINE = 5;
    public static final int STEP_EMERGENCY_CONTACT = 6;
    public static final int STEP_ENHANCED_SUPPORT = 7;
    @org.jetbrains.annotations.NotNull
    public static final com.socklet.smritisaathi.domain.model.PatientOnboardingState.Companion Companion = null;
    
    public PatientOnboardingState(int currentStep, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Patient patient, boolean isComplete) {
        super();
    }
    
    public final int getCurrentStep() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.Patient getPatient() {
        return null;
    }
    
    public final boolean isComplete() {
        return false;
    }
    
    public PatientOnboardingState() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.Patient component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.PatientOnboardingState copy(int currentStep, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Patient patient, boolean isComplete) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/PatientOnboardingState$Companion;", "", "()V", "STEP_BASIC_DETAILS", "", "STEP_DAILY_ROUTINE", "STEP_DEMENTIA_STAGE", "STEP_DOCTOR_ASSIGNMENT", "STEP_EMERGENCY_CONTACT", "STEP_ENHANCED_SUPPORT", "STEP_FAMILY_CONTACTS", "STEP_MEDICAL_REPORTS", "TOTAL_STEPS", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}