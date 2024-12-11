package com.example.newtechnews.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.model.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM article_table")
    suspend fun getAllArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("DELETE FROM article_table")
    suspend fun deleteAllArticles()

    @Query("SELECT * FROM article_table WHERE url = :url")
    suspend fun getArticleByUrl(url: String): Article

    //bookmarks

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: Bookmark)

    @Delete
    suspend fun removeBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks_table")
    fun getAllBookmarks(): List<Bookmark>

}