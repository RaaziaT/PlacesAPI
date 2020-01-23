package com.raaziat.pagingexample.model.openweather

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<X>,
    val message: Int
)