package com.example.audionrecorder.domain.usecases

import androidx.lifecycle.LiveData
import com.example.audionrecorder.domain.Repository
import com.example.audionrecorder.domain.entities.Record

class GetRecordListUseCase(
    private val repository: Repository
) {
    operator fun invoke(): LiveData<List<Record>> = repository.getRecordList()
}