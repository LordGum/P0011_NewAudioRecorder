package com.example.audionrecorder.domain.usecases

import com.example.audionrecorder.domain.Repository
import com.example.audionrecorder.domain.entities.Record

class GetRecordInfoUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int): Record = repository.getRecordInfo(id)
}