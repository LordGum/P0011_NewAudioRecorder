package com.example.audionrecorder.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_list")
data class RecordDbModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val recName: String,
    val recPath: String,
)