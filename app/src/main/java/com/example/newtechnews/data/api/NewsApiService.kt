package com.example.newtechnews.data.api

import com.example.newtechnews.BuildConfig
import com.example.newtechnews.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") category: String = "techcrunch,techradar,crypto-coins-news,engadget,hacker-news,new-scientist,recode,the-next-web",
        @Query("q") q: String?,
        @Query("pageSize") pageSize: Int = 5,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): NewsResponse
}