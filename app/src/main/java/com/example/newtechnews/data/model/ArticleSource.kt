package com.example.newtechnews.data.model

import com.google.gson.annotations.SerializedName

data class ArticleSource(
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    val name: String?
)

