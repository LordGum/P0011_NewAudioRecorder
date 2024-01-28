package com.example.audionrecorder.data

import android.media.MediaMetadataRetriever
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.audionrecorder.data.database.RecordListDao
import com.example.audionrecorder.data.mappers.Mapper
import com.example.audionrecorder.domain.Repository
import com.example.audionrecorder.domain.entities.Record
import java.io.File
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val recordListDao: RecordListDao,
    private val mapper: Mapper
): Repository {
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
}