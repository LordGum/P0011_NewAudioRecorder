package com.example.audionrecorder.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.audionrecorder.domain.entities.Record

class RecordItemDiffCallback: DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }
}