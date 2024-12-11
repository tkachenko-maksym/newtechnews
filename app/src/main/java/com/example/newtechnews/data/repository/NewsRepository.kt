package com.example.newtechnews.data.repository

import android.app.Application
import com.example.newtechnews.data.api.NewsApiService
import com.example.newtechnews.data.api.RetrofitInstance
import com.example.newtechnews.data.db.NewsDao
import com.example.newtechnews.data.db.NewsDatabase
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(
    private val newsApiService: NewsApiService = RetrofitInstance.newsApiService,
    private val newsDao: NewsDao
) {
    constructor(application: Application) : this(
        newsDao = NewsDatabase.getDatabase(application).newsDao()
    )

    suspend fun getTopHeadlines(
        query: String? = null,
        page: Int = 1
    ): Result<NewsResponse> {
        return try {
            val response = newsApiService.getTopHeadlines(q = query, page = page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun insertArticles(articles: List<Article>) {
        newsDao.insertArticles(articles)
    }

    suspend fun getAllArticles(): List<Article> {
        return newsDao.getAllArticles()
    }
    suspend fun getArticleByUrl(url: String): Article {
        return newsDao.getArticleByUrl(url)
    }
    suspend fun deleteAllArticles() {
        newsDao.deleteAllArticles()
    }
}
