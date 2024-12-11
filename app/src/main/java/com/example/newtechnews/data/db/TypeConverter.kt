package com.example.newtechnews.data.db
import androidx.room.TypeConverter
import com.example.newtechnews.data.model.ArticleSource
import com.google.gson.Gson
class Converters {

    @TypeConverter
    fun fromArticleSource(articleSource: ArticleSource?): String? {
        return Gson().toJson(articleSource)
    }

    @TypeConverter
    fun toArticleSource(articleSourceString: String?): ArticleSource? {
        return Gson().fromJson(articleSourceString, ArticleSource::class.java)
    }
}