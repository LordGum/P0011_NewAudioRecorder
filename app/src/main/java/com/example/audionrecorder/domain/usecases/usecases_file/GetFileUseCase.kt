package com.example.audionrecorder.domain.usecases.usecases_file

import com.example.audionrecorder.domain.Repository

class GetFileUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(path: String) = repository.getFile(path)
}