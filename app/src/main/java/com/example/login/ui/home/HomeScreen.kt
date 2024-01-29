package com.example.login.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.login.presentation.BottomBar
import com.example.login.presentation.TopBar
import com.example.login.presentation.navigation.BottomNavGraph

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val bottomNavController = rememberNavController()

    Scaffold(
        topBar = { TopBar{viewModel.logOut(navHostController)}}
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it),
        ) {
            BottomNavGraph(
                navHostController = bottomNavController,
                detailNavHostController = navHostController
            )

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
                BottomBar(navHostController = bottomNavController)
            }
        }
    }
}
