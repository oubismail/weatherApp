package com.example.myweatherapp.network

import com.example.myweatherapp.models.Forecast
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    companion object {
        val API_KEY = "06e71e40fa78196f38609d0612d2352c"
    }

    @GET("weather")
    fun loadForecasts(@Query("q") cityName :String, @Query("appid") apiKey :String): Call<Forecast>
}