package com.example.audionrecorder.data

import android.app.Application
import android.media.MediaMetadataRetriever
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.audionrecorder.data.database.AppDatabase
import com.example.audionrecorder.data.mappers.Mapper
import com.example.audionrecorder.domain.Repository
import com.example.audionrecorder.domain.entities.Record
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RepositoryImpl(
    application: Application
): Repository {
    private val recordListDao = AppDatabase.getInstance(application).recordDao()
    private val mapper = Mapper()

    override suspend fun addRecord(record: Record) {
        recordListDao.addRecord(mapper.mapEntityToDbModel(record))
    }

    override suspend fun deleteRecord(id: Int) {
        recordListDao.deleteRecord(id)
    }

    override suspend fun getRecordInfo(id: Int): Record {
        return mapper.mapDbModelToEntity(recordListDao.getRecordInfo(id))
    }

    override fun getRecordList(): LiveData<List<Record>> {
        return recordListDao.getRecordList().map {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override suspend fun getFile(path: String): File {
        return File(path)
    }

    override suspend fun addFile(path: String){
        val retriever: MediaMetadataRetriever?
        try {
            retriever = MediaMetadataRetriever()
            retriever.setDataSource(path)
        } catch (error: Exception) {
            throw RuntimeException("Problems with retriever in fun addFile")
        }
    }

    override suspend fun deleteFile(path: String) {
        val file = File(path)
        if (file.exists()) file.delete()
        else throw RuntimeException("Attempt to delete non-existent")
    }

    fun getPath(): String {
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val date = format.format(Date())

        val path: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        val file = File(path, "/recording_$date.arm")

        return file.absolutePath
    }


}