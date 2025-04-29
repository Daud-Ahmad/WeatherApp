package com.codesaaz.mvvm.data.network

import com.codesaaz.mvvm.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService
{
    @GET("current.json")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String
    ): Response<WeatherResponse>
}