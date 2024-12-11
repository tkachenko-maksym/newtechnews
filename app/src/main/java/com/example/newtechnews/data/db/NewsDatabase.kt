package com.example.newtechnews.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newtechnews.data.db.NewsDao
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.model.Bookmark

@Database(entities = [Article::class, Bookmark::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "tech_news_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}