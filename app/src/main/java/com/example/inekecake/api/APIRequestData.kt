package com.example.inekecake.api

import com.example.inekecake.model.ResponseModel
import retrofit2.Call
import retrofit2.http.*

interface APIRequestData {
    // GET DATA
    @GET("customers")
    fun ardRetrieveData() : Call<ResponseModel>

    // POST DATA
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

    // DELETE DATA
    @FormUrlEncoded
    @POST("customer")
    fun ardDeleteData(
        @Field("id") id: Int
    ) : Call<ResponseModel>

    // UPDATE DATA
    @FormUrlEncoded
    @PUT("customer")
    fun ardUpdateData(
        @Field("id") id: Int,
        @Field("nama") nama: String,
        @Field("noHp") noHp: String,
        @Field("alamat") alamat: String,
        @Field("cake") cake: String,
        @Field("harga") harga: String,
        @Field("tglPesan") tglPesan: String,
        @Field("tglKirim") tglKirim: String,
    ) : Call<ResponseModel>

}