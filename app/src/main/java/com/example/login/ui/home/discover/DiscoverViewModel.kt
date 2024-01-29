package com.example.login.ui.home.discover

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.data.mapper.toNewsEntity
import com.example.login.domain.model.News
import com.example.login.domain.repository.NewsListRepository
import com.example.login.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val newsListRepository: NewsListRepository
): ViewModel() {
    var state by mutableStateOf(DiscoverState())

    init {
        getSearchNewsList(forceFetchFromRemote = false)
    }

    fun getSearchNewsList(
        search: String? = null,
        forceFetchFromRemote: Boolean,
        isSearching: Boolean = false
    ) {
        viewModelScope.launch{
            if (isSearching) { state = state.copy(newsList = emptyList(), isSearching = true) }
            state = state.copy(isLoading = true)

            newsListRepository.getSearchNewsList(
                forceFetchFromRemote,
                nextPage = state.nextPage,
                query = search,
                isOnline = state.isOnline,
                fromSearching = isSearching
            ).collectLatest {result ->
                when(result){
                    is Resource.Error -> {
                        state = state.copy(isLoading = false)
                        Log.d("request", "Error = ${result.message}")
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        result.data?.let {newsList ->
                            state = state.copy(
                                newsList = state.newsList + newsList,
                                requestDate = LocalDateTime.now(),
                                isLoading = false
                            )
                        }
                        result.nextPage?.let {nextPage ->
                            state = state.copy(nextPage = nextPage)
                        }
                    }
                }
            }
        }
    }

    fun loadRefresh(){
        state = state.copy(
            newsList = emptyList(),
            nextPage = null,
            isSearching = false,
            search = "")
        viewModelScope.launch {
            newsListRepository.deleteSearchNewsList()
        }
        getSearchNewsList(forceFetchFromRemote =  true)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun isUserOnline(context: Context){
        state = state.copy(isOnline = isNetworkAvailable(context))
    }

    fun onTextChanged(text: String) {
        state = state.copy(search = text)
    }

    fun onBookmarkChanged(news: News, index: Int, fromSearching: Boolean = false) {
        viewModelScope.launch {
            if (fromSearching) {
                newsListRepository.addNews(news.toNewsEntity().copy(bookmark = !news.bookmark))
            } else {
                newsListRepository.updateNews(news.toNewsEntity().copy(bookmark = !news.bookmark))
            }
        }
        val list = state.newsList.toMutableList()
        list[index] = news.copy(bookmark = !news.bookmark)

        state = state.copy(newsList = list.toList())
    }
}

data class DiscoverState(
    val search: String = "",
    val newsList: List<News> = emptyList(),
    val isOnline: Boolean = true,
    val isLoading: Boolean = false,
    val nextPage: String? = null,
    val requestDate: LocalDateTime? = null,
    val isSearching: Boolean = false
)