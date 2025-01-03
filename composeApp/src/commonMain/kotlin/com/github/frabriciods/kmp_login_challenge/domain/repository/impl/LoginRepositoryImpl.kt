package com.github.frabriciods.kmp_login_challenge.domain.repository.impl

import com.github.frabriciods.kmp_login_challenge.domain.model.LoginModel
import com.github.frabriciods.kmp_login_challenge.domain.model.UserModel
import com.github.frabriciods.kmp_login_challenge.domain.model.toModel
import com.github.frabriciods.kmp_login_challenge.domain.model.toRequest
import com.github.frabriciods.kmp_login_challenge.domain.repository.LoginRepository
import com.github.frabriciods.kmp_login_challenge.networking.service.LoginNetwork
import com.github.frabriciods.kmp_login_challenge.util.Result
import com.github.frabriciods.kmp_login_challenge.util.map

class LoginRepositoryImpl(
    private val network: LoginNetwork
) : LoginRepository {
    override suspend fun makeLogin(loginModel: LoginModel): Result<UserModel> {
        return network.makeLogin(loginModel.toRequest()).map { response ->
            response.toModel()
        }
    }
}