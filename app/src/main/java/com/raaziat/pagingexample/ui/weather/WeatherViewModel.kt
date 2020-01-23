package com.raaziat.pagingexample.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raaziat.accuweathersample.model.weather.DailyForecast
import com.raaziat.pagingexample.network.ApiFactory
import com.raaziat.pagingexample.network.NetworkInterfacesWeather
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class WeatherViewModel : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : WeatherRepository = WeatherRepository(ApiFactory.networkInterfacesWeather)


    val weatherLiveData = MutableLiveData<MutableList<DailyForecast>>()

    fun fetchWeather(latlng:String){
        scope.launch {
            val weather = repository.getWeather(latlng)
            weatherLiveData.postValue(weather)
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}