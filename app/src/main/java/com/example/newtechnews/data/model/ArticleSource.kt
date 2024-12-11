package com.example.newtechnews.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleSource(
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    val name: String
): Parcelable

