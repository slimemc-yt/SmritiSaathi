package com.socklet.smritisaathi.domain.repository;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.socklet.smritisaathi.domain.model.User;
import com.socklet.smritisaathi.domain.model.UserRole;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.Flow;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/socklet/smritisaathi/domain/repository/OtpSendResult;", "", "verificationId", "", "resendToken", "Lcom/google/firebase/auth/PhoneAuthProvider$ForceResendingToken;", "(Ljava/lang/String;Lcom/google/firebase/auth/PhoneAuthProvider$ForceResendingToken;)V", "getResendToken", "()Lcom/google/firebase/auth/PhoneAuthProvider$ForceResendingToken;", "getVerificationId", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class OtpSendResult {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String verificationId = null;
    @org.jetbrains.annotations.Nullable
    private final com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken resendToken = null;
    
    public OtpSendResult(@org.jetbrains.annotations.NotNull
    java.lang.String verificationId, @org.jetbrains.annotations.Nullable
    com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken resendToken) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getVerificationId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken getResendToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.repository.OtpSendResult copy(@org.jetbrains.annotations.NotNull
    java.lang.String verificationId, @org.jetbrains.annotations.Nullable
    com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken resendToken) {
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