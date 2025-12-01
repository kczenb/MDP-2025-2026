package my.edu.nottingham.servicesapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            // For demo purposes, restart the foreground service after boot
            PersistentSyncService.start(context)
        }
    }
}