package com.codesaaz.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codesaaz.mvvm.model.WeatherResponse
import com.codesaaz.mvvm.data.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewmodel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel()
{
//    private val repository = WeatherRepository()

    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> get() = _weatherData

    private val _error = MutableLiveData<String>()
    val error:LiveData<String> get() = _error

    private val apiKey = "53253d77927f4cf4b80110223252303"

    fun fetchWeather(city: String){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                repository.getWeather(city, apiKey)
            }
            if(result.isSuccessful){
                _weatherData.value = result.body()
            }
            else{
                _error.value = "Error: ${result.message()}"
            }
        }
    }
}