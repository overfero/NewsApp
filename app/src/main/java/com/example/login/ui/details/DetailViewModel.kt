package com.example.login.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.domain.model.News
import com.example.login.domain.repository.NewsListRepository
import com.example.login.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val newsListRepository: NewsListRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    val id = savedStateHandle.get<String>("id")
    var state by mutableStateOf(DetailState())

    init {
        getNews(id ?: "-1")
    }

    private fun getNews(id: String){
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            newsListRepository.getNews(id).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        state = state.copy(isLoading = true)
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(news = it)
                        }
                    }
                }
            }
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val news: News? = null
)