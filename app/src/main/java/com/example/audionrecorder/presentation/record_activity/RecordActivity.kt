package com.example.audionrecorder.presentation.record_activity

import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.audionrecorder.R
import com.example.audionrecorder.databinding.ActivityRecordBinding
import com.example.audionrecorder.presentation.RecordApplication
import com.example.audionrecorder.presentation.ViewModelFactory
import com.example.audionrecorder.presentation.main_activity.MainActivity
import java.io.IOException
import javax.inject.Inject

class RecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordBinding
    private lateinit var viewModel: RecordViewModel
    private lateinit var path: String

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as RecordApplication).component
    }

    private var isRecording = false
    private var recorder: MediaRecorder = MediaRecorder()

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[RecordViewModel::class.java]

        try {
            startRecording()
            binding.timeRecord.base = SystemClock.elapsedRealtime()
            binding.timeRecord.start()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, R.string.toast_cant_recording, Toast.LENGTH_SHORT).show()
        }

        binding.RecordingButton.setOnClickListener {
            stopRecording()
            binding.timeRecord.base = SystemClock.elapsedRealtime()
            binding.timeRecord.stop()
            giveName()
        }
    }

    private fun startRecording() {
        path = viewModel.getPath()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
        recorder.setOutputFile(path)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        try {
            recorder.prepare()
        } catch (error: IOException) {
            throw RuntimeException("Error with starting record: $error")
        }
        recorder.start()
        isRecording = true
    }

    private fun stopRecording() {
        recorder.stop()
        recorder.release()
        isRecording = false
    }

    override fun onBackPressed() {
        if (isRecording) {
            Toast.makeText(this, R.string.toast_to_stop_recording, Toast.LENGTH_SHORT)
                .show()
        } else {
            super.onBackPressed()
        }
    }

    private fun giveName() {
        val myDialog = AlertDialog.Builder(this@RecordActivity)
        myDialog.setTitle(R.string.edit_text_dialog)
        myDialog.setCancelable(false)

        val nameInput = EditText(this@RecordActivity)
        nameInput.inputType = InputType.TYPE_CLASS_TEXT
        myDialog.setView(nameInput)

        myDialog.setPositiveButton(R.string.Ok_dialog) { _, _ ->
            val recName = nameInput.text.toString()
            if (recName.trim().isBlank()) {
                Toast.makeText(this, "name is empty", Toast.LENGTH_LONG).show()
                giveName()
            } else {
                viewModel.addRecord(recName, path)
                returnToMainActivity()
            }
        }
        myDialog.setNegativeButton(R.string.cancel_dialog) { dialog, _ ->
            dialog.cancel()
            returnToMainActivity()
        }
        myDialog.show()
    }

    private fun returnToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "RATag"

        fun newIntent(context: Context): Intent {
            return Intent(context, RecordActivity::class.java)
        }
    }
}