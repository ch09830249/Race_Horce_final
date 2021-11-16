package com.example.horcerunning_final.network

import retrofit2.Call
import retrofit2.http.GET

interface myAPI {
    @GET
    fun getExchangeRate(): Call<ExchangeRate>
}