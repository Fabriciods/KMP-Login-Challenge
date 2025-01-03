package com.github.frabriciods.kmp_login_challenge.domain.repository.impl

import com.github.frabriciods.kmp_login_challenge.domain.model.LoginModel
import com.github.frabriciods.kmp_login_challenge.networking.models.LoginRequest
import com.github.frabriciods.kmp_login_challenge.networking.models.UserResponse
import com.github.frabriciods.kmp_login_challenge.networking.service.LoginNetwork
import com.github.frabriciods.kmp_login_challenge.util.NetworkBaseError
import com.github.frabriciods.kmp_login_challenge.util.Result
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoginRepositoryTest {

    class FakeLoginNetwork(private val result: Result<UserResponse>) : LoginNetwork {
        override suspend fun makeLogin(loginRequest: LoginRequest): Result<UserResponse> {
            return result
        }
    }

    @Test
    fun `GIVEN loginRepository class WHEN makeLogin is success THEN return Result Success`() =
        runTest {
            //Given
            val fakeLoginNetwork = FakeLoginNetwork(Result.Success(UserResponse("Test")))
            val repository = LoginRepositoryImpl(fakeLoginNetwork)

            //WHEN
            val result = repository.makeLogin(LoginModel("Test", "Test"))

            //THEN
            assertTrue(result is Result.Success)
            assertEquals("Test", result.data.userName)
        }

    @Test
    fun `GIVEN loginRepository class WHEN makeLogin is unauthorized THEN return Result Failure`() =
        runTest {
            //Given
            val fakeLoginNetwork =
                FakeLoginNetwork(Result.Error(NetworkBaseError.UNAUTHORIZED_ERROR))
            val repository = LoginRepositoryImpl(fakeLoginNetwork)

            //WHEN
            val result = repository.makeLogin(LoginModel("Test", "Test"))

            //THEN
            assertTrue(result is Result.Error)
            assertEquals(NetworkBaseError.UNAUTHORIZED_ERROR, result.error)
        }

    @Test
    fun `GIVEN loginRepository class WHEN makeLogin is failure THEN return Result Failure`() =
        runTest {
            //Given
            val fakeLoginNetwork = FakeLoginNetwork(Result.Error(NetworkBaseError.REQUEST_ERROR))
            val repository = LoginRepositoryImpl(fakeLoginNetwork)

            //WHEN
            val result = repository.makeLogin(LoginModel("Test", "Test"))

            //THEN
            assertTrue(result is Result.Error)
            assertEquals(NetworkBaseError.REQUEST_ERROR, result.error)
        }
}
