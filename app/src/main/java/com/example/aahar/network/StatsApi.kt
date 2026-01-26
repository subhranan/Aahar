package com.example.aahar.network

import com.example.aahar.models.Stats
import retrofit2.http.GET

interface StatsApi {

    @GET("all")
    suspend fun getGlobalStats(): Stats
}
