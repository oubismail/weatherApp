package com.example.myweatherapp

import android.app.Application
import com.example.myweatherapp.network.DaggerWeatherComponent
import com.example.myweatherapp.network.WeatherComponent

class WeatherApp : Application() {

    companion object {
        lateinit var toDataComponent: WeatherComponent

        fun getAppComponent(): WeatherComponent {
            return toDataComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        toDataComponent = DaggerWeatherComponent.builder().build()
    }
}