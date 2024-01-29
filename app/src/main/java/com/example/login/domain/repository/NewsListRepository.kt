package com.example.login.domain.repository

import com.example.login.data.local.news.NewsEntity
import com.example.login.domain.model.News
import com.example.login.util.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface NewsListRepository {

    suspend fun getNewsList(
        forceFetchFromRemote: Boolean,
        isOnline: Boolean,
        nextPage: String? = null,
        category: String = "top"
    ): Flow<Resource<List<News>>>

    suspend fun getNews(id: String): Flow<Resource<News>>

    suspend fun updateNews(news: NewsEntity)

    suspend fun getBookmarkNewsList(): Flow<Resource<List<News>>>

    suspend fun getSearchNewsList(
        forceFetchFromRemote: Boolean,
        query: String? = null,
        nextPage: String? = null,
        isOnline: Boolean,
        fromSearching: Boolean = false
    ): Flow<Resource<List<News>>>

    suspend fun addNews(news: NewsEntity)

    suspend fun deleteNewsList()

    suspend fun deleteSearchNewsList()
}