package com.github.frabriciods.kmp_login_challenge.domain.model

import com.github.frabriciods.kmp_login_challenge.networking.models.LoginRequest

data class LoginModel(
    val login: String,
    val password: String,
)

fun LoginModel.toRequest() = LoginRequest(
    login = this.login,
    password = this.password
)