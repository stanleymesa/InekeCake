package com.example.inekecake.API

import com.example.inekecake.Model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit

interface APIRequestData {
    @GET("customers")
    fun ardRetrieveData() : Call<ResponseModel>

}