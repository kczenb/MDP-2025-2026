package my.edu.nottingham.servicesapp.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import my.edu.nottingham.servicesapp.data.Prefs
import my.edu.nottingham.servicesapp.net.ApiClient
import java.io.IOException

class WeatherSyncWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        return try {
            val weather = ApiClient.api.getWeather(3.14, 101.69)

            val temp = weather.current.temperature_2m
            val summary = "Weather code: ${weather.current.weather_code}"

            Prefs(applicationContext).saveWeather(summary, temp)

            Result.success(
                Data.Builder()
                    .putString("summary", summary)
                    .putDouble("temp", temp)
                    .build()
            )
        } catch (io: IOException) {
            Result.retry()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}