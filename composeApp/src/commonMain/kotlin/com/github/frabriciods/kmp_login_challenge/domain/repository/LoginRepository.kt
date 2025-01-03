package com.github.frabriciods.kmp_login_challenge.domain.repository

import com.github.frabriciods.kmp_login_challenge.domain.model.LoginModel
import com.github.frabriciods.kmp_login_challenge.domain.model.UserModel
import com.github.frabriciods.kmp_login_challenge.util.Result

interface LoginRepository {
    suspend fun makeLogin(loginModel: LoginModel): Result<UserModel>
}