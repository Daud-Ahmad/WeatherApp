package com.codesaaz.mvvm.data.repo

import com.codesaaz.mvvm.data.network.WeatherApiService
import com.codesaaz.mvvm.model.WeatherResponse
import retrofit2.Response
import javax.inject.Inject


class WeatherRepository @Inject constructor(
    private val api: WeatherApiService
)
{
    suspend fun getWeather(city: String, apiKey: String): Response<WeatherResponse> {
//        return APIClient.weatherApi.getWeather(city, apiKey)
        return  api.getWeather(city, apiKey)
    }
}