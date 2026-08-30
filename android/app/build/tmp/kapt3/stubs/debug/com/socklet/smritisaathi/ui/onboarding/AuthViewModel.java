package com.socklet.smritisaathi.ui.onboarding;

import android.app.Activity;
import android.content.Context;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.socklet.smritisaathi.data.datastore.DataStoreManager;
import com.socklet.smritisaathi.domain.model.Patient;
import com.socklet.smritisaathi.domain.model.User;
import com.socklet.smritisaathi.domain.model.UserRole;
import com.socklet.smritisaathi.domain.repository.AuthRepository;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0006\u0010\u0014\u001a\u00020\u0013J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0016H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J&\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001eJ6\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\u001c2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00130\"2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00130\"J6\u0010%\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\'2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00130\"2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00130\"J4\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\u000e\b\u0002\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00130,2\u0014\b\u0002\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00130\"J\u001c\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001a0/2\u0006\u00100\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001eJ<\u00101\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\u0006\u00102\u001a\u00020\u001c2\u000e\b\u0002\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00130,2\u0014\b\u0002\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00130\"J\u000e\u00103\u001a\u00020\u00132\u0006\u00104\u001a\u00020\u001cJ\u000e\u00105\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\'J@\u00106\u001a\u00020\u00132\u0006\u00107\u001a\u00020\u001c2\b\b\u0002\u0010&\u001a\u00020\'2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00130\"2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00130\"J\u0006\u00108\u001a\u00020\u0013J.\u00109\u001a\u00020\u00132\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00130\"2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00130\"J\u000e\u0010:\u001a\u00020\u00132\u0006\u0010;\u001a\u00020\u001aJ<\u0010<\u001a\u00020\u00132\u0006\u0010=\u001a\u00020>2\u0014\b\u0002\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00130\"2\u0014\b\u0002\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00130\"H\u0002J:\u0010?\u001a\u00020\u00132\u0006\u0010@\u001a\u00020\u001c2\u0014\b\u0002\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00130\"2\u0014\b\u0002\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00130\"R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006A"}, d2 = {"Lcom/socklet/smritisaathi/ui/onboarding/AuthViewModel;", "Landroidx/lifecycle/ViewModel;", "authRepository", "Lcom/socklet/smritisaathi/domain/repository/AuthRepository;", "patientRepository", "Lcom/socklet/smritisaathi/domain/repository/PatientRepository;", "dataStoreManager", "Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;", "(Lcom/socklet/smritisaathi/domain/repository/AuthRepository;Lcom/socklet/smritisaathi/domain/repository/PatientRepository;Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/socklet/smritisaathi/ui/onboarding/AuthUiState;", "exceptionHandler", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "checkCurrentUser", "", "clearError", "deleteAccount", "Lkotlin/Result;", "deleteAccount-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadUserProfile", "Lcom/socklet/smritisaathi/domain/model/User;", "userId", "", "loadUserProfile-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pairPatientDevice", "pairingCode", "onSuccess", "Lkotlin/Function1;", "Lcom/socklet/smritisaathi/domain/model/Patient;", "onError", "quickDemoLogin", "role", "Lcom/socklet/smritisaathi/domain/model/UserRole;", "resendOtp", "context", "Landroid/content/Context;", "onCodeSent", "Lkotlin/Function0;", "onVerificationFailed", "searchDoctors", "", "query", "sendOtp", "phoneNumber", "setPhoneNumber", "phone", "setSelectedRole", "signInWithGoogle", "idToken", "signOut", "startDemoPatientSession", "syncDoctorDirectory", "user", "verifyCredential", "credential", "Lcom/google/firebase/auth/PhoneAuthCredential;", "verifyOtp", "code", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class AuthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.repository.PatientRepository patientRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.data.datastore.DataStoreManager dataStoreManager = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineExceptionHandler exceptionHandler = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.socklet.smritisaathi.ui.onboarding.AuthUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.socklet.smritisaathi.ui.onboarding.AuthUiState> uiState = null;
    
    @javax.inject.Inject
    public AuthViewModel(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.repository.PatientRepository patientRepository, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.data.datastore.DataStoreManager dataStoreManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.socklet.smritisaathi.ui.onboarding.AuthUiState> getUiState() {
        return null;
    }
    
    private final void checkCurrentUser() {
    }
    
    public final void setSelectedRole(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.UserRole role) {
    }
    
    public final void setPhoneNumber(@org.jetbrains.annotations.NotNull
    java.lang.String phone) {
    }
    
    /**
     * Sign In using Google ID token
     */
    public final void signInWithGoogle(@org.jetbrains.annotations.NotNull
    java.lang.String idToken, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.UserRole role, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.User, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    /**
     * Pair Patient device using 6-digit code
     */
    public final void pairPatientDevice(@org.jetbrains.annotations.NotNull
    java.lang.String pairingCode, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.Patient, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    /**
     * Start Instant Demo Patient Session (for fast emulator/hackathon judging testing)
     */
    public final void startDemoPatientSession(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.Patient, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    /**
     * Quick Demo Sign In for Family / Doctor roles
     */
    public final void quickDemoLogin(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.UserRole role, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.User, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    /**
     * Send OTP to phone number (Secondary fallback)
     */
    public final void sendOtp(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String phoneNumber, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onCodeSent, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onVerificationFailed) {
    }
    
    /**
     * Resend OTP to phone number (Secondary fallback)
     */
    public final void resendOtp(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onCodeSent, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onVerificationFailed) {
    }
    
    public final void verifyOtp(@org.jetbrains.annotations.NotNull
    java.lang.String code, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.User, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError) {
    }
    
    private final void verifyCredential(com.google.firebase.auth.PhoneAuthCredential credential, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onError, kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.User, kotlin.Unit> onSuccess) {
    }
    
    public final void signOut() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object searchDoctors(@org.jetbrains.annotations.NotNull
    java.lang.String query, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.socklet.smritisaathi.domain.model.User>> $completion) {
        return null;
    }
    
    public final void syncDoctorDirectory(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.User user) {
    }
    
    public final void clearError() {
    }
}