package my.edu.nottingham.servicesapp.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat.START_STICKY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import my.edu.nottingham.servicesapp.MainActivity
import my.edu.nottingham.servicesapp.R
import my.edu.nottingham.servicesapp.data.Prefs
import my.edu.nottingham.servicesapp.net.ApiClient


class PersistentSyncService : Service() {

    private val notifId = 1001
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        ensureChannel(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("SERVICE_DEBUG", "Service onStartCommand called")
        startForeground(notifId, buildNotification())

        scope.launch {
            val prefs = Prefs(this@PersistentSyncService)

            while (isActive) {
                try {
                    val weather = ApiClient.api.getWeather()

                    val summary = "Code: ${weather.current.weather_code}"
                    val temp = weather.current.temperature_2m
                    prefs.saveWeather(summary, temp)

                    Log.d("SERVICE_DEBUG", "Weather saved $summary, $temp°C")
                } catch (e: Exception) {
                    Log.e("SERVICE_DEBUG", "Weather error", e)
                }

                kotlinx.coroutines.delay(20000) // every 30 seconds for demo
            }
        }
        return START_STICKY // ask system to recreate after kill (intent not redelivered)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun buildNotification(): Notification {
        val openIntent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(
            this, 0, openIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_sync)
            .setContentTitle("Weather Sync Running")
            .setContentText("Foreground service active")
            .setOngoing(true)
            .setContentIntent(pi)
            .build()
    }

    companion object {
        fun start(context: Context) {
            val i = Intent(context, PersistentSyncService::class.java)
            context.startForegroundService(i)
        }
        fun stop(context: Context) {
            val i = Intent(context, PersistentSyncService::class.java)
            context.stopService(i)
        }
    }
}