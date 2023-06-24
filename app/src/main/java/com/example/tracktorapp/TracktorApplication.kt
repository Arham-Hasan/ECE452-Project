package com.example.tracktorapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TracktorApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}