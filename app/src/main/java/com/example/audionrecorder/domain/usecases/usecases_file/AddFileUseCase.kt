package com.example.audionrecorder.domain.usecases.usecases_file

import com.example.audionrecorder.domain.Repository
import javax.inject.Inject

class AddFileUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(path: String) = repository.addFile(path)
}