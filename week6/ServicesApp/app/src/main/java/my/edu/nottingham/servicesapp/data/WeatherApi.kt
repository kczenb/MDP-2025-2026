package my.edu.nottingham.servicesapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double = 51.5072,
        @Query("longitude") lon: Double = -0.1276,
        @Query("current") current: String = "temperature_2m,weather_code"
    ): WeatherResponse
}

data class WeatherResponse(
    val current: CurrentWeather
)

data class CurrentWeather(
    val temperature_2m: Double,
    val weather_code: Int
)