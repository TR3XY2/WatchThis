package com.watchthis.ui

import android.app.Application

class WatchThisApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LocaleHelper.applySavedLocale(this)
    }
}
