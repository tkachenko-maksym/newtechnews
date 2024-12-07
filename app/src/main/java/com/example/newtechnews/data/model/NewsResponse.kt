package com.example.newtechnews.data.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)