package com.raaziat.pagingexample.network

import com.raaziat.accuweathersample.model.weather.Weather
import com.raaziat.pagingexample.model.openweather.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkInterfacesOpenWeather {

    @GET("data/2.5/forecast")
    fun getForecast(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?
    ): Deferred<Response<WeatherResponse>>
}