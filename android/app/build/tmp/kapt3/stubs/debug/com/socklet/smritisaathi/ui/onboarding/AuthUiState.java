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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u007f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0013J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u0083\u0001\u0010.\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010/\u001a\u00020\u00032\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u000202H\u00d6\u0001J\t\u00103\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0018R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0018R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017\u00a8\u00064"}, d2 = {"Lcom/socklet/smritisaathi/ui/onboarding/AuthUiState;", "", "isLoading", "", "error", "", "phoneNumber", "verificationId", "resendToken", "Lcom/google/firebase/auth/PhoneAuthProvider$ForceResendingToken;", "otpSent", "isLoggedIn", "currentUser", "Lcom/socklet/smritisaathi/domain/model/User;", "selectedRole", "Lcom/socklet/smritisaathi/domain/model/UserRole;", "pairedPatient", "Lcom/socklet/smritisaathi/domain/model/Patient;", "verificationComplete", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/google/firebase/auth/PhoneAuthProvider$ForceResendingToken;ZZLcom/socklet/smritisaathi/domain/model/User;Lcom/socklet/smritisaathi/domain/model/UserRole;Lcom/socklet/smritisaathi/domain/model/Patient;Z)V", "getCurrentUser", "()Lcom/socklet/smritisaathi/domain/model/User;", "getError", "()Ljava/lang/String;", "()Z", "getOtpSent", "getPairedPatient", "()Lcom/socklet/smritisaathi/domain/model/Patient;", "getPhoneNumber", "getResendToken", "()Lcom/google/firebase/auth/PhoneAuthProvider$ForceResendingToken;", "getSelectedRole", "()Lcom/socklet/smritisaathi/domain/model/UserRole;", "getVerificationComplete", "getVerificationId", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class AuthUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String error = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String phoneNumber = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String verificationId = null;
    @org.jetbrains.annotations.Nullable
    private final com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken resendToken = null;
    private final boolean otpSent = false;
    private final boolean isLoggedIn = false;
    @org.jetbrains.annotations.Nullable
    private final com.socklet.smritisaathi.domain.model.User currentUser = null;
    @org.jetbrains.annotations.Nullable
    private final com.socklet.smritisaathi.domain.model.UserRole selectedRole = null;
    @org.jetbrains.annotations.Nullable
    private final com.socklet.smritisaathi.domain.model.Patient pairedPatient = null;
    private final boolean verificationComplete = false;
    
    public AuthUiState(boolean isLoading, @org.jetbrains.annotations.Nullable
    java.lang.String error, @org.jetbrains.annotations.NotNull
    java.lang.String phoneNumber, @org.jetbrains.annotations.Nullable
    java.lang.String verificationId, @org.jetbrains.annotations.Nullable
    com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken resendToken, boolean otpSent, boolean isLoggedIn, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.User currentUser, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.UserRole selectedRole, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Patient pairedPatient, boolean verificationComplete) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPhoneNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getVerificationId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken getResendToken() {
        return null;
    }
    
    public final boolean getOtpSent() {
        return false;
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.User getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.UserRole getSelectedRole() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Patient getPairedPatient() {
        return null;
    }
    
    public final boolean getVerificationComplete() {
        return false;
    }
    
    public AuthUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Patient component10() {
        return null;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.User component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.UserRole component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.ui.onboarding.AuthUiState copy(boolean isLoading, @org.jetbrains.annotations.Nullable
    java.lang.String error, @org.jetbrains.annotations.NotNull
    java.lang.String phoneNumber, @org.jetbrains.annotations.Nullable
    java.lang.String verificationId, @org.jetbrains.annotations.Nullable
    com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken resendToken, boolean otpSent, boolean isLoggedIn, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.User currentUser, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.UserRole selectedRole, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Patient pairedPatient, boolean verificationComplete) {
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