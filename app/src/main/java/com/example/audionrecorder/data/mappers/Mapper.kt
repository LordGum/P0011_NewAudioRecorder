package com.example.audionrecorder.data.mappers

import com.example.audionrecorder.data.database.model.RecordDbModel
import com.example.audionrecorder.domain.entities.Record
import com.example.audionrecorder.domain.entities.ViewTypes

class Mapper {
    fun mapDbModelToEntity(record: RecordDbModel) = Record(
        record.id,
        record.recName,
        record.recPath,
        ViewTypes.VIEW_TYPE_DISABLE.num
    )

    fun mapEntityToDbModel(record: Record) = RecordDbModel(
        record.id,
        record.recName,
        record.recPath
    )

}