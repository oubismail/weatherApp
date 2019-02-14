package com.example.myweatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Forecast {
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null
    @SerializedName("main")
    @Expose
    var main: Main? = null
    @SerializedName("wind")
    @Expose
    var wind: Wind? = null
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("cod")
    @Expose
    var cod: Int = 0
}