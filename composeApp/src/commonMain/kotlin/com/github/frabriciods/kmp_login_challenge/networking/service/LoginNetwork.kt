package com.github.frabriciods.kmp_login_challenge.networking.service

import com.github.frabriciods.kmp_login_challenge.networking.models.LoginRequest
import com.github.frabriciods.kmp_login_challenge.networking.models.UserResponse
import com.github.frabriciods.kmp_login_challenge.util.Result

interface LoginNetwork {
    suspend fun makeLogin(loginRequest: LoginRequest): Result<UserResponse>
}