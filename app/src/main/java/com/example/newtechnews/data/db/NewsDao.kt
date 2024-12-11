package com.example.newtechnews.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newtechnews.data.model.Article

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
}