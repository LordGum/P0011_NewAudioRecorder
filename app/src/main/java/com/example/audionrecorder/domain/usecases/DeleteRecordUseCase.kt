package com.example.audionrecorder.domain.usecases

import com.example.audionrecorder.domain.Repository
import javax.inject.Inject

class DeleteRecordUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int) = repository.deleteRecord(id)
}