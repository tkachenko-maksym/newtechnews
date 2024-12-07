package com.example.newtechnews.data.repository

import com.example.newtechnews.data.api.NewsApiService
import com.example.newtechnews.data.api.RetrofitInstance
import com.example.newtechnews.data.model.NewsResponse

class NewsRepository(private val newsService: NewsApiService = RetrofitInstance.newsApiService) {
    suspend fun getTopHeadlines(
        query: String? = null,
        page: Int = 1,
    ): Result<NewsResponse> {
        return try {
            Result.success(newsService.getTopHeadlines(q = query, page = page))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}