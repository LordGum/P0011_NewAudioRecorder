package com.example.audionrecorder.di

import androidx.lifecycle.ViewModel
import com.example.audionrecorder.presentation.main_activity.MainViewModel
import com.example.audionrecorder.presentation.record_activity.RecordViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecordViewModel::class)
    fun bindRecordViewModel(viewModel: RecordViewModel): ViewModel
}