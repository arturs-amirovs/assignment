package com.example.f3test

import android.content.SharedPreferences
import javax.inject.Inject

class Preferences @Inject constructor(private val prefs: SharedPreferences) {

    companion object {
        @Volatile private var instance: Preferences? = null

        fun getInstance(prefs: SharedPreferences) =
            instance ?: synchronized(this) {
                instance ?: Preferences(prefs).also { instance = it }
            }
    }

    interface Keys {
        companion object {
            const val FB_USER_ID = "FB_USER_ID"
            const val FB_TOKEN = "FB_TOKEN"
            const val PHOTO_URL = "PHOTO_URL"
        }
    }

    fun setUserID(token: String?) {
        prefs.edit().putString(Keys.FB_USER_ID, token).apply()
    }

    fun getUserID(): String? {
        return prefs.getString(Keys.FB_USER_ID, "")
    }

    fun setToken(token: String?) {
        prefs.edit().putString(Keys.FB_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(Keys.FB_TOKEN, "")
    }

    fun setPhotoUrl(url: String?) {
        prefs.edit().putString(Keys.PHOTO_URL, url).apply()
    }

    fun getPhotoUrl(): String? {
        return prefs.getString(Keys.PHOTO_URL, null)
    }
}