package com.example.dahee_prac.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    val api:HanaAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://dev12-mbp.hanabank.com:18080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HanaAPI::class.java)
    }
}