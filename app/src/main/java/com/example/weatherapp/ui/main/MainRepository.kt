package com.example.weatherapp.ui.main

import com.example.weatherapp.data.models.WeatherResponse
import com.example.weatherapp.util.Resource

interface MainRepository {

    suspend fun getWeatherData(city: String): Resource<WeatherResponse>
}