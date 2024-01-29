package com.example.login.di

import android.content.Context
import androidx.room.Room
import com.example.login.data.local.news.NewsDatabase
import com.example.login.data.remote.NewsApi
import com.example.login.data.remote.NewsApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun providesNewsApi(): NewsApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun providesNewsDatabase(@ApplicationContext context: Context): NewsDatabase{
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        ).build()
    }
}

