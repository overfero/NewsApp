package com.example.login.ui.home.bookmark

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.login.presentation.HeaderTextComponent
import com.example.login.presentation.component.NewsItem

@Composable
fun BookmarkScreen(navHostController: NavHostController) {
    val viewModel: BookmarkViewModel = hiltViewModel()
    val state = viewModel.state

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        LazyColumn {
            items(state.newsList.size){index ->
                NewsItem(news = state.newsList[index], navHostController = navHostController){
                    viewModel.onBookmarkChanged(
                        state.newsList[index].copy(bookmark = !state.newsList[index].bookmark),
                        index)
                }
            }
            item { 
                Box(modifier = Modifier.fillMaxWidth().height(65.dp))
            }
        }
    }
}