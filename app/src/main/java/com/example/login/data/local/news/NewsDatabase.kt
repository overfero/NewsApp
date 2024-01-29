package com.example.login.data.local.news

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([NewsEntity::class], version = 1)
abstract class NewsDatabase: RoomDatabase() {
    abstract val newsDao: NewsDao
}