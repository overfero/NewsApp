package com.example.login.util

sealed class Screen(val route: String) {
    object Login: Screen(route = "login")
    object SignUp: Screen(route = "signup")
    object PrivacyPolicy: Screen(route = "privacy_policy")
    object TermOfUse: Screen(route = "term_of_use")
    object Home: Screen(route = "home")
    object Detail: Screen(route = "detail")
    object NewsList: Screen(route = "newslist")
    object Bookmark: Screen(route = "bookmark")
    object Discover: Screen(route = "discover")
    object Setting: Screen(route = "setting")
}