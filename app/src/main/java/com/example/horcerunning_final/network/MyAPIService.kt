package com.example.horcerunning_final.network


import retrofit2.http.GET
import com.example.horcerunning_final.json.Currency
import retrofit2.Call

interface MyAPIService {
    //Assign the path to the Json file
    @GET("capi.php")
    fun getExchangeRate(): Call<Currency>
}