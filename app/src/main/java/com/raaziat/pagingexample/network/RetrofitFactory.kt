package com.raaziat.pagingexample.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.raaziat.pagingexample.BuildConfig
import com.raaziat.pagingexample.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitFactory {

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request()
            .url().newBuilder()
            .addQueryParameter("key", Constants.GOOGLE_API_KEY)
            .build()
        val newRequest = chain.request()
            .newBuilder().url(newUrl).build()

        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val clint =
        if (BuildConfig.DEBUG) {
            OkHttpClient().newBuilder().addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
        }

    fun retrofit(baseUrl:String):Retrofit = Retrofit.Builder()
        .client(clint)
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

}