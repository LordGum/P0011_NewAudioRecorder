package com.example.audionrecorder.presentation.record_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.audionrecorder.data.RepositoryImpl
import com.example.audionrecorder.domain.entities.Record
import com.example.audionrecorder.domain.usecases.AddRecordUseCase
import com.example.audionrecorder.domain.usecases.usecases_file.AddFileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordViewModel(application: Application): AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val addRecordUseCase = AddRecordUseCase(repository)
    private val getPath = repository.getPath()
    private val addFileUseCase = AddFileUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.Default)

    private val _record = MutableLiveData<Record>()
    val record: LiveData<Record>
        get() = _record

    fun addRecord(inputName: String?, inputPath: String?) {
        val name = parseName(inputName)
        val path = parsePath(inputPath)
        val fieldsValid = validateData(name, path)
        if (fieldsValid) {
            scope.launch {
                val record = Record(recName = name, recPath = path)
                addFileUseCase(path)
                addRecordUseCase(record)
            }
        }
    }
    fun getPath():String {
        val path = parsePath(getPath)
        if (path.isNotBlank()) return getPath
        else throw RuntimeException("path is blank")
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }
    private fun parsePath(inputPath: String?): String {
        return inputPath?.trim() ?: ""
    }
    private fun validateData(name: String, path: String): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
        }
        if (path.isBlank()) {
            result = false
        }
        return result
    }


}