package com.raaziat.pagingexample.ui.weather

import com.raaziat.accuweathersample.model.weather.DailyForecast
import com.raaziat.pagingexample.network.ApiFactory
import com.raaziat.pagingexample.network.BaseRepository

class WeatherRepository() : BaseRepository(){

    val api = ApiFactory.networkInterfacesWeather

    suspend fun getWeather(q:String) :MutableList<DailyForecast>?{

        //safeApiCall is defined in BaseRepository.kt (https://gist.github.com/navi25/67176730f5595b3f1fb5095062a92f15)
        val locationResponse = safeApiCall(
            call = {api.getLocation(q).await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        val locationKey = locationResponse?.Key;

        val weatherResponse = safeApiCall(
            call = {api.getWeather(locationKey!!.toInt()).await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        return weatherResponse?.DailyForecasts?.toMutableList();
    }
}