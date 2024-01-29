package com.example.login.ui.home.newslist

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.login.presentation.CategoryItem
import com.example.login.presentation.component.NewsItem
import com.example.login.presentation.component.TopNewsSlider
import com.example.login.ui.home.HomeViewModel
import com.example.login.ui.theme.IconColor
import com.example.login.util.Screen
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun NewsListScreen(
    navHostController: NavHostController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = viewModel::loadRefresh
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn{
            item {
                val pagerState = rememberPagerState { 3 }
                if (state.headerNewsList.isNotEmpty()){
                    HorizontalPager(state = pagerState) {index ->
                        TopNewsSlider(news = state.headerNewsList[index], navHostController){
                            viewModel.onBookmarkChanged(state.headerNewsList[index], index, true)
                        }
                    }

                    LaunchedEffect(Unit) {
                        while (true) {
                            delay(3000)
                            pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), contentAlignment = Alignment.Center){
                        val offset = when(pagerState.currentPage){
                            0 -> {-20}
                            1 -> {0}
                            2 -> {20}
                            else -> {-20}
                        }
                        Divider(
                            color = Color.LightGray.copy(alpha = 0.4f),
                            thickness = 8.dp,
                            modifier = Modifier
                                .width(60.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )
                        Divider(
                            color = IconColor,
                            thickness = 8.dp,
                            modifier = Modifier
                                .width(20.dp)
                                .offset(offset.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )
                    }
                } else {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 6.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color.LightGray.copy(alpha = 0.5f))
                    )
                }
            }
            item {
                LazyRow {
                    items(viewModel.nameTag.size){index ->
                        CategoryItem(
                            title = viewModel.nameTag[index],
                            selected = viewModel.nameTag[index] == state.nameTag
                        ) {
                            viewModel.onCategoryChanged(viewModel.category[index], viewModel.nameTag[index])
                        }
                    }
                }
            }
            items(state.newsList.size){index ->
                NewsItem(news = state.newsList[index], navHostController = navHostController){
                    viewModel.onBookmarkChanged(state.newsList[index], index)
                }
                if (index >= state.newsList.size - 1 && !state.isLoading && state.isOnline){
                    viewModel.isUserOnline(LocalContext.current)
                    viewModel.getNewsList(true)
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