package com.github.frabriciods.kmp_login_challenge.networking.service

import com.github.frabriciods.kmp_login_challenge.networking.models.LoginRequest
import com.github.frabriciods.kmp_login_challenge.util.NetworkBaseError
import com.github.frabriciods.kmp_login_challenge.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoginNetworkTest {

    private fun mockHttpClient(
        responseStatusCode: HttpStatusCode,
        responseBody: String
    ): HttpClient {
        return HttpClient(MockEngine) {
            install(ContentNegotiation){
                json(Json { ignoreUnknownKeys = true })
            }
            engine {
                addHandler {
                    respond(
                        content = ByteReadChannel(responseBody),
                        status = responseStatusCode,
                        headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    )
                }
            }
        }
    }

    @Test
    fun `GIVEN loginNetwork class WHEN makeLogin is success THEN return Result Success`() =
        runTest {
            //Given
            val expectedResponse = """{"userName":"Test"}"""
            val client = mockHttpClient(HttpStatusCode.OK,expectedResponse)
            val loginNetwork = LoginNetworkImpl(client)

            //WHEN
            val result = loginNetwork.makeLogin(LoginRequest(login = "Test", password = "Test"))

            //THEN
            assertTrue(result is Result.Success)
            assertEquals("Test", result.data.userName)
        }

    @Test
    fun `GIVEN loginNetwork class WHEN makeLogin is unauthorized THEN return Result failure`() =
        runTest {
            //Given
            val client = mockHttpClient(HttpStatusCode.Unauthorized,"")
            val loginNetwork = LoginNetworkImpl(client)

            //WHEN
            val result = loginNetwork.makeLogin(LoginRequest(login = "Test", password = "Test"))

            //THEN
            assertTrue(result is Result.Error)
            assertEquals(NetworkBaseError.UNAUTHORIZED_ERROR,result.error)
        }

    @Test
    fun `GIVEN loginNetwork class WHEN makeLogin is failure THEN return Result failure`() =
        runTest {
            //Given
            val client = HttpClient(MockEngine){
                engine {
                    addHandler { throw Exception("Network error") }
                }
            }
            val loginNetwork = LoginNetworkImpl(client)

            //WHEN
            val result = loginNetwork.makeLogin(LoginRequest(login = "Test", password = "Test"))

            //THEN
            assertTrue(result is Result.Error)
            assertEquals(NetworkBaseError.REQUEST_ERROR,result.error)
        }

}