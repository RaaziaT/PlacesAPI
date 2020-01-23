package com.raaziat.pagingexample.model.openweather

data class City(
    val coord: Coord,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)