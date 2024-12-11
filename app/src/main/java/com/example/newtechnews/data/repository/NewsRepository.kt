package com.example.newtechnews.data.repository

import android.app.Application
import com.example.newtechnews.data.api.NewsApiService
import com.example.newtechnews.data.api.RetrofitInstance
import com.example.newtechnews.data.db.NewsDao
import com.example.newtechnews.data.db.NewsDatabase
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.model.Bookmark
import com.example.newtechnews.data.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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

    suspend fun deleteAllArticles() {
        newsDao.deleteAllArticles()
    }

    //bookmarks

    suspend fun getBookmarkedArticles(): List<Article> {
        return withContext(Dispatchers.IO) {
            newsDao.getAllBookmarks().map { bookmark -> bookmark.toArticle() }
        }
    }

    suspend fun isArticleInBookmark(article: Article): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Retrieve all bookmarks and check if the article is already bookmarked
                val bookmarks = newsDao.getAllBookmarks()
                bookmarks.any { it.url == article.url }
            } catch (e: Exception) {
                // Handle any errors (e.g., database not available) and return false
                false
            }
        }
    }


    suspend fun toggleBookmark(article: Article) {
        withContext(Dispatchers.IO) {
            val bookmark = article.toBookmark()

            if (isArticleInBookmark(article)) {
                // If the article is already bookmarked, remove it
                newsDao.removeBookmark(bookmark)
            } else {
                // If the article is not bookmarked, add it to bookmarks
                newsDao.addBookmark(bookmark)
            }
        }
    }

}

// Extensions for conversion between Article and Bookmark
private fun Article.toBookmark() = Bookmark(
    url = this.url,
    articleSource = this.articleSource,
    author = this.author,
    title = this.title,
    description = this.description,
    urlToImage = this.urlToImage,
    publishedAt = this.publishedAt,
    content = this.content
)

private fun Bookmark.toArticle() = Article(
    url = this.url,
    articleSource = this.articleSource,
    author = this.author,
    title = this.title,
    description = this.description,
    urlToImage = this.urlToImage,
    publishedAt = this.publishedAt,
    content = this.content
)