package com.raaziat.pagingexample.network

import com.raaziat.pagingexample.utils.Constants
import retrofit2.create

object ApiFactory{

    val networkInterfaces = RetrofitFactory
        .retrofit(Constants.GOOGLE_BASE_URL)
        .create(NetworkInterfaces::class.java)

}