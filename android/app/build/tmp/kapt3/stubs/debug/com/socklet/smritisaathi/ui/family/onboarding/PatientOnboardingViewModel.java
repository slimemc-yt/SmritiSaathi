package com.socklet.smritisaathi.ui.family.onboarding;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.socklet.smritisaathi.domain.model.*;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import com.socklet.smritisaathi.data.seeder.DatabaseSeeder;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.Date;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00aa\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ$\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u00112\u0006\u0010\u0018\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020%J\u000e\u0010&\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020(J\u001e\u0010)\u001a\u00020\u001c2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-J\u0006\u0010/\u001a\u00020\u001cJ\b\u00100\u001a\u00020\u001cH\u0002J\u0006\u00101\u001a\u00020\u001cJ\u0006\u00102\u001a\u00020\u001cJ\u000e\u00103\u001a\u00020\u001c2\u0006\u00104\u001a\u000205J\u000e\u00106\u001a\u00020\u001c2\u0006\u00104\u001a\u000205J\u000e\u00107\u001a\u00020\u001c2\u0006\u00108\u001a\u00020-J\u000e\u00109\u001a\u00020\u001c2\u0006\u00104\u001a\u000205J\u000e\u0010:\u001a\u00020\u001c2\u0006\u00104\u001a\u000205J\u000e\u0010;\u001a\u00020\u001c2\u0006\u00108\u001a\u00020-J\"\u0010<\u001a\u00020\u001c2\u0006\u0010=\u001a\u00020-2\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u001c0?J\u000e\u0010@\u001a\u00020\u001c2\u0006\u0010A\u001a\u00020-J\u0010\u0010B\u001a\u00020\u001c2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012J\u0010\u0010C\u001a\u00020\u001c2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0017J\u0010\u0010D\u001a\u00020\u001c2\b\u0010E\u001a\u0004\u0018\u00010FJ\u000e\u0010G\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020-J\u000e\u0010I\u001a\u00020\u001c2\u0006\u0010J\u001a\u00020-J\u0010\u0010K\u001a\u00020\u001c2\b\u0010*\u001a\u0004\u0018\u00010+J\u000e\u0010L\u001a\u00020\u001c2\u0006\u0010M\u001a\u00020-J\u000e\u0010N\u001a\u00020\u001c2\u0006\u0010O\u001a\u00020PJ\u000e\u0010Q\u001a\u00020\u001c2\u0006\u00104\u001a\u000205J\u000e\u0010R\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020-J\u000e\u0010S\u001a\u00020\u001c2\u0006\u0010J\u001a\u00020-J\u000e\u0010T\u001a\u00020\u001c2\u0006\u0010M\u001a\u00020-J\u000e\u0010U\u001a\u00020\u001c2\u0006\u0010V\u001a\u00020WJ\u000e\u0010X\u001a\u00020\u001c2\u0006\u0010Y\u001a\u00020-J\u000e\u0010Z\u001a\u00020\u001c2\u0006\u0010[\u001a\u00020\\J\u000e\u0010]\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020-J\u0010\u0010^\u001a\u00020\u001c2\b\u0010*\u001a\u0004\u0018\u00010+J\u000e\u0010_\u001a\u00020\u001c2\u0006\u0010`\u001a\u00020WJ\u000e\u0010a\u001a\u00020\u001c2\u0006\u0010A\u001a\u00020-J\u000e\u0010b\u001a\u00020\u001c2\u0006\u0010c\u001a\u00020WJ\u000e\u0010d\u001a\u00020\u001c2\u0006\u0010c\u001a\u00020WJ\u000e\u0010e\u001a\u00020\u001c2\u0006\u0010f\u001a\u00020-J\u000e\u0010g\u001a\u00020\u001c2\u0006\u0010h\u001a\u00020-J\u000e\u0010i\u001a\u00020\u001c2\u0006\u0010f\u001a\u00020-J\u000e\u0010j\u001a\u00020\u001c2\u0006\u00104\u001a\u000205J4\u0010k\u001a\b\u0012\u0004\u0012\u00020\"0\u00112\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010l\u001a\u00020-H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bm\u0010nR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006o"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/PatientOnboardingViewModel;", "Landroidx/lifecycle/ViewModel;", "patientRepository", "Lcom/socklet/smritisaathi/domain/repository/PatientRepository;", "authRepository", "Lcom/socklet/smritisaathi/domain/repository/AuthRepository;", "databaseSeeder", "Lcom/socklet/smritisaathi/data/seeder/DatabaseSeeder;", "(Lcom/socklet/smritisaathi/domain/repository/PatientRepository;Lcom/socklet/smritisaathi/domain/repository/AuthRepository;Lcom/socklet/smritisaathi/data/seeder/DatabaseSeeder;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/socklet/smritisaathi/ui/family/onboarding/PatientOnboardingUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addCustomDoctor", "Lkotlin/Result;", "Lcom/socklet/smritisaathi/domain/model/Doctor;", "doctor", "addCustomDoctor-gIAlu-s", "(Lcom/socklet/smritisaathi/domain/model/Doctor;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addCustomHospital", "Lcom/socklet/smritisaathi/domain/model/Hospital;", "hospital", "addCustomHospital-gIAlu-s", "(Lcom/socklet/smritisaathi/domain/model/Hospital;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addFamilyContact", "", "addMealTime", "meal", "Lcom/socklet/smritisaathi/domain/model/MealTime;", "addMedicalReport", "report", "Lcom/socklet/smritisaathi/domain/model/MedicalReport;", "addMedicineTime", "medicine", "Lcom/socklet/smritisaathi/domain/model/MedicineTime;", "addNapTime", "nap", "Lcom/socklet/smritisaathi/domain/model/NapTime;", "addPendingMedicalReport", "uri", "Landroid/net/Uri;", "fileName", "", "reportType", "clearEditingContact", "loadDoctorsAndHospitals", "nextStep", "previousStep", "removeFamilyContact", "index", "", "removeMealTime", "removeMedicalReport", "reportId", "removeMedicineTime", "removeNapTime", "removePendingMedicalReport", "savePatient", "familyMemberId", "onComplete", "Lkotlin/Function1;", "searchRegisteredDoctors", "query", "selectDoctor", "selectHospital", "selectRegisteredDoctor", "doctorUser", "Lcom/socklet/smritisaathi/domain/model/User;", "setContactName", "name", "setContactPhone", "phone", "setContactPhoto", "setContactRelationship", "relationship", "setDementiaStage", "stage", "Lcom/socklet/smritisaathi/domain/model/DementiaStage;", "setEditingContact", "setEmergencyContactName", "setEmergencyContactPhone", "setEmergencyContactRelationship", "setEnhancedSupportEnabled", "enabled", "", "setPatientAge", "age", "setPatientGender", "gender", "Lcom/socklet/smritisaathi/domain/model/Gender;", "setPatientName", "setPatientPhoto", "setScreenPinningPermissionGranted", "granted", "setSearchQuery", "setShowAddDoctorDialog", "show", "setShowAddHospitalDialog", "setSleepTime", "time", "setSosNumber", "number", "setWakeTime", "updateFamilyContact", "uploadMedicalReport", "fileType", "uploadMedicalReport-BWLJW6A", "(Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class PatientOnboardingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.repository.PatientRepository patientRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.data.seeder.DatabaseSeeder databaseSeeder = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingUiState> uiState = null;
    
    @javax.inject.Inject
    public PatientOnboardingViewModel(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.repository.PatientRepository patientRepository, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.data.seeder.DatabaseSeeder databaseSeeder) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingUiState> getUiState() {
        return null;
    }
    
    private final void loadDoctorsAndHospitals() {
    }
    
    public final void searchRegisteredDoctors(@org.jetbrains.annotations.NotNull
    java.lang.String query) {
    }
    
    public final void nextStep() {
    }
    
    public final void previousStep() {
    }
    
    public final void setPatientName(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
    
    public final void setPatientAge(@org.jetbrains.annotations.NotNull
    java.lang.String age) {
    }
    
    public final void setPatientGender(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Gender gender) {
    }
    
    public final void setPatientPhoto(@org.jetbrains.annotations.Nullable
    android.net.Uri uri) {
    }
    
    public final void setDementiaStage(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DementiaStage stage) {
    }
    
    public final void addMedicalReport(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.MedicalReport report) {
    }
    
    public final void removeMedicalReport(@org.jetbrains.annotations.NotNull
    java.lang.String reportId) {
    }
    
    public final void addPendingMedicalReport(@org.jetbrains.annotations.NotNull
    android.net.Uri uri, @org.jetbrains.annotations.NotNull
    java.lang.String fileName, @org.jetbrains.annotations.NotNull
    java.lang.String reportType) {
    }
    
    public final void removePendingMedicalReport(@org.jetbrains.annotations.NotNull
    java.lang.String reportId) {
    }
    
    public final void addFamilyContact() {
    }
    
    public final void updateFamilyContact(int index) {
    }
    
    public final void removeFamilyContact(int index) {
    }
    
    public final void setEditingContact(int index) {
    }
    
    public final void clearEditingContact() {
    }
    
    public final void setContactName(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
    
    public final void setContactRelationship(@org.jetbrains.annotations.NotNull
    java.lang.String relationship) {
    }
    
    public final void setContactPhone(@org.jetbrains.annotations.NotNull
    java.lang.String phone) {
    }
    
    public final void setContactPhoto(@org.jetbrains.annotations.Nullable
    android.net.Uri uri) {
    }
    
    public final void setSearchQuery(@org.jetbrains.annotations.NotNull
    java.lang.String query) {
    }
    
    public final void selectDoctor(@org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Doctor doctor) {
    }
    
    public final void selectRegisteredDoctor(@org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.User doctorUser) {
    }
    
    public final void selectHospital(@org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Hospital hospital) {
    }
    
    public final void setShowAddHospitalDialog(boolean show) {
    }
    
    public final void setShowAddDoctorDialog(boolean show) {
    }
    
    public final void setWakeTime(@org.jetbrains.annotations.NotNull
    java.lang.String time) {
    }
    
    public final void setSleepTime(@org.jetbrains.annotations.NotNull
    java.lang.String time) {
    }
    
    public final void addNapTime(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.NapTime nap) {
    }
    
    public final void removeNapTime(int index) {
    }
    
    public final void addMealTime(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.MealTime meal) {
    }
    
    public final void removeMealTime(int index) {
    }
    
    public final void addMedicineTime(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.MedicineTime medicine) {
    }
    
    public final void removeMedicineTime(int index) {
    }
    
    public final void setEmergencyContactName(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
    
    public final void setEmergencyContactPhone(@org.jetbrains.annotations.NotNull
    java.lang.String phone) {
    }
    
    public final void setEmergencyContactRelationship(@org.jetbrains.annotations.NotNull
    java.lang.String relationship) {
    }
    
    public final void setSosNumber(@org.jetbrains.annotations.NotNull
    java.lang.String number) {
    }
    
    public final void setEnhancedSupportEnabled(boolean enabled) {
    }
    
    public final void setScreenPinningPermissionGranted(boolean granted) {
    }
    
    public final void savePatient(@org.jetbrains.annotations.NotNull
    java.lang.String familyMemberId, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onComplete) {
    }
}