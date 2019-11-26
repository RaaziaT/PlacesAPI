package com.raaziat.pagingexample.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raaziat.accuweathersample.model.weather.DailyForecast
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class WeatherViewModel : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : WeatherRepository = WeatherRepository()


    val weatherLiveData = MutableLiveData<MutableList<DailyForecast>>()

    fun fetchMovies(latlng:String){
        scope.launch {
            val popularMovies = repository.getWeather(latlng)
            weatherLiveData.postValue(popularMovies)
        }
    }


    fun cancelAllRequests() = coroutineContext.cancel()

}