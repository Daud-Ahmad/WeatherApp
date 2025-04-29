package com.codesaaz.mvvm.model

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Current(
    val temp_c: Double,
    val feelslike_c: Double
)

data class Location(
    val name: String,
    val region: String,
    val country: String
)
