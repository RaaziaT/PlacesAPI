package com.raaziat.pagingexample.ui.weather

import com.raaziat.accuweathersample.model.weather.DailyForecast
import com.raaziat.pagingexample.network.BaseRepository
import com.raaziat.pagingexample.network.NetworkInterfacesWeather

class WeatherRepository(private val api: NetworkInterfacesWeather) : BaseRepository(){

    suspend fun getWeather(q:String) :MutableList<DailyForecast>?{

        //safeApiCall is defined in BaseRepository.kt
        val locationResponse = safeApiCall(
            call = {api.getLocation(q).await()},
            errorMessage = "Error Fetching Popular Weather"
        )

        val locationKey = locationResponse?.Key;

        val weatherResponse = safeApiCall(
            call = {api.getWeather(locationKey!!.toInt()).await()},
            errorMessage = "Error Fetching Popular Weather"
        )

        return weatherResponse?.DailyForecasts?.toMutableList();
    }
}