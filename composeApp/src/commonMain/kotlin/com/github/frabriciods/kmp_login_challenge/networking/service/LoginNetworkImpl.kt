package com.github.frabriciods.kmp_login_challenge.networking.service

import com.github.frabriciods.kmp_login_challenge.networking.models.LoginRequest
import com.github.frabriciods.kmp_login_challenge.networking.models.UserResponse
import com.github.frabriciods.kmp_login_challenge.util.NetworkBaseError
import com.github.frabriciods.kmp_login_challenge.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class LoginNetworkImpl(private val client: HttpClient) : LoginNetwork {

    override suspend fun makeLogin(loginRequest: LoginRequest): Result<UserResponse> {
        return try {
            val response = client.post(urlString = getBaseUrl()) {
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    Result.Success(response.body<UserResponse>())
                }

                HttpStatusCode.Unauthorized -> {
                    Result.Error(NetworkBaseError.UNAUTHORIZED_ERROR)
                }

                else -> {
                    Result.Error(NetworkBaseError.REQUEST_ERROR)
                }
            }
        } catch (e: Exception) {
            return Result.Error(NetworkBaseError.REQUEST_ERROR)
        }
    }
}

expect fun getBaseUrl(): String