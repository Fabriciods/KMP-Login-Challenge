package com.github.frabriciods.kmp_login_challenge.util

import platform.Foundation.NSUserDefaults

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UserPreferences  {
    private val userNSUserDefaults = NSUserDefaults.standardUserDefaults
    actual fun saveUserName(name: String) {
        userNSUserDefaults.setObject(name, forKey = "user_name")
    }

    actual fun getUserName(): String? {
        return userNSUserDefaults.stringForKey("user_name")
    }

    actual fun clear() {
        userNSUserDefaults.removeObjectForKey("user_name")
    }
}