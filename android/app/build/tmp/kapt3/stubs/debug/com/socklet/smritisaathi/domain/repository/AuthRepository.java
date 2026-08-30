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

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ0\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J\b\u0010\u0016\u001a\u00020\u000fH\u0002J\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\b\u0010\u0019\u001a\u0004\u0018\u00010\u000fJ&\u0010\u001a\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u0015J&\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u0015J\u0006\u0010\u001f\u001a\u00020 J,\u0010!\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\"\u0010\u0012J\u000e\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000fJ$\u0010%\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010&\u001a\u00020\u0018H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\'\u0010(J*\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180*0\f2\u0006\u0010+\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b,\u0010\u0015Jr\u0010-\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010.\u001a\u00020\u000f2\u0006\u0010/\u001a\u0002002\u0018\u00101\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020\r022\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\r052\u0016\u00107\u001a\u0012\u0012\b\u0012\u000608j\u0002`9\u0012\u0004\u0012\u00020\r05H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b:\u0010;J\u001c\u0010<\u001a\b\u0012\u0004\u0012\u00020\u000f0\fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b=\u0010>J$\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00180\f2\u0006\u0010@\u001a\u000206H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bA\u0010BJ.\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00180\f2\u0006\u0010D\u001a\u00020E2\b\b\u0002\u0010.\u001a\u00020\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bF\u0010GJ,\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00180\f2\u0006\u0010I\u001a\u00020\u000f2\u0006\u0010J\u001a\u00020EH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bK\u0010LJ\u0006\u0010M\u001a\u00020\rJ\u000e\u0010N\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010>J\u0016\u0010O\u001a\u00020\r2\u0006\u0010&\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010(J,\u0010P\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010D\u001a\u00020EH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bQ\u0010LR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006R"}, d2 = {"Lcom/socklet/smritisaathi/domain/repository/AuthRepository;", "", "appContext", "Landroid/content/Context;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "(Landroid/content/Context;Lcom/google/firebase/auth/FirebaseAuth;Lcom/google/firebase/firestore/FirebaseFirestore;)V", "usersCollection", "Lcom/google/firebase/firestore/CollectionReference;", "completeOnboarding", "Lkotlin/Result;", "", "userId", "", "patientId", "completeOnboarding-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAccount", "deleteAccount-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateDoctorCode", "getCurrentUser", "Lcom/socklet/smritisaathi/domain/model/User;", "getCurrentUserId", "getDoctorByCode", "code", "getDoctorByCode-gIAlu-s", "getUser", "getUser-gIAlu-s", "isLoggedIn", "", "linkPatientToUser", "linkPatientToUser-0E7RQCE", "normalizeDoctorCode", "input", "saveUser", "user", "saveUser-gIAlu-s", "(Lcom/socklet/smritisaathi/domain/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchDoctors", "", "query", "searchDoctors-gIAlu-s", "sendOtp", "phoneNumber", "options", "Lcom/google/firebase/auth/PhoneAuthOptions$Builder;", "onCodeSent", "Lkotlin/Function2;", "Lcom/google/firebase/auth/PhoneAuthProvider$ForceResendingToken;", "onVerificationCompleted", "Lkotlin/Function1;", "Lcom/google/firebase/auth/PhoneAuthCredential;", "onVerificationFailed", "Ljava/lang/Exception;", "Lkotlin/Exception;", "sendOtp-hUnOzRk", "(Ljava/lang/String;Lcom/google/firebase/auth/PhoneAuthOptions$Builder;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInAnonymously", "signInAnonymously-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithCredential", "credential", "signInWithCredential-gIAlu-s", "(Lcom/google/firebase/auth/PhoneAuthCredential;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithDemoAccount", "role", "Lcom/socklet/smritisaathi/domain/model/UserRole;", "signInWithDemoAccount-0E7RQCE", "(Lcom/socklet/smritisaathi/domain/model/UserRole;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithGoogle", "idToken", "selectedRole", "signInWithGoogle-0E7RQCE", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/UserRole;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signOut", "syncAllExistingDoctors", "syncDoctorToPublicDirectory", "updateUserRole", "updateUserRole-0E7RQCE", "app_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.CollectionReference usersCollection = null;
    
    @javax.inject.Inject
    public AuthRepository(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context appContext, @org.jetbrains.annotations.NotNull
    com.google.firebase.auth.FirebaseAuth auth, @org.jetbrains.annotations.NotNull
    com.google.firebase.firestore.FirebaseFirestore firestore) {
        super();
    }
    
    /**
     * Generate unique permanent Doctor Code (e.g. "DR-8821")
     */
    private final java.lang.String generateDoctorCode() {
        return null;
    }
    
    /**
     * Helper for normalizing doctor codes into standard formats:
     * e.g. "8821" -> "DR-8821", "dr-8821" -> "DR-8821", "DR8821" -> "DR-8821"
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String normalizeDoctorCode(@org.jetbrains.annotations.NotNull
    java.lang.String input) {
        return null;
    }
    
    /**
     * Synchronize a registered doctor's profile to public Firestore collections
     * (/doctors and /doctorCodes) so they are immediately and publicly searchable
     * by all family members and caregivers regardless of user-specific collection rules.
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object syncDoctorToPublicDirectory(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.User user, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Migration & Auto-Sync: Scans existing accounts and public collections to ensure
     * that all previously registered Doctors (even those created before this update)
     * are assigned unique permanent Doctor Codes and published to /doctors and /doctorCodes.
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object syncAllExistingDoctors(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get current logged in user
     */
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.User getCurrentUser() {
        return null;
    }
    
    /**
     * Check if user is logged in
     */
    public final boolean isLoggedIn() {
        return false;
    }
    
    /**
     * Get current user ID
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getCurrentUserId() {
        return null;
    }
    
    /**
     * Sign out. Clears the Firebase session AND the Google Sign-In client state so
     * the next sign-in shows the account chooser instead of silently reusing the
     * previously authorized account. Cloud account/data is never deleted.
     */
    public final void signOut() {
    }
}