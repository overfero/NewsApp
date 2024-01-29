package com.example.login.data.remote.response

data class NewsListDto(
    val nextPage: String,
    val results: List<NewsDto>,
    val status: String,
    val totalResults: Int
)