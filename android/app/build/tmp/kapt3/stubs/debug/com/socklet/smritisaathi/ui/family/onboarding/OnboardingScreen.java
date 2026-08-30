package com.socklet.smritisaathi.ui.family.onboarding;

import androidx.compose.animation.AnimatedContentTransitionScope;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import androidx.navigation.NavHostController;
import com.socklet.smritisaathi.domain.model.DementiaStage;
import com.socklet.smritisaathi.ui.theme.Dimensions;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\t\u0007\b\t\n\u000b\f\r\u000e\u000fB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\t\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "", "route", "", "(Ljava/lang/String;)V", "getRoute", "()Ljava/lang/String;", "BasicDetails", "Completion", "DailyRoutine", "DementiaStage", "DoctorHospital", "EmergencyContact", "EnhancedSupport", "FamilyContacts", "MedicalReports", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$BasicDetails;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$Completion;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$DailyRoutine;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$DementiaStage;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$DoctorHospital;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$EmergencyContact;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$EnhancedSupport;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$FamilyContacts;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$MedicalReports;", "app_debug"})
public abstract class OnboardingScreen {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String route = null;
    
    private OnboardingScreen(java.lang.String route) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRoute() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$BasicDetails;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "()V", "app_debug"})
    public static final class BasicDetails extends com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen.BasicDetails INSTANCE = null;
        
        private BasicDetails() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$Completion;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "()V", "app_debug"})
    public static final class Completion extends com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen.Completion INSTANCE = null;
        
        private Completion() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$DailyRoutine;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "()V", "app_debug"})
    public static final class DailyRoutine extends com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen.DailyRoutine INSTANCE = null;
        
        private DailyRoutine() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$DementiaStage;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "()V", "app_debug"})
    public static final class DementiaStage extends com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen.DementiaStage INSTANCE = null;
        
        private DementiaStage() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$DoctorHospital;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "()V", "app_debug"})
    public static final class DoctorHospital extends com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen.DoctorHospital INSTANCE = null;
        
        private DoctorHospital() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$EmergencyContact;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "()V", "app_debug"})
    public static final class EmergencyContact extends com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen.EmergencyContact INSTANCE = null;
        
        private EmergencyContact() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$EnhancedSupport;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "()V", "app_debug"})
    public static final class EnhancedSupport extends com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen.EnhancedSupport INSTANCE = null;
        
        private EnhancedSupport() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$FamilyContacts;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "()V", "app_debug"})
    public static final class FamilyContacts extends com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen.FamilyContacts INSTANCE = null;
        
        private FamilyContacts() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen$MedicalReports;", "Lcom/socklet/smritisaathi/ui/family/onboarding/OnboardingScreen;", "()V", "app_debug"})
    public static final class MedicalReports extends com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.family.onboarding.OnboardingScreen.MedicalReports INSTANCE = null;
        
        private MedicalReports() {
        }
    }
}