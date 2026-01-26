package com.example.aahar.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StatsApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://disease.sh/v3/covid-19/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: StatsApi = retrofit.create(StatsApi::class.java)
}