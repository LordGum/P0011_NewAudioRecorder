package com.example.audionrecorder.domain.usecases.usecases_file

import com.example.audionrecorder.domain.Repository

class DeleteFileUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(path: String) = repository.deleteFile(path)
}