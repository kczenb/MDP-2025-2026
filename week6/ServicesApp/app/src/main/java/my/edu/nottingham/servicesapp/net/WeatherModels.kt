package my.edu.nottingham.servicesapp.net

data class WeatherResponse(
    val latitude: Double?,
    val longitude: Double?,
    val current: Current?
)

data class Current(
    val temperature_2m: Double?,
    val weather_code: Int?,
    val time: String?
)