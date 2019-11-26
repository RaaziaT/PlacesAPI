package com.raaziat.pagingexample.network

import com.raaziat.accuweathersample.model.Location.Location
import com.raaziat.accuweathersample.model.weather.Weather
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkInterfacesWeather {
    @GET("forecasts/v1/daily/5day/{locationKey}")
    fun getWeather(
        @Path("locationKey") locationKey:Int
    ) : Deferred<Response<Weather>>

    @GET("locations/v1/cities/geoposition/search")
    fun getLocation(
        @Query("q") q:String
    ) : Deferred<Response<Location>>
}