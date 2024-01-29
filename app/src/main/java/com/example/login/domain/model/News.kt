package com.example.login.domain.model

data class News(
    val article_id: String,
    val category: List<String>,
    val content: String,
    val country: List<String>,
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
