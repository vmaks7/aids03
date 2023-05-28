package com.vandanov.aids03.data.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://78.136.240.141/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}