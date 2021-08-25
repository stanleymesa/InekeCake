package com.example.inekecake.API

import com.example.inekecake.Model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIRequestData {
    @GET("customers")
    fun ardRetrieveData() : Call<ResponseModel>

    @FormUrlEncoded
    @POST("customers")
    fun ardPostData(
        @Field("nama") nama: String,
        @Field("noHp") noHp: String,
        @Field("alamat") alamat: String,
        @Field("cake") cake: String,
        @Field("harga") harga: String,
        @Field("tglPesan") tglPesan: String,
        @Field("tglKirim") tglKirim: String,
    ) : Call<ResponseModel>


}