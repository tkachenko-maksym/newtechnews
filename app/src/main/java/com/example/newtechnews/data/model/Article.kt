package com.example.newtechnews.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey val url: String,
    @SerializedName("source")
    val articleSource: ArticleSource,
    val author: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)