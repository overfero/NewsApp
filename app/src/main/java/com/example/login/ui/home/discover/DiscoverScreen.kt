package com.example.login.ui.home.discover

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.login.presentation.HeaderTextComponent
import com.example.login.presentation.SearchDiscover
import com.example.login.presentation.TextSearch
import com.example.login.presentation.component.NewsItem
import com.example.login.ui.theme.BackgroundColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiscoverScreen(navHostController: NavHostController) {
    val viewModel: DiscoverViewModel = hiltViewModel()
    val state = viewModel.state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = viewModel::loadRefresh
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter
        ){
            LazyColumn {
                item {
                    SearchDiscover(text = state.search, onValueChange = { viewModel.onTextChanged(it) }) {
                        viewModel.getSearchNewsList(
                            search = state.search,
                            forceFetchFromRemote = true,
                            isSearching = true
                        )
                    }
                    
                    if (state.isSearching) {
                        TextSearch(query = state.search)
                    }
                }
                items(state.newsList.size) {index ->
                    NewsItem(news = state.newsList[index], navHostController = navHostController){
                        viewModel.onBookmarkChanged(state.newsList[index], index, state.isSearching)
                    }

                    if (index >= state.newsList.size - 1 && !state.isLoading && state.isOnline){
                        viewModel.isUserOnline(LocalContext.current)
                        viewModel.getSearchNewsList(forceFetchFromRemote = true)
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = state.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Preview
@Composable
fun PrevDiscover() {
//    DiscoverScreen()
}