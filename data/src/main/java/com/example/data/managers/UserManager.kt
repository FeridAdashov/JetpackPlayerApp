package com.example.data.managers

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Base64
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import java.lang.ref.WeakReference
import java.util.Locale

class UserManager(context: Context) {

    private val mContext = WeakReference(context)

    fun getLanguage(): String? {
        return PreferenceManager.getDefaultSharedPreferences(mContext.get()!!)
            .getString(LANGUAGE, null)
    }

    fun getDefaultLanguage(): String {
        return "az"
    }

    fun setLanguage(language: String) {
        PreferenceManager.getDefaultSharedPreferences(mContext.get()!!).edit {
            putString(LANGUAGE, language)
        }
    }

    fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun getToken(): String {
        val token =
            PreferenceManager.getDefaultSharedPreferences(mContext.get()!!).getString(TOKEN, "")
        return String(Base64.decode(token!!.toByteArray(), Base64.CRLF))
    }

    fun setToken(token: String) {
        PreferenceManager.getDefaultSharedPreferences(mContext.get()!!).edit {
            putString(TOKEN, Base64.encodeToString(token.toByteArray(), Base64.DEFAULT))
        }
    }

    companion object {
        private const val LANGUAGE = "language"
        private const val TOKEN = "token"
    }
}