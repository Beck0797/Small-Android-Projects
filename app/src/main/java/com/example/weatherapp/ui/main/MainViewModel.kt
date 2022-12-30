package com.example.weatherapp.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.util.DispatcherProvider
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class WeatherEvent {
        class Success(val resultText: String): WeatherEvent()
        class Failure(val errorText: String): WeatherEvent()
        object Loading : WeatherEvent()
        object Empty : WeatherEvent()
    }

    private val _weatherReceive = MutableStateFlow<WeatherEvent>(WeatherEvent.Empty)
    val weatherReceive: StateFlow<WeatherEvent> = _weatherReceive

    fun getWeatherData(cityName: String) {
        if (cityName.trim().isEmpty()) {
            _weatherReceive.value = WeatherEvent.Failure("Not a valid city Name")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _weatherReceive.value = WeatherEvent.Loading
            when(val weatherResponse = repository.getWeatherData(cityName)) {

                is Resource.Error -> _weatherReceive.value = WeatherEvent.Failure(weatherResponse.message!!)
                is Resource.Success -> {
                    Log.e("ansRep", "response is  ${weatherResponse.toString()}")
                    val weatherInfo = "Location: ${weatherResponse.data!!.location.name}\n" +
                            "Time: ${weatherResponse.data!!.location.localtime}\n" +
                            "Temperature: ${weatherResponse.data!!.current.temp_c} ${weatherResponse.data!!.current.condition.text}"

                    _weatherReceive.value = WeatherEvent.Success(weatherInfo)

                }
            }
        }
    }


}