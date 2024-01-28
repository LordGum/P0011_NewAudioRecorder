package com.example.audionrecorder.domain.usecases

import com.example.audionrecorder.domain.Repository
import com.example.audionrecorder.domain.entities.Record
import javax.inject.Inject

class GetRecordInfoUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int): Record = repository.getRecordInfo(id)
}