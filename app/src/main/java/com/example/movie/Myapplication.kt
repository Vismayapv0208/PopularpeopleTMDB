package com.example.movie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Example: Initialize third-party libraries (e.g., crashlytics, logging, etc.)
        // Crashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        // SomeAnalyticsLibrary.initialize(this)
    }
}
