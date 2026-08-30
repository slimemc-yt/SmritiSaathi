package com.socklet.smritisaathi.domain.model;

import android.net.Uri;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BI\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006H\u00c6\u0003JM\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006H\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012\u00a8\u0006 "}, d2 = {"Lcom/socklet/smritisaathi/domain/model/DailyRoutine;", "", "wakeTime", "", "sleepTime", "napTimes", "", "Lcom/socklet/smritisaathi/domain/model/NapTime;", "mealTimes", "Lcom/socklet/smritisaathi/domain/model/MealTime;", "medicineTimes", "Lcom/socklet/smritisaathi/domain/model/MedicineTime;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getMealTimes", "()Ljava/util/List;", "getMedicineTimes", "getNapTimes", "getSleepTime", "()Ljava/lang/String;", "getWakeTime", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class DailyRoutine {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String wakeTime = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String sleepTime = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.NapTime> napTimes = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.MealTime> mealTimes = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> medicineTimes = null;
    
    public DailyRoutine(@org.jetbrains.annotations.NotNull
    java.lang.String wakeTime, @org.jetbrains.annotations.NotNull
    java.lang.String sleepTime, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.NapTime> napTimes, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MealTime> mealTimes, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> medicineTimes) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWakeTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSleepTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.NapTime> getNapTimes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MealTime> getMealTimes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> getMedicineTimes() {
        return null;
    }
    
    public DailyRoutine() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.NapTime> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MealTime> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DailyRoutine copy(@org.jetbrains.annotations.NotNull
    java.lang.String wakeTime, @org.jetbrains.annotations.NotNull
    java.lang.String sleepTime, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.NapTime> napTimes, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MealTime> mealTimes, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> medicineTimes) {
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