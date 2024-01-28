package com.example.audionrecorder.domain

import androidx.lifecycle.LiveData
import com.example.audionrecorder.domain.entities.Record
import java.io.File

interface Repository {
    suspend fun addRecord(record: Record)
    suspend fun deleteRecord(id: Int)
    suspend fun getRecordInfo(id: Int): Record
    fun getRecordList(): LiveData<List<Record>>
    suspend fun getFile(path: String): File
    suspend fun addFile(path: String)
    suspend fun deleteFile(path: String)
}