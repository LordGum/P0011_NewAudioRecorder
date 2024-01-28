package com.example.audionrecorder.domain.usecases

import com.example.audionrecorder.domain.Repository

class DeleteRecordUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int) = repository.deleteRecord(id)
}