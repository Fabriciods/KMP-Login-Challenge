package com.github.frabriciods.kmp_login_challenge

import androidx.compose.ui.window.ComposeUIViewController
import com.github.frabriciods.kmp_login_challenge.di.initKoin
import com.github.frabriciods.kmp_login_challenge.presentation.App
import com.github.frabriciods.kmp_login_challenge.presentation.login.LoginScreen

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }