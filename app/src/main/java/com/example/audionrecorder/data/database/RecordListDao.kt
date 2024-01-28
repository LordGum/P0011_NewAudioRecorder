package com.example.audionrecorder.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.audionrecorder.data.database.model.RecordDbModel

@Dao
interface RecordListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecord(record: RecordDbModel)

    @Query("DELETE FROM record_list WHERE id=:recordId")
    suspend fun deleteRecord(recordId: Int)

    @Query("SELECT * FROM record_list WHERE id=:recordId")
    suspend fun getRecordInfo(recordId: Int): RecordDbModel

    @Query("SELECT * FROM record_list ORDER BY id")
    fun getRecordList(): LiveData<List<RecordDbModel>>
}