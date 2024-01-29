package com.example.login.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.login.data.mapper.toNewsEntity
import com.example.login.domain.model.News
import com.example.login.domain.repository.NewsListRepository
import com.example.login.util.Resource
import com.example.login.util.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsListRepository: NewsListRepository
): ViewModel() {
    var state by mutableStateOf(HomeState())
    val category: List<String> = listOf("top", "business", "politics", "science,technology",
        "entertainment,sports", "food,health", "world")
    val nameTag: List<String> = listOf("Top", "Business", "Politics",
        "Science", "Entertainment", "Health", "World")

    init {
        checkForActiveSession()
    }

    fun getNewsList(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean = false
        ){
        viewModelScope.launch{
            state = state.copy(isLoading = true)

            newsListRepository.getNewsList(
                forceFetchFromRemote,
                state.isOnline,
                state.nextPage,
                state.category
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
                                newsList = if (isRefresh) newsList.subList(3, newsList.size) else state.newsList + newsList,
                                requestDate = LocalDateTime.now(),
                                isLoading = false,
                                headerNewsList = if (isRefresh) newsList.subList(0,3) else state.headerNewsList
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

    fun onBookmarkChanged(news: News, index: Int, isHeader: Boolean = false){
        viewModelScope.launch {
            newsListRepository.updateNews(news.toNewsEntity().copy(bookmark = !news.bookmark))
        }
        val list = if (isHeader) state.headerNewsList.toMutableList() else state.newsList.toMutableList()
        list[index] = news.copy(bookmark = !news.bookmark)

        state = if (isHeader) {
            state.copy(
                headerNewsList = list.toList()
            )
        } else {
            state.copy(
                newsList = list.toList()
            )
        }
    }

    fun loadRefresh(){
        state = state.copy(
            newsList = emptyList(),
            headerNewsList = emptyList(),
            nextPage = null)
        viewModelScope.launch {
            newsListRepository.deleteNewsList()
        }
        getNewsList(true, true)
    }

    fun onCategoryChanged(category: String, name: String){
        state = state.copy(category = category, nameTag = name, newsList = emptyList())
        getNewsList(false)
    }

    fun logOut(navHostController: NavHostController){
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = AuthStateListener{
            if (it.currentUser == null){
                navHostController.popBackStack()
                navHostController.navigate(Screen.Login.route)
            }else{

            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    private fun checkForActiveSession(){
        if (FirebaseAuth.getInstance().currentUser != null){
            state = state.copy(userLogged = true)
            getNewsList(false, isRefresh = true)
        }else{
            state = state.copy(userLogged = false)
        }
    }
}

data class HomeState(
    val userLogged: Boolean = false,
    val isLoading: Boolean = false,
    val newsList: List<News> = emptyList(),
    val nextPage: String? = null,
    val requestDate: LocalDateTime? = null,
    val isOnline: Boolean = true,
    val category: String = "top",
    val nameTag: String = "Top",
    val headerNewsList: List<News> = emptyList()
)