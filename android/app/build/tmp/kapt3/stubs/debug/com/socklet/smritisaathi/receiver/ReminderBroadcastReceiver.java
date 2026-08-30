package com.socklet.smritisaathi.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.socklet.smritisaathi.MainActivity;
import com.socklet.smritisaathi.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J(\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0002\u00a8\u0006\u000f"}, d2 = {"Lcom/socklet/smritisaathi/receiver/ReminderBroadcastReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "showHeadsUpNotification", "reminderId", "", "title", "medicine", "Companion", "app_debug"})
public final class ReminderBroadcastReceiver extends android.content.BroadcastReceiver {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_MEDICATION_ALARM = "com.socklet.smritisaathi.ACTION_MEDICATION_ALARM";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_REMINDER_ID = "extra_reminder_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_REMINDER_TITLE = "extra_reminder_title";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_MEDICINE_NAME = "extra_medicine_name";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_PATIENT_ID = "extra_patient_id";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CHANNEL_ID = "smritisaathi_medication_channel";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CHANNEL_NAME = "Medication & Daily Care Reminders";
    @org.jetbrains.annotations.NotNull
    public static final com.socklet.smritisaathi.receiver.ReminderBroadcastReceiver.Companion Companion = null;
    
    public ReminderBroadcastReceiver() {
        super();
    }
    
    @java.lang.Override
    public void onReceive(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.content.Intent intent) {
    }
    
    private final void showHeadsUpNotification(android.content.Context context, java.lang.String reminderId, java.lang.String title, java.lang.String medicine) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/socklet/smritisaathi/receiver/ReminderBroadcastReceiver$Companion;", "", "()V", "ACTION_MEDICATION_ALARM", "", "CHANNEL_ID", "CHANNEL_NAME", "EXTRA_MEDICINE_NAME", "EXTRA_PATIENT_ID", "EXTRA_REMINDER_ID", "EXTRA_REMINDER_TITLE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}