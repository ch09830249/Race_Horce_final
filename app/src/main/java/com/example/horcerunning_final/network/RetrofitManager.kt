package com.example.horcerunning_final.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class RetrofitManager private constructor() {

    //The website URL
    val URL = "https://tw.rter.info/"
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Singleton: Single instance for this class
    companion object {
        private val manager = RetrofitManager()
        val client: Retrofit
            get() = manager.retrofit
    }
}