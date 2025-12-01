package my.edu.nottingham.servicesapp.net

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import my.edu.nottingham.servicesapp.data.WeatherApi
import okhttp3.Dns
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.Inet4Address
import java.util.concurrent.TimeUnit

object ApiClient {

    object IPv4Dns : Dns {
        override fun lookup(hostname: String): List<java.net.InetAddress> {
            return Dns.SYSTEM.lookup(hostname).filter { it is Inet4Address }
        }
    }

    private val client = OkHttpClient.Builder()
        .dns(IPv4Dns)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(WeatherApi::class.java)
    }
}