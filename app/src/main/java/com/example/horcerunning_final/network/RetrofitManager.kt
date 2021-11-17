package com.example.horcerunning_final.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class RetrofitManager private constructor() {
    val URL = "https://tw.rter.info/"
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        private val manager = RetrofitManager()
        val client: Retrofit
            get() = manager.retrofit
    }
}