package com.example.weatherapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            viewModel.getWeatherData(binding.edTextCityName.text.toString())
        }

        lifecycleScope.launchWhenCreated {
            viewModel.weatherReceive.collect { event ->
                when (event) {
                    is MainViewModel.WeatherEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.txtData.setTextColor(Color.BLACK)
                        binding.txtData.text = event.resultText
                    }
                    is MainViewModel.WeatherEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.txtData.setTextColor(Color.RED)
                        binding.txtData.text = event.errorText
                    }
                    is MainViewModel.WeatherEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }

            }
        }

    }
}