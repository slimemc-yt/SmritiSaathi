package com.socklet.smritisaathi.data.seeder;

import com.google.firebase.firestore.FirebaseFirestore;
import com.socklet.smritisaathi.domain.model.Doctor;
import com.socklet.smritisaathi.domain.model.Hospital;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Utility class to seed initial data for doctors and hospitals
 * This should be called once when the app is first set up
 */
@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u000e\u0010\n\u001a\u00020\u000bH\u0082@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u000bH\u0082@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\u000e\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/socklet/smritisaathi/data/seeder/DatabaseSeeder;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "(Lcom/google/firebase/firestore/FirebaseFirestore;)V", "getDoctorName", "", "index", "", "specialty", "seedDoctors", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "seedHospitals", "seedInitialData", "app_debug"})
public final class DatabaseSeeder {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    
    @javax.inject.Inject
    public DatabaseSeeder(@org.jetbrains.annotations.NotNull
    com.google.firebase.firestore.FirebaseFirestore firestore) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object seedInitialData(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object seedHospitals(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object seedDoctors(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String getDoctorName(int index, java.lang.String specialty) {
        return null;
    }
}