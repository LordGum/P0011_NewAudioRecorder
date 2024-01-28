package com.example.audionrecorder.domain.usecases

import com.example.audionrecorder.domain.Repository
import com.example.audionrecorder.domain.entities.Record
import javax.inject.Inject

class AddRecordUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(record: Record) = repository.addRecord(record)
}