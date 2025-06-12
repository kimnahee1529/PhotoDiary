package com.todaylab.cleanarchtemplate

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodayLuckyApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}