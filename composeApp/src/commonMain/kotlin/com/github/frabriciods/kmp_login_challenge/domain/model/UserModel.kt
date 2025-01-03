package com.github.frabriciods.kmp_login_challenge.domain.model

import com.github.frabriciods.kmp_login_challenge.networking.models.UserResponse

data class UserModel(
    val userName: String
)

fun UserResponse.toModel() = UserModel(
    userName = this.userName
)