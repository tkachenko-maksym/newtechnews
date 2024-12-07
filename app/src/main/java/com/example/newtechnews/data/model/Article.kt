package com.example.newtechnews.data.model

data class Article(
    val articleSource: ArticleSource?,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)