package com.raaziat.pagingexample.ui.maps

import com.raaziat.pagingexample.model.places.Result
import com.raaziat.pagingexample.network.BaseRepository
import com.raaziat.pagingexample.network.NetworkInterfaces
import com.raaziat.pagingexample.utils.Constants

class MapsRepository (private val api: NetworkInterfaces): BaseRepository(){

    suspend fun getPlaces(location:String) : MutableList<Result>?{
        val placesResponse = safeApiCall(
            call = {api.getNearbySearchD(location,Constants.RADIUS_1000,Constants.TYPE).await()},
            errorMessage = "Error Fetching PLaces"
        )

        return placesResponse?.results?.toMutableList()
    }

}