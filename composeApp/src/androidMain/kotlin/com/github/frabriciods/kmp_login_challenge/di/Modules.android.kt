package com.github.frabriciods.kmp_login_challenge.di

import com.github.frabriciods.kmp_login_challenge.domain.repository.LoginRepository
import com.github.frabriciods.kmp_login_challenge.domain.repository.impl.LoginRepositoryImpl
import com.github.frabriciods.kmp_login_challenge.domain.useCase.PostLoginUseCase
import com.github.frabriciods.kmp_login_challenge.networking.service.LoginNetwork
import com.github.frabriciods.kmp_login_challenge.networking.service.LoginNetworkImpl
import com.github.frabriciods.kmp_login_challenge.presentation.login.LoginViewModel
import com.github.frabriciods.kmp_login_challenge.util.UserPreferences
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


 fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp.create()) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation){
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}

actual val targetModule = module {
    singleOf(::UserPreferences)
    single { UserPreferences(get()) }
    single { createHttpClient() }

    single<LoginNetwork> { LoginNetworkImpl(get()) }

    single<LoginRepository> { LoginRepositoryImpl(get()) }

    factory { PostLoginUseCase(get()) }

    viewModel {
        LoginViewModel(
            loginUseCase = get(),
            userPreferences = get()
        )
    }
}