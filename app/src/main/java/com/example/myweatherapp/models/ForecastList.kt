package com.example.myweatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ForecastList {
    @SerializedName("cnt")
    @Expose
    var count: Int = 0
    @SerializedName("list")
    @Expose
    var list: List<Forecast>? = null
}