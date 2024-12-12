package com.example.newtechnews

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newtechnews.data.db.NewsDao
import com.example.newtechnews.data.db.NewsDatabase
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.model.ArticleSource
import com.example.newtechnews.data.model.Bookmark
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsDaoTest {
    private lateinit var database: NewsDatabase
    private lateinit var newsDao: NewsDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, NewsDatabase::class.java
        ).build()
        newsDao = database.newsDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetArticles() = runBlocking {
        // Given
        val article = createMockArticle()

        // When
        newsDao.insertArticles(listOf(article))
        val articles = newsDao.getAllArticles()

        // Then
        assertEquals(1, articles.size)
        assertEquals(article, articles[0])
    }

    @Test
    fun deleteAllArticles() = runBlocking {
        // Given
        val article = createMockArticle()
        newsDao.insertArticles(listOf(article))

        // When
        newsDao.deleteAllArticles()
        val articles = newsDao.getAllArticles()

        // Then
        assertTrue(articles.isEmpty())
    }

    @Test
    fun addAndRemoveBookmark() = runBlocking {
        // Given
        val bookmark = createMockBookmark()

        // When - Add
        newsDao.addBookmark(bookmark)
        var bookmarks = newsDao.getAllBookmarks()

        // Then
        assertEquals(1, bookmarks.size)
        assertEquals(bookmark, bookmarks[0])

        // When - Remove
        newsDao.removeBookmark(bookmark)
        bookmarks = newsDao.getAllBookmarks()

        // Then
        assertTrue(bookmarks.isEmpty())
    }

    private fun createMockArticle() = Article(
        url = "https://example.com",
        articleSource = ArticleSource("1", "Test Source"),
        author = "Test Author",
        title = "Test Title",
        description = "Test Description",
        urlToImage = "https://example.com/image.jpg",
        publishedAt = "2024-01-01",
        content = "Test Content"
    )

    private fun createMockBookmark() = Bookmark(
        url = "https://example.com",
        articleSource = ArticleSource("1", "Test Source"),
        author = "Test Author",
        title = "Test Title",
        description = "Test Description",
        urlToImage = "https://example.com/image.jpg",
        publishedAt = "2024-01-01",
        content = "Test Content"
    )
}