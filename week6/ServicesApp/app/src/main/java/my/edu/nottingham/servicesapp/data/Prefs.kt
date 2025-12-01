package my.edu.nottingham.servicesapp.data

import android.content.Context
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.core.content.edit
import java.text.DateFormat

class Prefs(context: Context) {
    private val prefs = context.getSharedPreferences("weather", Context.MODE_PRIVATE)

    fun saveWeather(summary: String, temp: Double) {
        prefs.edit {
            putString("summary", summary)
                .putFloat("temp", temp.toFloat())
                .putLong("updated", System.currentTimeMillis())
        }
    }

    fun lastSummary() = prefs.getString("summary", "No data yet") ?: "No data yet"
    fun lastTemp() = prefs.getFloat("temp", Float.NaN).toDouble()
    fun lastUpdated(): String? = prefs.getLong("updated", 0L).let { if (it == 0L) "—" else DateFormat.getDateTimeInstance().format(it) }
}