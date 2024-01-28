package com.example.audionrecorder.di

import android.app.Application
import com.example.audionrecorder.data.RepositoryImpl
import com.example.audionrecorder.data.database.AppDatabase
import com.example.audionrecorder.data.database.RecordListDao
import com.example.audionrecorder.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {
        @ApplicationScope
        @Provides
        fun provideToDoDao(
            application: Application
        ): RecordListDao {
            return  AppDatabase.getInstance(application).recordDao()
        }
    }
}