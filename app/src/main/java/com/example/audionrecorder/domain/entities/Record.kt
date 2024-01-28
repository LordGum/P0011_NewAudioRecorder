package com.example.audionrecorder.domain.entities

data class Record (
    val id: Int = UNDEFINED_ID,
    val recName: String,
    val recPath: String,
    var type: Int = ViewTypes.VIEW_TYPE_DISABLE.num
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}