package com.github.frabriciods.kmp_login_challenge.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.github.frabriciods.kmp_login_challenge.presentation.navigation.SetupNavGraph
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
          val navController = rememberNavController()
            SetupNavGraph(navController)
        }
    }

}