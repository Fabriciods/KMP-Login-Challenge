package com.github.frabriciods.kmp_login_challenge.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

expect val targetModule: Module
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()

    modules(targetModule)
}

fun initKoin() = initKoin {}