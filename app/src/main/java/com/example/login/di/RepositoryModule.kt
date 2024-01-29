package com.example.login.di

import com.example.login.data.repository.NewsListRepositoryImpl
import com.example.login.domain.repository.NewsListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsListRepositoryImpl: NewsListRepositoryImpl
    ): NewsListRepository
}