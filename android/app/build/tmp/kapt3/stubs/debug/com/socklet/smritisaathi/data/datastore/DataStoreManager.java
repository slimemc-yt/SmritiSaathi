package com.socklet.smritisaathi.data.datastore;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.*;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u000f\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u001a\u0010\u0018\u001a\u00020\u00162\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\rH\u0086@\u00a2\u0006\u0002\u0010\u001aJ(\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\r2\b\b\u0002\u0010\u001e\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u001aR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\tR\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\tR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\tR\u0019\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\t\u00a8\u0006%"}, d2 = {"Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dementiaTierFlow", "Lkotlinx/coroutines/flow/Flow;", "", "getDementiaTierFlow", "()Lkotlinx/coroutines/flow/Flow;", "isDevicePairedFlow", "", "pairedPatientIdFlow", "", "getPairedPatientIdFlow", "pairedPatientNameFlow", "getPairedPatientNameFlow", "preferredLanguageFlow", "getPreferredLanguageFlow", "userRoleFlow", "getUserRoleFlow", "clearSession", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearUserSession", "uid", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "savePatientPairing", "patientId", "patientName", "dementiaTier", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "savePreferredLanguage", "langCode", "saveUserRole", "role", "Companion", "app_debug"})
public final class DataStoreManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_PAIRED_PATIENT_ID = null;
    @org.jetbrains.annotations.NotNull
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_PAIRED_PATIENT_NAME = null;
    @org.jetbrains.annotations.NotNull
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_USER_ROLE = null;
    @org.jetbrains.annotations.NotNull
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> KEY_PREFERRED_LANGUAGE = null;
    @org.jetbrains.annotations.NotNull
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Integer> KEY_DEMENTIA_TIER = null;
    @org.jetbrains.annotations.NotNull
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> KEY_IS_DEVICE_PAIRED = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.String> pairedPatientIdFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.String> pairedPatientNameFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.String> userRoleFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.String> preferredLanguageFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> dementiaTierFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isDevicePairedFlow = null;
    @org.jetbrains.annotations.NotNull
    public static final com.socklet.smritisaathi.data.datastore.DataStoreManager.Companion Companion = null;
    
    @javax.inject.Inject
    public DataStoreManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getPairedPatientIdFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getPairedPatientNameFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getUserRoleFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getPreferredLanguageFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getDementiaTierFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> isDevicePairedFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object savePatientPairing(@org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    java.lang.String patientName, int dementiaTier, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object saveUserRole(@org.jetbrains.annotations.NotNull
    java.lang.String role, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object savePreferredLanguage(@org.jetbrains.annotations.NotNull
    java.lang.String langCode, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object clearSession(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object clearUserSession(@org.jetbrains.annotations.Nullable
    java.lang.String uid, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"Lcom/socklet/smritisaathi/data/datastore/DataStoreManager$Companion;", "", "()V", "KEY_DEMENTIA_TIER", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "getKEY_DEMENTIA_TIER", "()Landroidx/datastore/preferences/core/Preferences$Key;", "KEY_IS_DEVICE_PAIRED", "", "getKEY_IS_DEVICE_PAIRED", "KEY_PAIRED_PATIENT_ID", "", "getKEY_PAIRED_PATIENT_ID", "KEY_PAIRED_PATIENT_NAME", "getKEY_PAIRED_PATIENT_NAME", "KEY_PREFERRED_LANGUAGE", "getKEY_PREFERRED_LANGUAGE", "KEY_USER_ROLE", "getKEY_USER_ROLE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> getKEY_PAIRED_PATIENT_ID() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> getKEY_PAIRED_PATIENT_NAME() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> getKEY_USER_ROLE() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> getKEY_PREFERRED_LANGUAGE() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Integer> getKEY_DEMENTIA_TIER() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getKEY_IS_DEVICE_PAIRED() {
            return null;
        }
    }
}