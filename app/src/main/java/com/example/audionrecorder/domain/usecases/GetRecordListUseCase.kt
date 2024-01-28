package com.example.audionrecorder.domain.usecases

import androidx.lifecycle.LiveData
import com.example.audionrecorder.domain.Repository
import com.example.audionrecorder.domain.entities.Record
import javax.inject.Inject

class GetRecordListUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): LiveData<List<Record>> = repository.getRecordList()
}