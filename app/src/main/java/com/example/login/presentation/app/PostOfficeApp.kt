package com.example.login.presentation.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.login.presentation.navigation.NavGraph
import com.example.login.ui.home.HomeViewModel

@Composable
fun PostOfficeApp(homeViewModel: HomeViewModel = viewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
        ) {
        val userLoggedIn = homeViewModel.state.userLogged
        val navController = rememberNavController()
        NavGraph(navHostController = navController, userLoggedIn = userLoggedIn)
    }
}