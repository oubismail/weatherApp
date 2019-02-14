package com.example.myweatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Wind {
    @SerializedName("speed")
    @Expose
    val speed: Double = 0.toDouble()
    @SerializedName("deg")
    @Expose
    val deg: Int = 0
}