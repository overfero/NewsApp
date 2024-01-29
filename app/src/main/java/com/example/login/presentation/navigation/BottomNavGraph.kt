package com.example.login.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.login.ui.details.DetailScreen
import com.example.login.ui.home.HomeScreen
import com.example.login.ui.home.bookmark.BookmarkScreen
import com.example.login.ui.home.discover.DiscoverScreen
import com.example.login.ui.home.newslist.NewsListScreen
import com.example.login.ui.home.setting.SettingScreen
import com.example.login.ui.login.LoginScreen
import com.example.login.ui.signup.SignupScreen
import com.example.login.ui.term.PrivacyPolicyScreen
import com.example.login.ui.term.TermsAndConditionScreen
import com.example.login.util.Screen

@Composable
fun BottomNavGraph(
    navHostController: NavHostController,
    detailNavHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.NewsList.route
    ){
        composable(route = Screen.NewsList.route){
            NewsListScreen(detailNavHostController)
        }
        composable(route = Screen.Discover.route){
            DiscoverScreen(detailNavHostController)
        }
        composable(route = Screen.Bookmark.route){
            BookmarkScreen(detailNavHostController)
        }
        composable(route = Screen.Setting.route){
            SettingScreen()
        }
    }
}