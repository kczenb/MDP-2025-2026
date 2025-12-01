package my.edu.nottingham.servicesapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

const val CHANNEL_ID = "persistent_sync_channel"

fun ensureChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val ch = NotificationChannel(
            CHANNEL_ID,
            "Weather Sync",
            NotificationManager.IMPORTANCE_LOW
        ).apply { description = "Shows the foreground service status" }
        val nm = context.getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(ch)
    }
}