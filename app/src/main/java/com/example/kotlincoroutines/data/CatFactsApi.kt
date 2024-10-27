package com.example.kotlincoroutines.data

import retrofit2.http.GET
import retrofit2.http.Query


interface CatFactsApi {
    @GET("/")
    suspend fun getRandomCatFact(
        @Query("count") count: Int = 1,
        @Query("lang") lang: String = "rus"
    ): CatFactsResponse
}
