package com.github.frabriciods.kmp_login_challenge.domain.useCase

import com.github.frabriciods.kmp_login_challenge.domain.model.LoginModel
import com.github.frabriciods.kmp_login_challenge.domain.model.UserModel
import com.github.frabriciods.kmp_login_challenge.domain.repository.LoginRepository
import com.github.frabriciods.kmp_login_challenge.util.NetworkBaseError
import com.github.frabriciods.kmp_login_challenge.util.Result
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PostLoginUseCaseTest {
    class FakeLoginRepository(private val result: Result<UserModel>) : LoginRepository {
        override suspend fun makeLogin(loginModel: LoginModel): Result<UserModel> {
            return result
        }
    }

    @Test
    fun `GIVEN postLoginUseCase class WHEN makeLogin is successful THEN return UserModel`() =
        runTest {
            //GIVEN
            val fakeRepository = FakeLoginRepository(Result.Success(UserModel("Test")))
            val useCase = PostLoginUseCase(fakeRepository)

            //WHEN
            val result = useCase.execute(LoginModel("Test", "Test"))

            //THEN
            assertTrue(result is Result.Success)
            assertEquals("Test", result.data.userName)
        }

    @Test
    fun `GIVEN postLoginUseCase class WHEN makeLogin is unauthorized THEN return error`() =
        runTest {
            //GIVEN
            val fakeRepository = FakeLoginRepository(Result.Error(NetworkBaseError.UNAUTHORIZED_ERROR))
            val useCase = PostLoginUseCase(fakeRepository)

            //WHEN
            val result = useCase.execute(LoginModel("Test", "Test"))

            //THEN
            assertTrue(result is Result.Error)
            assertEquals(NetworkBaseError.UNAUTHORIZED_ERROR, result.error)
        }

    @Test
    fun `GIVEN postLoginUseCase class WHEN makeLogin is failure THEN return error`() =
        runTest {
            //GIVEN
            val fakeRepository = FakeLoginRepository(Result.Error(NetworkBaseError.REQUEST_ERROR))
            val useCase = PostLoginUseCase(fakeRepository)

            //WHEN
            val result = useCase.execute(LoginModel("Test", "Test"))

            //THEN
            assertTrue(result is Result.Error)
            assertEquals(NetworkBaseError.REQUEST_ERROR, result.error)
        }
}