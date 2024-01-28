package com.example.audionrecorder.presentation.main_activity

import androidx.lifecycle.ViewModel
import com.example.audionrecorder.domain.entities.ViewTypes
import com.example.audionrecorder.domain.usecases.DeleteRecordUseCase
import com.example.audionrecorder.domain.usecases.GetRecordListUseCase
import com.example.audionrecorder.domain.usecases.usecases_file.DeleteFileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val deleteRecordUseCase: DeleteRecordUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    val recordList: GetRecordListUseCase
): ViewModel() {

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