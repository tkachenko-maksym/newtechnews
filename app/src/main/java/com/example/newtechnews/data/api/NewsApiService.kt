package com.example.newtechnews.data.api

import com.example.newtechnews.BuildConfig
import com.example.newtechnews.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String = "technology",
        @Query("q") q: String?,
        @Query("pageSize") pageSize: Int=5,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponse
}