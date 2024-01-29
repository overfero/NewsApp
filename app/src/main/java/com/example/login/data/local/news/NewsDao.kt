package com.example.login.data.local.news

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.login.domain.model.News

@Dao
interface NewsDao {

    @Upsert
    suspend fun upsertNewsList(newsList: List<NewsEntity>)

    @Query("SELECT * FROM news_table WHERE article_id = :id")
    suspend fun getNewsById(id: String): NewsEntity

    @Query("SELECT * FROM news_table WHERE category = :category AND fromSearch = :fromSearch ORDER BY pubDate DESC")
    suspend fun getNewsList(category: String, fromSearch: Boolean = false): List<NewsEntity>

    @Query("DELETE FROM news_table WHERE bookmark = :bookmark AND fromSearch = :fromSearch")
    suspend fun deleteNewsList(bookmark: Boolean = false, fromSearch: Boolean = false)

    @Update
    suspend fun updateNews(news: NewsEntity)

    @Query("SELECT * FROM news_table WHERE bookmark = :bookmark")
    suspend fun getBookmarkNewsList(bookmark: Boolean = true): List<NewsEntity>

    @Query("SELECT * FROM news_table WHERE fromSearch = :fromSearch")
    suspend fun getSearchNewsList(fromSearch: Boolean = true): List<NewsEntity>

    @Query("DELETE FROM news_table WHERE fromSearch = :fromSearch AND bookmark = :bookmark")
    suspend fun deleteSearchNewsList(fromSearch: Boolean = true, bookmark: Boolean = false)

    @Insert
    suspend fun insertNews(news: NewsEntity)
}