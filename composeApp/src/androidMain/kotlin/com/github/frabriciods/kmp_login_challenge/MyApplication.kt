package com.github.frabriciods.kmp_login_challenge

import android.app.Application
import com.github.frabriciods.kmp_login_challenge.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApplication)
        }
    }
}