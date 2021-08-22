package com.example.inekecake.API

import com.example.inekecake.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetroServer {
    private var baseURL: String = "http://10.0.2.2:8000/api/inekecake/"
    private lateinit var retro: Retrofit

    fun konekRetrofit(): Retrofit{
        retro = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retro
    }

//    fun konekRetrofit(): Retrofit{
//        retro = Retrofit.Builder()
//            .client(OkHttpClient.Builder().apply {
//                if (BuildConfig.DEBUG)
//                    ignoreAllSSLErrors()
//            }.build())
//            .baseUrl(baseURL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        return retro
//    }
//
//    fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
//        val naiveTrustManager = object : X509TrustManager {
//            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
//            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
//            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
//        }
//
//        val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
//            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
//            init(null, trustAllCerts, SecureRandom())
//        }.socketFactory
//
//        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
//        hostnameVerifier(HostnameVerifier { _, _ -> true })
//        return this
//    }
}