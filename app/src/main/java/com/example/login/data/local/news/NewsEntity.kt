package com.example.login.data.local.news

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey
    val article_id: String,
    val category: String,
    val content: String,
    val country: String,
    val description: String,
    val image_url: String,
    val link: String,
    val pubDate: String,
    val source_id: String,
    val title: String,
    val nextPage: String,
    val bookmark: Boolean,
    val fromSearch: Boolean
)
