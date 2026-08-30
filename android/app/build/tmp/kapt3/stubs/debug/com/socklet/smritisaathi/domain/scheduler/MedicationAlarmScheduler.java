package com.socklet.smritisaathi.domain.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import com.socklet.smritisaathi.domain.model.Reminder;
import com.socklet.smritisaathi.receiver.ReminderBroadcastReceiver;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.Calendar;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u001c\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u000e\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/socklet/smritisaathi/domain/scheduler/MedicationAlarmScheduler;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "alarmManager", "Landroid/app/AlarmManager;", "cancelReminder", "", "reminderId", "", "parseTime", "Lkotlin/Pair;", "", "timeString", "scheduleReminder", "reminder", "Lcom/socklet/smritisaathi/domain/model/Reminder;", "app_debug"})
public final class MedicationAlarmScheduler {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable
    private final android.app.AlarmManager alarmManager = null;
    
    @javax.inject.Inject
    public MedicationAlarmScheduler(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    public final void scheduleReminder(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Reminder reminder) {
    }
    
    public final void cancelReminder(@org.jetbrains.annotations.NotNull
    java.lang.String reminderId) {
    }
    
    private final kotlin.Pair<java.lang.Integer, java.lang.Integer> parseTime(java.lang.String timeString) {
        return null;
    }
}