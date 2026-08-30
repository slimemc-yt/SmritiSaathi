package com.socklet.smritisaathi.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.socklet.smritisaathi.MainActivity
import com.socklet.smritisaathi.R

class ReminderBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_MEDICATION_ALARM = "com.socklet.smritisaathi.ACTION_MEDICATION_ALARM"
        const val EXTRA_REMINDER_ID = "extra_reminder_id"
        const val EXTRA_REMINDER_TITLE = "extra_reminder_title"
        const val EXTRA_MEDICINE_NAME = "extra_medicine_name"
        const val EXTRA_PATIENT_ID = "extra_patient_id"

        const val CHANNEL_ID = "smritisaathi_medication_channel"
        const val CHANNEL_NAME = "Medication & Daily Care Reminders"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Phone restarted - alarms are automatically refreshed when app boots
            return
        }

        val reminderId = intent.getStringExtra(EXTRA_REMINDER_ID) ?: "med_reminder"
        val title = intent.getStringExtra(EXTRA_REMINDER_TITLE) ?: "Medication Reminder"
        val medicine = intent.getStringExtra(EXTRA_MEDICINE_NAME) ?: "Time for your scheduled medicine"

        showHeadsUpNotification(context, reminderId, title, medicine)
    }

    private fun showHeadsUpNotification(
        context: Context,
        reminderId: String,
        title: String,
        medicine: String
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Urgent medication notifications and routine checks for elderly dementia care"
                enableVibration(true)
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("OPEN_VIEW", "MEDICATION_PROMPT")
            putExtra("REMINDER_ID", reminderId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            reminderId.hashCode(),
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("💊 $title")
            .setContentText(medicine)
            .setStyle(NotificationCompat.BigTextStyle().bigText("$medicine\nTap to open and mark as Taken ✅ or Snooze ⏰"))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setSound(alarmSound)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)

        notificationManager.notify(reminderId.hashCode(), builder.build())
    }
}
