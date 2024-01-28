package com.example.audionrecorder.presentation.main_activity

import android.Manifest
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.audionrecorder.databinding.ActivityMainBinding
import com.example.audionrecorder.presentation.adapter.RecordAdapter
import com.example.audionrecorder.presentation.record_activity.RecordActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var recAdapter: RecordAdapter
    private var mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRV()
        askPermissions()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.recordList.invoke().observe(this) {
            recAdapter.submitList(it)
        }

        binding.RecordingButton.setOnClickListener{
            val intent = RecordActivity.newIntent(this)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        recAdapter.notifyDataSetChanged()
    }
    private fun setupRV() {
        val rv = binding.rv
        with(rv) {
            recAdapter = RecordAdapter(applicationContext)
            adapter = recAdapter

            recycledViewPool.setMaxRecycledViews(
                RecordAdapter.VIEW_TYPE_DISABLE,
                RecordAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                RecordAdapter.VIEW_TYPE_ENABLE,
                RecordAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                RecordAdapter.VIEW_TYPE_PAUSE,
                RecordAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                RecordAdapter.VIEW_TYPE_DELETED,
                RecordAdapter.MAX_POOL_SIZE
            )
        }
        setupClickListener()
        setupSwipeListener(rv)
    }
    private fun setupSwipeListener(rv: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback (
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = recAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteRecord(item.id, item.recPath, item.type)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rv)
    }
    private fun askPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (!report.areAllPermissionsGranted()) {
                        Toast.makeText(
                            applicationContext,
                            "Разрешите доступ, пожалуйста", //todo(ask permission)
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest>,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }
    private fun setupClickListener() {
        recAdapter.onRecordClickListener = {

        }
    }
    companion object {
        const val tag = "MATag"
    }
}