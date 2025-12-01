package my.edu.nottingham.vulnerablemobileapp

import android.app.Service
import android.content.Intent
import android.os.IBinder

class VulnerableService : Service() {

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Vulnerable: Command injection possibility using Kotlin scope functions
        intent?.extras?.getString("command")?.let { command ->
            // No validation before potential execution
            // In real vulnerable code, this might be: Runtime.getRuntime().exec(command)
        }

        return START_STICKY
    }
}