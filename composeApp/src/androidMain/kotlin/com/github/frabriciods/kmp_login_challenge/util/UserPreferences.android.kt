package com.github.frabriciods.kmp_login_challenge.util

import android.content.Context
import android.content.SharedPreferences


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UserPreferences(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    actual fun saveUserName(name: String) {
        sharedPreferences.edit().putString("user_name", name).apply()
    }

    actual fun getUserName(): String? {
        return sharedPreferences.getString("user_name", null)
    }

    actual fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}