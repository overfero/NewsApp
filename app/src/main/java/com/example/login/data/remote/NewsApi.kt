package com.example.login.data.remote

import com.example.login.data.remote.response.NewsListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("news")
    suspend fun getNewsList(
        @Query("q") q: String? = null,
        @Query("timeframe") timeFrame: String? = null,
        @Query("country") country: String? = "id",
        @Query("category") category: String? = null,
        @Query("language") language: String? = "id",
        @Query("timezone") timezone: String = "Asia/Jakarta",
        @Query("image") image: Int = 1,
        @Query("page") page: String? = null,
        @Query("apikey") apikey: String = API_KEY
    ): NewsListDto

    companion object{
        const val BASE_URL = "https://newsdata.io/api/1/"
        const val API_KEY = "pub_"
    }
}