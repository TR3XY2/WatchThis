package com.watchthis.ui

import android.content.Context
import android.content.SharedPreferences

/**
 * Управління поточним користувачем
 */
object UserManager {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_GOOGLE_ID = "google_id"
    private const val KEY_EMAIL = "email"
    private const val KEY_NAME = "name"

    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setCurrentUser(userId: Int, googleId: String, email: String?, name: String?) {
        prefs?.edit()?.apply {
            putInt(KEY_USER_ID, userId)
            putString(KEY_GOOGLE_ID, googleId)
            putString(KEY_EMAIL, email)
            putString(KEY_NAME, name)
            apply()
        }
    }

    fun getCurrentUserId(): Int = prefs?.getInt(KEY_USER_ID, -1) ?: -1

    fun getCurrentUserIdString(): String = getCurrentUserId().toString()

    fun getCurrentGoogleId(): String? = prefs?.getString(KEY_GOOGLE_ID, null)

    fun getCurrentEmail(): String? = prefs?.getString(KEY_EMAIL, null)

    fun getCurrentName(): String? = prefs?.getString(KEY_NAME, null)

    fun isUserLoggedIn(): Boolean = getCurrentUserId() > 0

    fun logout() {
        prefs?.edit()?.clear()?.apply()
    }
}
