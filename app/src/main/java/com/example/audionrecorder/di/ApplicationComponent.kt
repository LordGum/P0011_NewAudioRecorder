package com.example.audionrecorder.di

import android.app.Application
import com.example.audionrecorder.presentation.main_activity.MainActivity
import com.example.audionrecorder.presentation.record_activity.RecordActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: RecordActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}