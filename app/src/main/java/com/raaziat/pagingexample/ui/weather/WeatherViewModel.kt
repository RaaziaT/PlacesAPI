package com.raaziat.pagingexample.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raaziat.pagingexample.model.openweather.WeatherResponse
import com.raaziat.pagingexample.network.ApiFactory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class WeatherViewModel : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : WeatherRepository = WeatherRepository(ApiFactory.networkInterfacesOpenWeather)


    val weatherLiveData = MutableLiveData<WeatherResponse>()

    fun fetchWeather(lat:Double?,lng:Double?){
        scope.launch {
            val weather = repository.getWeather(lat,lng)
            weatherLiveData.postValue(weather)
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()

}