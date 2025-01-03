package com.github.frabriciods.kmp_login_challenge.util

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class UserPreferences {
     fun saveUserName(name: String)
     fun getUserName(): String?
     fun clear()
}

