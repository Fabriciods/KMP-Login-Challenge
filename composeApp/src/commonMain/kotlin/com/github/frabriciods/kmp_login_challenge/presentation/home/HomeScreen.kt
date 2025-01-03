package com.github.frabriciods.kmp_login_challenge.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.frabriciods.kmp_login_challenge.util.UserPreferences
import kmp_login_challenge.composeapp.generated.resources.Res
import kmp_login_challenge.composeapp.generated.resources.home_screen_button
import kmp_login_challenge.composeapp.generated.resources.home_screen_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    navigateToLogin: () -> Unit,
    userName: String
) {
    val userPreferences = koinInject<UserPreferences>()
    MaterialTheme {
        Column(
            Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "${stringResource(
                    Res.string.home_screen_title)}\n$userName",
                style = MaterialTheme.typography.h6
            )
            Button(onClick = {
                navigateToLogin.invoke()
                userPreferences.clear()
            }
            ) {
                Text(stringResource(Res.string.home_screen_button))
            }
        }
    }
}