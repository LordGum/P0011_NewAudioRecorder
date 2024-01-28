package com.example.audionrecorder.presentation.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.audionrecorder.R
import com.example.audionrecorder.domain.entities.Record
import com.example.audionrecorder.domain.entities.ViewTypes
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecordAdapter(
    private val context: Context
): ListAdapter<Record, RecordViewHolder>(RecordItemDiffCallback()) {

    var onRecordClickListener: ((Record) -> Unit)? = null
    private var currentPosition: Int? = null
    private var mediaPlayer = MediaPlayer()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val layout: Int = when (viewType) {
            VIEW_TYPE_DISABLE -> {
                R.layout.item_disable
            }
            VIEW_TYPE_PAUSE, VIEW_TYPE_ENABLE -> {
                R.layout.item_enable_and_pause
            }
            VIEW_TYPE_DELETED -> {
                R.layout.item_deleted
            }
            else -> {
                throw RuntimeException("Unknown view type: $viewType")
            }
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = getItem(position)
        val file = File(record.recPath)

        when (record.type) {
            VIEW_TYPE_DELETED -> {
                holder.tvName.text = String.format(
                    context.getString(R.string.record_was_deleted),
                    record.recName
                )
            }
            VIEW_TYPE_DISABLE -> {
                setNameDate(holder, record.recName, file)
                holder.view.setOnClickListener {
                    stopPreviousRecord(holder)
                    onRecordClickListener?.invoke(record)
                    record.type = VIEW_TYPE_ENABLE
                    notifyItemChanged(position)
                }
            }
            VIEW_TYPE_ENABLE -> {
                createMediaPlayer(record)
                setNameDate(holder, record.recName, file)
                holder.tvTime.text = timePattern(mediaPlayer.duration)
                holder.image.setBackgroundResource(R.drawable.ic_pause)
                mediaPlayer.start()

                holder.view.setOnClickListener {
                    onRecordClickListener?.invoke(record)
                    record.type = VIEW_TYPE_PAUSE
                    notifyItemChanged(position)
                }
                mediaPlayer.setOnCompletionListener {
                    record.type = ViewTypes.VIEW_TYPE_DISABLE.num
                    notifyItemChanged(position)
                    it.stop()
                }
            }
            VIEW_TYPE_PAUSE -> {
                mediaPlayer.pause()
                setNameDate(holder, record.recName, file)
                holder.tvTime.text = timePattern(mediaPlayer.duration)
                holder.image.setBackgroundResource(R.drawable.ic_play)
                holder.view.setOnClickListener {
                    onRecordClickListener?.invoke(record)
                    record.type = VIEW_TYPE_ENABLE
                    notifyItemChanged(position)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val record = getItem(position)
        val file = File(record.recPath)
        if (!file.exists()) {
            record.type = VIEW_TYPE_DELETED
            return VIEW_TYPE_DELETED
        }
        return when(record.type) {
            ViewTypes.VIEW_TYPE_ENABLE.num -> VIEW_TYPE_ENABLE
            ViewTypes.VIEW_TYPE_PAUSE.num -> VIEW_TYPE_PAUSE
            ViewTypes.VIEW_TYPE_DISABLE.num -> VIEW_TYPE_DISABLE
            else -> throw RuntimeException("Unknown viewType: ${record.type}")
        }
    }
    private fun datePattern(file: File): String? {
        val ft: DateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        var fileDate = ft.format(Date(file.lastModified()))
        val todayDate = ft.format(Date())
        if (fileDate.substring(0, 10) == todayDate.substring(0, 10)) {
            fileDate = String.format(
                context.getString(R.string.date_of_record),
                fileDate.substring(10)
            )
        }

        return fileDate
    }
    private fun timePattern(time: Int): String {
        var sec: String = (time / 1000 % 60).toString()
        var min: String = (time / 60000 % 60).toString()
        val hour: String = (time / 3600000).toString()

        if (min.length < 2) {
            min = "0$min"
        }
        if (sec.length < 2) {
            sec = "0$sec"
        }

        return if (hour == "0") {
            "$min:$sec"
        } else {
            "$hour:$min:$sec"
        }
    }
    private fun stopPreviousRecord(holder: RecordViewHolder) {
        if (currentPosition != null) {
            val previousRecord = getItem(currentPosition!!)
            previousRecord.type = VIEW_TYPE_DISABLE
            notifyItemChanged(currentPosition!!)
            mediaPlayer.stop()
        }
        currentPosition = holder.absoluteAdapterPosition
    }
    private fun setNameDate(holder: RecordViewHolder, name: String, file: File) {
        holder.tvName.text = name
        holder.tvDate.text = datePattern(file)
    }
    private fun createMediaPlayer(record: Record) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        val uri = Uri.parse(record.recPath)
        mediaPlayer = MediaPlayer.create(context, uri)
    }

    companion object {
        const val VIEW_TYPE_DISABLE = 0
        const val VIEW_TYPE_ENABLE = 1
        const val VIEW_TYPE_PAUSE = 2
        const val VIEW_TYPE_DELETED = 3

        const val MAX_POOL_SIZE = 30

        private const val DATE_PATTERN = "dd.MM.yyyy в HH:mm"
    }

}