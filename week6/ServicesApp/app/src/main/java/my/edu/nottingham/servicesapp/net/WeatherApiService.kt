//package my.edu.nottingham.servicesapp.net
//
//import retrofit2.Response
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface WeatherApiService {
//    @GET("v1/forecast")
//    suspend fun getCurrent(
//        @Query("latitude") lat: Double,
//        @Query("longitude") lon: Double,
//        @Query("current") current: String = "temperature_2m,weather_code"
//    ): Response<WeatherResponse>
//}