package com.example.myweatherapp.network

import com.example.myweatherapp.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    val API_KEY = "06e71e40fa78196f38609d0612d2352c"
    val BASE_PATH = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun weatherAPIClient() : WeatherAPI {

        val httpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(WeatherAPI::class.java)
    }
}