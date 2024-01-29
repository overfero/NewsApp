package com.example.login.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.login.ui.details.DetailScreen
import com.example.login.ui.home.HomeScreen
import com.example.login.ui.login.LoginScreen
import com.example.login.ui.term.PrivacyPolicyScreen
import com.example.login.ui.signup.SignupScreen
import com.example.login.ui.term.TermsAndConditionScreen
import com.example.login.util.Screen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    userLoggedIn: Boolean
) {
    NavHost(
        navController = navHostController,
        startDestination = if (userLoggedIn) Screen.Home.route else Screen.SignUp.route
    ){
        composable(route = Screen.Login.route){
            LoginScreen(navHostController)
        }
        composable(route = Screen.SignUp.route){
            SignupScreen(navHostController)
        }
        composable(route = Screen.PrivacyPolicy.route){
            PrivacyPolicyScreen()
        }
        composable(route = Screen.TermOfUse.route){
            TermsAndConditionScreen()
        }
        composable(route = Screen.Home.route){
            HomeScreen(navHostController)
        }
        composable(
            route = Screen.Detail.route + "/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){
            DetailScreen()
        }
    }
}