package com.example.newtechnews

import com.example.newtechnews.data.db.NewsDao
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.model.ArticleSource
import com.example.newtechnews.data.model.Bookmark
import com.example.newtechnews.data.repository.NewsRepository
import com.example.newtechnews.data.repository.toArticle
import com.example.newtechnews.data.repository.toBookmark
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NewsRepositoryTest {
    @Mock
    private lateinit var newsDao: NewsDao

    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        newsRepository = NewsRepository(newsDao = newsDao)
    }

    @Test
    fun `insertArticles inserts articles into DAO`() = runTest {
        val articles = listOf(
            Article("url1", ArticleSource("1", "Source1"), "Author1", "Title1", "Desc1", "Image1", "2024-01-01", "Content1")
        )

        newsRepository.insertArticles(articles)

        Mockito.verify(newsDao).insertArticles(articles)
    }

    @Test
    fun `getAllArticles retrieves articles from DAO`() = runTest {
        val articles = listOf(
            Article("url1", ArticleSource("1", "Source1"), "Author1", "Title1", "Desc1", "Image1", "2024-01-01", "Content1")
        )

        Mockito.`when`(newsDao.getAllArticles()).thenReturn(articles)

        val result = newsRepository.getAllArticles()

        assertEquals(articles, result)
        Mockito.verify(newsDao).getAllArticles()
    }

    @Test
    fun `deleteAllArticles clears all articles in DAO`() = runTest {
        newsRepository.deleteAllArticles()

        Mockito.verify(newsDao).deleteAllArticles()
    }

    @Test
    fun `getBookmarkedArticles retrieves bookmarks and converts to articles`() = runTest {
        val bookmarks = listOf(
            Bookmark("url1", ArticleSource("1", "Source1"), "Author1", "Title1", "Desc1", "Image1", "2024-01-01", "Content1")
        )
        val expectedArticles = bookmarks.map { it.toArticle() }

        Mockito.`when`(newsDao.getAllBookmarks()).thenReturn(bookmarks)

        val result = newsRepository.getBookmarkedArticles()

        assertEquals(expectedArticles, result)
        Mockito.verify(newsDao).getAllBookmarks()
    }

    @Test
    fun `toggleBookmark adds or removes bookmark`() = runTest {
        val article = Article("url1", ArticleSource("1", "Source1"), "Author1", "Title1", "Desc1", "Image1", "2024-01-01", "Content1")
        val bookmark = article.toBookmark()

        // Simulate article is not bookmarked initially
        Mockito.`when`(newsDao.getAllBookmarks()).thenReturn(emptyList())

        newsRepository.toggleBookmark(article)

        Mockito.verify(newsDao).addBookmark(bookmark)

        // Simulate article is bookmarked
        Mockito.`when`(newsDao.getAllBookmarks()).thenReturn(listOf(bookmark))

        newsRepository.toggleBookmark(article)

        Mockito.verify(newsDao).removeBookmark(bookmark)
    }

    @Test
    fun `isArticleInBookmark checks if article exists in bookmarks`() = runTest {
        val article = Article("url1", ArticleSource("1", "Source1"), "Author1", "Title1", "Desc1", "Image1", "2024-01-01", "Content1")
        val bookmark = article.toBookmark()

        // Simulate article is bookmarked
        Mockito.`when`(newsDao.getAllBookmarks()).thenReturn(listOf(bookmark))

        val result = newsRepository.isArticleInBookmark(article)

        assertTrue(result)

        // Simulate article is not bookmarked
        Mockito.`when`(newsDao.getAllBookmarks()).thenReturn(emptyList())

        val resultNotBookmarked = newsRepository.isArticleInBookmark(article)

        assertFalse(resultNotBookmarked)
    }
}

