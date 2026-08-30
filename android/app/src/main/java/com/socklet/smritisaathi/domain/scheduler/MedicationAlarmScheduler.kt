package com.socklet.smritisaathi.domain.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.socklet.smritisaathi.domain.model.Reminder
import com.socklet.smritisaathi.receiver.ReminderBroadcastReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicationAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    fun scheduleReminder(reminder: Reminder) {
        if (alarmManager == null) return

        try {
            val (hour, minute) = parseTime(reminder.scheduledTime)
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                // If the scheduled time has already passed today, schedule for tomorrow
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }

            val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
                action = ReminderBroadcastReceiver.ACTION_MEDICATION_ALARM
                putExtra(ReminderBroadcastReceiver.EXTRA_REMINDER_ID, reminder.id)
                putExtra(ReminderBroadcastReceiver.EXTRA_REMINDER_TITLE, reminder.title)
                putExtra(ReminderBroadcastReceiver.EXTRA_MEDICINE_NAME, reminder.medicineName)
                putExtra(ReminderBroadcastReceiver.EXTRA_PATIENT_ID, reminder.patientId)
            }

            val requestCode = reminder.id.hashCode()
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }

            Log.d("MedicationAlarm", "Scheduled reminder '${reminder.title}' for ${calendar.time}")
        } catch (e: Exception) {
            Log.e("MedicationAlarm", "Failed to schedule alarm for reminder ${reminder.id}", e)
        }
    }

    fun cancelReminder(reminderId: String) {
        try {
            val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
                action = ReminderBroadcastReceiver.ACTION_MEDICATION_ALARM
            }
            val requestCode = reminderId.hashCode()
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            if (pendingIntent != null) {
                alarmManager?.cancel(pendingIntent)
                pendingIntent.cancel()
            }
        } catch (e: Exception) {
            Log.e("MedicationAlarm", "Failed to cancel alarm for reminder $reminderId", e)
        }
    }

    private fun parseTime(timeString: String): Pair<Int, Int> {
        return try {
            // Check for format like "08:30 AM" or "8:30 PM" or "14:00"
            val clean = timeString.trim().uppercase()
            if (clean.contains("AM") || clean.contains("PM")) {
                val isPm = clean.contains("PM")
                val parts = clean.replace("AM", "").replace("PM", "").trim().split(":")
                var hour = parts[0].trim().toInt()
                val minute = if (parts.size > 1) parts[1].trim().toInt() else 0
                if (isPm && hour < 12) hour += 12
                if (!isPm && hour == 12) hour = 0
                Pair(hour, minute)
            } else {
                val parts = clean.split(":")
                val hour = parts[0].trim().toInt()
                val minute = if (parts.size > 1) parts[1].trim().toInt() else 0
                Pair(hour, minute)
            }
        } catch (e: Exception) {
            Pair(8, 30) // Default fallback 8:30 AM
        }
    }
}
