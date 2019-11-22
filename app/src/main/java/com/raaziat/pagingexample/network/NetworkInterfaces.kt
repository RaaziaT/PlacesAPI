package com.raaziat.pagingexample.network

import com.raaziat.pagingexample.model.direction.Directions
import com.raaziat.pagingexample.model.places.NearbySearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkInterfaces {
    @GET("place/nearbysearch/json")
    fun getNearbySearch(
        @Query("location") location: String,
        @Query("radius") radius: String,
        @Query("type") types: String,
        @Query("key") key: String
    ): Call<NearbySearch>

    @GET("directions/json")
    fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): Call<Directions>
}