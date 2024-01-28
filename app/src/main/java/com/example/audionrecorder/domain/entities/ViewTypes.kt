package com.example.audionrecorder.domain.entities

enum class ViewTypes(val num : Int) {
    VIEW_TYPE_DISABLE(0),
    VIEW_TYPE_ENABLE(1),
    VIEW_TYPE_PAUSE(2),
    VIEW_TYPE_DELETED(3)
}