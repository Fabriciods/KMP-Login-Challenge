package com.github.frabriciods.kmp_login_challenge.presentation.common

sealed class ScreenState {
    data object Success : ScreenState()
    data object Loading : ScreenState()
    data object Error : ScreenState()

}