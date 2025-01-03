package com.github.frabriciods.kmp_login_challenge.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.frabriciods.kmp_login_challenge.domain.model.LoginModel
import com.github.frabriciods.kmp_login_challenge.domain.model.UserModel
import com.github.frabriciods.kmp_login_challenge.domain.useCase.PostLoginUseCase
import com.github.frabriciods.kmp_login_challenge.util.BaseError
import com.github.frabriciods.kmp_login_challenge.util.onError
import com.github.frabriciods.kmp_login_challenge.util.onSuccess
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginUseCase: PostLoginUseCase
) : ViewModel() {


    fun makeLogin(
        loginModel: LoginModel,
        handleSuccess: (UserModel) -> Unit,
        handleError: (BaseError) -> Unit,
        toggleLoading: () -> Unit
    ) {
        viewModelScope.launch {
            toggleLoading()
            loginUseCase.execute(loginModel).onSuccess { result ->
                toggleLoading()
                handleSuccess(result)
            }.onError { error ->
                toggleLoading()
                handleError(error)
            }
        }
    }
}