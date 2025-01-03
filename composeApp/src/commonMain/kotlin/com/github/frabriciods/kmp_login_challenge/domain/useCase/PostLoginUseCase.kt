package com.github.frabriciods.kmp_login_challenge.domain.useCase

import com.github.frabriciods.kmp_login_challenge.domain.model.LoginModel
import com.github.frabriciods.kmp_login_challenge.domain.model.UserModel
import com.github.frabriciods.kmp_login_challenge.domain.repository.LoginRepository
import com.github.frabriciods.kmp_login_challenge.util.Result

class PostLoginUseCase(private val repository: LoginRepository) {
    suspend fun execute(loginModel: LoginModel): Result<UserModel> {
        return repository.makeLogin(loginModel)
    }
}