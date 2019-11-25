package com.raaziat.pagingexample.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit


object RetrofitClient {
    private const val GOOGLE_BASE_URL = "https://maps.googleapis.com/maps/api/"

    fun googleMethods(): NetworkInterfaces {
        val retrofit = Retrofit.Builder()
            .baseUrl(RetrofitClient.GOOGLE_BASE_URL)
            .client(OkHttpClient().newBuilder().build())
            .build()

        return retrofit.create(NetworkInterfaces::class.java)
    }
}