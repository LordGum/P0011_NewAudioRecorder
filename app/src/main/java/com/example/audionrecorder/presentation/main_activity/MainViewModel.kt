package com.example.audionrecorder.presentation.main_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.audionrecorder.data.RepositoryImpl
import com.example.audionrecorder.domain.entities.ViewTypes
import com.example.audionrecorder.domain.usecases.DeleteRecordUseCase
import com.example.audionrecorder.domain.usecases.GetRecordListUseCase
import com.example.audionrecorder.domain.usecases.usecases_file.DeleteFileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)
    private val deleteRecordUseCase = DeleteRecordUseCase(repository)
    private val deleteFileUseCase = DeleteFileUseCase(repository)
    val recordList = GetRecordListUseCase(repository)
    private val scope = CoroutineScope(Dispatchers.Default)

    fun deleteRecord(id: Int, path: String, type: Int) {
        scope.launch {
            val typeDelete = ViewTypes.VIEW_TYPE_DELETED.num
            deleteRecordUseCase(id)
            if (type != typeDelete) deleteFileUseCase(path)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}