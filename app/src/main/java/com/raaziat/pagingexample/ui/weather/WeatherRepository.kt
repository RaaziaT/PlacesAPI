package com.raaziat.pagingexample.ui.weather

import com.raaziat.pagingexample.model.openweather.WeatherResponse
import com.raaziat.pagingexample.network.BaseRepository
import com.raaziat.pagingexample.network.NetworkInterfacesOpenWeather

class WeatherRepository(private val api: NetworkInterfacesOpenWeather) : BaseRepository(){

    suspend fun getWeather(lat:Double?,lng:Double?) :WeatherResponse?{

        //safeApiCall is defined in BaseRepository.kt
//        val locationResponse = safeApiCall(
//            call = {api.getLocation(q).await()},
//            errorMessage = "Error Fetching Popular Weather"
//        )
//
//        val locationKey = locationResponse?.Key;

        return safeApiCall(
        call = { api.getForecast(lat,lng).await()},
        errorMessage = "Error Fetching Popular Weather"
    );
    }
}