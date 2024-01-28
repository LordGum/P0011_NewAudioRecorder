package com.example.audionrecorder.domain.usecases

import com.example.audionrecorder.domain.Repository
import com.example.audionrecorder.domain.entities.Record

class AddRecordUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(record: Record) = repository.addRecord(record)
}