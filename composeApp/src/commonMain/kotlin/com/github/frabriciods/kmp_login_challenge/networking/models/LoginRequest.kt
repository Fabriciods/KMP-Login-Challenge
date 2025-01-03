package com.github.frabriciods.kmp_login_challenge.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val login: String,
    val password: String
)
