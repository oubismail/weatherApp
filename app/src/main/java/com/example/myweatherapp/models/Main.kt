package com.example.myweatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Main {
    @SerializedName("temp")
    @Expose
    val temp: Double = 0.toDouble()
    @SerializedName("pressure")
    @Expose
    val pressure: Int = 0
    @SerializedName("humidity")
    @Expose
    val humidity: Int = 0
    @SerializedName("temp_min")
    @Expose
    val tempMin: Double = 0.toDouble()
    @SerializedName("temp_max")
    @Expose
    val tempMax: Double = 0.toDouble()
}