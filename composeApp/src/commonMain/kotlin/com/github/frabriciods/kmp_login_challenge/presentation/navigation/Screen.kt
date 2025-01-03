package com.github.frabriciods.kmp_login_challenge.presentation.navigation

sealed class Screen(val route: String){
    data object Login: Screen(route = "login")
    data object Home: Screen(route = "home")
}