package com.example.weatherapp.ui.main

import android.util.Log
import com.example.weatherapp.data.WeatherApi
import com.example.weatherapp.data.models.WeatherResponse
import com.example.weatherapp.util.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: WeatherApi
) : MainRepository {

    override suspend fun getWeatherData(city: String): Resource<WeatherResponse> {
        return try {
            val response = api.getWeatherData(city)
            val result = response.body()

            Log.e("ansRep", "response is  $response")
            Log.e("ansRep", "body is  $result")
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch(e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}