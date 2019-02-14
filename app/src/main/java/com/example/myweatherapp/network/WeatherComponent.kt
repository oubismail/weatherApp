package com.example.myweatherapp.network

import com.example.myweatherapp.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface WeatherComponent {

    fun inject(target: MainActivity)

}