package com.example.audionrecorder.presentation.record_activity

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.audionrecorder.domain.entities.Record
import com.example.audionrecorder.domain.usecases.AddRecordUseCase
import com.example.audionrecorder.domain.usecases.usecases_file.AddFileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class RecordViewModel @Inject constructor(
    private val addRecordUseCase: AddRecordUseCase,
    private val addFileUseCase: AddFileUseCase
): ViewModel() {

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
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val date = format.format(Date())

        val path: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        val file = File(path, "/recording_$date.arm")

        val getPath = file.absolutePath
        val parsePath = parsePath(getPath)
        if (parsePath.isNotBlank()) return getPath
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