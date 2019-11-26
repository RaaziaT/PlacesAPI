package com.raaziat.accuweathersample.model.weather

data class DailyForecast(
    val Date: String,
    val Day: Day,
    val EpochDate: Int,
    val Link: String,
    val MobileLink: String,
    val Night: Night,
    val Sources: List<String>,
    val Temperature: Temperature
)