package com.github.frabriciods.kmp_login_challenge.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.frabriciods.kmp_login_challenge.domain.model.LoginModel
import com.github.frabriciods.kmp_login_challenge.domain.model.UserModel
import com.github.frabriciods.kmp_login_challenge.domain.useCase.PostLoginUseCase
import com.github.frabriciods.kmp_login_challenge.presentation.common.ScreenState
import com.github.frabriciods.kmp_login_challenge.util.BaseError
import com.github.frabriciods.kmp_login_challenge.util.NetworkBaseError
import com.github.frabriciods.kmp_login_challenge.util.UserPreferences
import com.github.frabriciods.kmp_login_challenge.util.onError
import com.github.frabriciods.kmp_login_challenge.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginUseCase: PostLoginUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {


    var userName by mutableStateOf("")
    var password by mutableStateOf("")
    var isButtonLoading by mutableStateOf(false)
    var isInputStateError by mutableStateOf(false)

    private val _loginState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val loginState = _loginState.asStateFlow()

    fun onUserNameChange(newValue: String) {
        userName = newValue
        isInputStateError = false
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
        isInputStateError = false
    }

    fun makeLogin(

    ) {
        if (userName.isBlank() || password.isBlank())
            return
        viewModelScope.launch {
            isButtonLoading = true
            loginUseCase.execute(
                LoginModel(
                    login = userName,
                    password = password
                )
            ).onSuccess { result ->
                isButtonLoading = false
                handleSuccess(result)
            }.onError { error ->
                isButtonLoading = false
                handleError(error)
            }
        }
    }

    private fun handleSuccess(result: UserModel) {
        saveUser(result.userName)
        _loginState.value = ScreenState.Success
    }

    private fun handleError(
        error: BaseError
    ) {
        if (error == NetworkBaseError.UNAUTHORIZED_ERROR) {
            isInputStateError = true
        } else {
            _loginState.value = ScreenState.Error
        }
    }

    private fun saveUser(userName: String) {
        viewModelScope.launch {
            userPreferences.saveUserName(userName)
        }
    }
}
