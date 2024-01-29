package com.example.login.ui.home.bookmark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.data.mapper.toNewsEntity
import com.example.login.domain.model.News
import com.example.login.domain.repository.NewsListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private  val newsListRepository: NewsListRepository
): ViewModel() {
    var state by mutableStateOf(BookmarkState())

    init {
        getBookmarkNewsList()
    }

    private fun getBookmarkNewsList(){
        viewModelScope.launch {
            newsListRepository.getBookmarkNewsList().collectLatest {
                it.data?.let { newsList ->
                    state = state.copy(newsList = newsList)
                }
            }
        }
    }

    fun onBookmarkChanged(news: News, index: Int){
        viewModelScope.launch {
            newsListRepository.updateNews(news.toNewsEntity())
        }
        val list = state.newsList.toMutableList()
        list[index] = news
        state = state.copy(
            newsList = list.toList()
        )
    }
}

data class BookmarkState(
    val newsList: List<News> = emptyList()
)