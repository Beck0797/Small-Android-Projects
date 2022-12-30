package com.example.weatherapp.data

import com.example.weatherapp.data.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    companion object{
        const val BASE_URL = "http://api.weatherapi.com/"
        const val CLIENT_ID = "032c9f96368448adbe194428222912"
    }

//    @Headers("Accept-Version: v1", "Authorization: key $CLIENT_ID")
    @Headers("key: $CLIENT_ID")
    @GET("v1/current.json")
    suspend fun getWeatherData(
        @Query("q") query : String,
    ): Response<WeatherResponse>
}