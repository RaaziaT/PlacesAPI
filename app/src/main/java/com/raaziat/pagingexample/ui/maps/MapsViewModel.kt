package com.raaziat.pagingexample.ui.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raaziat.pagingexample.model.places.Result
import com.raaziat.pagingexample.network.ApiFactory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MapsViewModel :ViewModel(){
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : MapsRepository = MapsRepository(ApiFactory.networkInterfaces)

     val placesLiveData = MutableLiveData<MutableList<Result>>()

    fun fetchPlaces(location: String){
        scope.launch {
            val places = repository.getPlaces(location)
            placesLiveData.postValue(places)
        }
    }

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}