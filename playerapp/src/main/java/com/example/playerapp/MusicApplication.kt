package com.example.playerapp

import android.app.Application
import com.example.data.managers.UserManager
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MusicApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //TODO these lines is hardcoded. Token should get from server side
        UserManager(applicationContext).setToken("Bearer token12345")
        UserManager(applicationContext).setLanguage("Az")
    }
}