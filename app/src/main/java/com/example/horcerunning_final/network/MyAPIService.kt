package com.example.horcerunning_final.network


import retrofit2.http.GET
import com.example.horcerunning_final.json.Currency
import retrofit2.Call

interface MyAPIService {
    @GET("capi.php")
    fun getExchangeRate(): Call<Currency>
}