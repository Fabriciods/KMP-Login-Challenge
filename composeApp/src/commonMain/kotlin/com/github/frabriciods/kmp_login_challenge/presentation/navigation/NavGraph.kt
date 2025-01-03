package com.github.frabriciods.kmp_login_challenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.frabriciods.kmp_login_challenge.presentation.home.HomeScreen
import com.github.frabriciods.kmp_login_challenge.presentation.login.LoginScreen
import com.github.frabriciods.kmp_login_challenge.util.UserPreferences
import org.koin.compose.koinInject


@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val userPreferences = koinInject<UserPreferences>()
    NavHost(
        startDestination = setStartDestination(userPreferences.getUserName()),
        navController = navController
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                navigateToLogin = {
                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.Home.route){ inclusive = true }
                    }
                },
                userName = userPreferences.getUserName().orEmpty()
            )
        }

    }
}

private fun setStartDestination(userName: String?): String {
    if (userName.isNullOrBlank())
        return Screen.Login.route
    return Screen.Home.route
}