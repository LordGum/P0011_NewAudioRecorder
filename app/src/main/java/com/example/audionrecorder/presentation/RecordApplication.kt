package com.example.audionrecorder.presentation

import android.app.Application
import com.example.audionrecorder.di.DaggerApplicationComponent

class RecordApplication : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}