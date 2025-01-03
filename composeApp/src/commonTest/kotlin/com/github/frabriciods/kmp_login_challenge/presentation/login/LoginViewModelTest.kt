package com.github.frabriciods.kmp_login_challenge.presentation.login

import com.github.frabriciods.kmp_login_challenge.domain.model.LoginModel
import com.github.frabriciods.kmp_login_challenge.domain.model.UserModel
import com.github.frabriciods.kmp_login_challenge.domain.repository.LoginRepository
import com.github.frabriciods.kmp_login_challenge.domain.useCase.PostLoginUseCase
import com.github.frabriciods.kmp_login_challenge.util.NetworkBaseError
import com.github.frabriciods.kmp_login_challenge.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private class FakeLoginRepository(private val response: Result<UserModel>) : LoginRepository {
        override suspend fun makeLogin(loginModel: LoginModel): Result<UserModel> {
            return response
        }
    }

    private fun createUseCase(response: Result<UserModel>): PostLoginUseCase {
        val fakeRepository = FakeLoginRepository(response)
        return PostLoginUseCase(fakeRepository)
    }

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `GIVEN loginViewModel class WHEN makeLogin is successful THEN successful handle`() = runTest {
       //GIVEN
        val useCase = createUseCase(Result.Success(UserModel("Test")))
        val viewModel = LoginViewModel(useCase)

        //WHEN
        var successCalled = false
        var loadingToggled = 0

        viewModel.makeLogin(
            loginModel = LoginModel("Test", "Test"),
            handleSuccess = {
                successCalled = true
                assertEquals("Test", it.userName)
            },
            handleError = {  },
            toggleLoading = {loadingToggled++}
        )

        testDispatcher.scheduler.advanceUntilIdle()
        //THEN
        assertTrue(successCalled)
        assertEquals(2, loadingToggled)
    }

    @Test
    fun `GIVEN loginViewModel class WHEN makeLogin is failure THEN handle error`() = runTest {
        //GIVEN
        val useCase = createUseCase(Result.Error(NetworkBaseError.REQUEST_ERROR))
        val viewModel = LoginViewModel(useCase)

        //WHEN
        var errorCalled = false
        var loadingToggled = 0

        viewModel.makeLogin(
            loginModel = LoginModel("Test", "Test"),
            handleSuccess = {},
            handleError = {
                errorCalled = true
                assertEquals(NetworkBaseError.REQUEST_ERROR, it)
            },
            toggleLoading = {loadingToggled++}
        )

        testDispatcher.scheduler.advanceUntilIdle()
        //THEN
        assertTrue(errorCalled)
        assertEquals(2, loadingToggled)
    }
}