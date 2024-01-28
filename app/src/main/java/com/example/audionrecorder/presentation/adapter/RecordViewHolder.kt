package com.example.audionrecorder.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.audionrecorder.R

class RecordViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val tvName = view.findViewById<TextView>(R.id.tv_RecordName)
    val tvDate = view.findViewById<TextView>(R.id.tv_date)
    val tvTime = view.findViewById<TextView>(R.id.tv_time)
    val image = view.findViewById<ImageView>(R.id.image_button)
}