package com.raaziat.pagingexample.network

import com.raaziat.pagingexample.utils.Constants

object ApiFactory {

    val networkInterfaces: NetworkInterfaces = RetrofitFactory
        .retrofit(Constants.GOOGLE_BASE_URL, RetrofitFactory.clint)
        .create(NetworkInterfaces::class.java)

    val networkInterfacesWeather: NetworkInterfacesWeather = RetrofitFactory
        .retrofit(Constants.WEATHER_BASE_URL, RetrofitFactory.clintWeather)
        .create(NetworkInterfacesWeather::class.java)

    val networkInterfacesOpenWeather: NetworkInterfacesOpenWeather = RetrofitFactory
        .retrofit(Constants.BASE_URL_OPEN_WEATHER, RetrofitFactory.clintOpenWeather)
        .create(NetworkInterfacesOpenWeather::class.java)

}