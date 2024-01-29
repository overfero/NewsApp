package com.example.login.data.repository

import com.example.login.data.local.news.NewsDatabase
import com.example.login.data.local.news.NewsEntity
import com.example.login.data.mapper.toNews
import com.example.login.data.mapper.toNewsEntity
import com.example.login.data.remote.NewsApi
import com.example.login.domain.model.News
import com.example.login.domain.repository.NewsListRepository
import com.example.login.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class NewsListRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase
): NewsListRepository {
    override suspend fun getNewsList(
        forceFetchFromRemote: Boolean,
        isOnline: Boolean,
        nextPage: String?,
        category: String
    ): Flow<Resource<List<News>>> {
        return flow {
            emit(Resource.Loading(true))

            val localNewsList = newsDatabase.newsDao.getNewsList(category)
            val shouldLoadLocalNews = localNewsList.isNotEmpty() && !forceFetchFromRemote
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val newestDateRequest = try {
                LocalDateTime.parse(localNewsList[0].pubDate, formatter)
            } catch (e: Exception){
                LocalDateTime.now()
            }
            val duration = Duration.between(newestDateRequest, LocalDateTime.now())

            if (shouldLoadLocalNews && !isOnline){
                emit(Resource.Success(data = localNewsList.map { it.toNews() }, nextPage = localNewsList[0].nextPage))
                emit(Resource.Loading(false))
                return@flow
            } else if (shouldLoadLocalNews && duration.toHours() <= 3 && isOnline){
                emit(Resource.Success(data = localNewsList.map { it.toNews() }, nextPage = localNewsList[0].nextPage))
                emit(Resource.Loading(false))
                return@flow
            }else if (isOnline && duration.toHours() > 3){
                newsDatabase.newsDao.deleteNewsList()
            }

            val newsListFromApi = try {
                newsApi.getNewsList(
                    page = nextPage,
                    category = category
                )
            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news IO"))
                return@flow
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news HTTP"))
                return@flow
            } catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news $e"))
                return@flow
            }

            val nextPageId = newsListFromApi.nextPage
            val newsEntity = newsListFromApi.results.let {
                it.map { movieDto -> movieDto.toNewsEntity(nextPageId) }
            }

            newsDatabase.newsDao.upsertNewsList(newsEntity)
            emit(Resource.Success(data = newsEntity.map { it.toNews() }, nextPage = nextPageId))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getNews(id: String): Flow<Resource<News>> {
        return flow {
            emit(Resource.Loading(true))

            val newsEntity = newsDatabase.newsDao.getNewsById(id)

            if (newsEntity != null){
                emit(Resource.Success(data = newsEntity.toNews()))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(message = "Error no such news"))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateNews(news: NewsEntity) {
        newsDatabase.newsDao.updateNews(news)
    }

    override suspend fun getBookmarkNewsList(): Flow<Resource<List<News>>> {
        return flow {
            val bookmarkNewsList = newsDatabase.newsDao.getBookmarkNewsList()

            if (bookmarkNewsList != null){
                emit(Resource.Success(data = bookmarkNewsList.map { it.toNews() }))
                return@flow
            }
        }
    }

    override suspend fun getSearchNewsList(
        forceFetchFromRemote: Boolean,
        query: String?,
        nextPage: String?,
        isOnline: Boolean,
        fromSearching: Boolean
    ): Flow<Resource<List<News>>> {
        return flow {
            emit(Resource.Loading(true))

            val localNewsList = newsDatabase.newsDao.getSearchNewsList()
            val shouldLoadLocalNews = localNewsList.isNotEmpty() && !forceFetchFromRemote
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val newestDateRequest = try {
                LocalDateTime.parse(localNewsList[0].pubDate, formatter)
            } catch (e: Exception){
                LocalDateTime.now()
            }
            val duration = Duration.between(newestDateRequest, LocalDateTime.now())

            if (shouldLoadLocalNews && !isOnline){
                emit(Resource.Success(data = localNewsList.map { it.toNews() }, nextPage = localNewsList[0].nextPage))
                emit(Resource.Loading(false))
                return@flow
            } else if (shouldLoadLocalNews && duration.toHours() <= 1 && isOnline){
                emit(Resource.Success(data = localNewsList.map { it.toNews() }, nextPage = localNewsList[0].nextPage))
                emit(Resource.Loading(false))
                return@flow
            }else if (isOnline && duration.toHours() > 1){
                newsDatabase.newsDao.deleteSearchNewsList()
            }

            val newsListFromApi = try {
                newsApi.getNewsList(
                    page = nextPage,
                    country = null,
                    language = if (fromSearching) null else "en",
                    q = query
                )
            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news IO"))
                return@flow
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news HTTP"))
                return@flow
            } catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading news $e"))
                return@flow
            }

            val nextPageId = newsListFromApi.nextPage
            val newsEntity = newsListFromApi.results.let {
                it.map { newsDto -> newsDto.toNewsEntity(nextPageId, true) }
            }

            if (!fromSearching) newsDatabase.newsDao.upsertNewsList(newsEntity)
            emit(Resource.Success(data = newsEntity.map { it.toNews() }, nextPage = nextPageId))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun addNews(news: NewsEntity) {
        newsDatabase.newsDao.insertNews(news)
    }

    override suspend fun deleteNewsList() {
        newsDatabase.newsDao.deleteNewsList()
    }

    override suspend fun deleteSearchNewsList() {
        newsDatabase.newsDao.deleteSearchNewsList()
    }
}