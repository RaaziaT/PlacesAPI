package com.raaziat.pagingexample.model.openweather

data class X(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)