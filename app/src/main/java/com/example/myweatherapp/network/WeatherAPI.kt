package com.example.myweatherapp.network

import com.example.myweatherapp.models.Forecast
import com.example.myweatherapp.models.ForecastList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    companion object {
        val API_KEY = "06e71e40fa78196f38609d0612d2352c"
    }

    @GET("group")
    fun loadForecastsList(@Query("id") citiesIds :String,@Query("units") units: String, @Query("appid") apiKey :String): Call<ForecastList>

    @GET("weather")
    fun loadForecastByLocation(@Query("lat") lat :Double, @Query("lon") lon :Double,@Query("units") units: String, @Query("appid") apiKey :String): Call<Forecast>
}