package com.github.frabriciods.kmp_login_challenge.presentation.common

import com.github.frabriciods.kmp_login_challenge.util.BaseError

sealed class ScreenState {
    data object Success : ScreenState()
    data object Loading : ScreenState()
    data object Error : ScreenState()

}