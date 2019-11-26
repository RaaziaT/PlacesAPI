package com.raaziat.accuweathersample.model.weather

data class Weather(
    val DailyForecasts: List<DailyForecast>,
    val Headline: Headline
)