package com.zrq.advancedlight.activity.kotlin

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.ReturnCode
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.zrq.advancedlight.R
import com.zrq.advancedlight.adapter.MediaAdapter
import com.zrq.advancedlight.databinding.ActivityCameraBinding
import com.zrq.advancedlight.entity.Media
import com.zrq.advancedlight.entity.MediaType
import com.zrq.advancedlight.util.OtherUtils
import com.zrq.advancedlight.view.CameraBottomDialog
import com.zrq.advancedlight.view.LoadingDialog
import com.zrq.advancedlight.view.PictureDialog
import com.zrq.advancedlight.view.VideoDialog
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCameraBinding
    private var videoPath: String? = null
    private var imagePath: String? = null
    private lateinit var mAdapter: MediaAdapter
    private val list = mutableListOf<Media>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initData()
        initEvent()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), 110)
        mAdapter = MediaAdapter(this, list,
            //onDelete
            { position ->
                val removeAt = list.removeAt(position)
                mAdapter.notifyDataSetChanged()
                Snackbar.make(window.decorView, "删除成功", Snackbar.LENGTH_LONG)
                    .setAction("撤回") {
                        list.add(position, removeAt)
                        mAdapter.notifyDataSetChanged()
                    }
                    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                                File(removeAt.path).delete()
                            }
                        }
                    })
                    .show()
            },
            //onAdd
            {
                val bottomDialog = CameraBottomDialog(this, this, {
                    addImage()
                }, {
                    addVideo()
                })

                bottomDialog.behavior.apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                }
                bottomDialog.show()
            },
            //onItemClick
            { position ->
                val media = list[position]
                when (media.type) {
                    MediaType.Photo -> {
                        val dialog = PictureDialog(this, this, media.path)
                        dialog.show()
                    }
                    MediaType.Video -> {
                        val dialog = VideoDialog(this, this, media.path)
                        dialog.show()
                    }
                }
            })
        mBinding.recyclerView.adapter = mAdapter
    }

    private fun addImage() {
        val intent = Intent()
        intent.action = ACTION_IMAGE_CAPTURE
        intent.apply {
            createImageFile()?.let { file ->
                imagePath = file.absolutePath
                Log.d(TAG, "filePath: ${file.absolutePath}")
                val imageUri = FileProvider.getUriForFile(this@CameraActivity, FILE_PROVIDER_AUTHORITY, file)
                putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            }
        }
        startActivityForResult(intent, REQUEST_CODE_IMAGE)
    }

    private fun addVideo() {
        val intent = Intent()
        intent.action = MediaStore.ACTION_VIDEO_CAPTURE
        intent.apply {
            createVideoFile()?.let { file ->
                videoPath = file.absolutePath
                Log.d(TAG, "videoPath: $videoPath")
                val videoUri = FileProvider.getUriForFile(this@CameraActivity, FILE_PROVIDER_AUTHORITY, file)
                putExtra(MediaStore.EXTRA_OUTPUT, videoUri)
            }
        }
        startActivityForResult(intent, REQUEST_CODE_VIDEO)
    }

    private fun initEvent() {
        Thread {
            File(FILE_MOVIES).listFiles()?.forEach {
                it.delete()
            }
            File(FILE_PICTURES).listFiles()?.forEach {
                it.delete()
            }
        }.start()

        mBinding.apply {
            tvNormal.setOnClickListener {
                tvNormal.isSelected = true
                tvError.isSelected = false
                tvNormal.setTextColor(Color.WHITE)
                tvError.setTextColor(resources.getColor(R.color.blue))
                cardView.visibility = View.GONE
            }
            tvError.setOnClickListener {
                tvError.isSelected = true
                tvNormal.isSelected = false
                tvError.setTextColor(Color.WHITE)
                tvNormal.setTextColor(resources.getColor(R.color.blue))
                cardView.visibility = View.VISIBLE
            }
            cardView.setOnClickListener {
                Log.d(TAG, "requestFocus")
                etDesc.requestFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(mBinding.etDesc, 0)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (currentFocus != mBinding.cardView) {
                    Log.d(TAG, "clearFocus")
                    mBinding.etDesc.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(mBinding.etDesc.windowToken, 0)
                }
            }
            else -> {}
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun createImageFile(): File? {
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(Date())
        val fileName = "PIC_$format.jpg"
        val file = File(FILE_PICTURES)
        return try {
            File(file, fileName)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun createVideoFile(): File? {
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(Date())
        val fileName = "VIDEO_$format.mp4"
        val file = File(FILE_MOVIES)
        return try {
            File(file, fileName)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                when (requestCode) {
                    REQUEST_CODE_IMAGE -> {
                        imagePath?.let {
                            Log.d(TAG, "onActivityResult: $it")
                            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Date())
                            OtherUtils.addWaterMaskByPath(it, time, Color.RED)
                            list.add(Media(it, MediaType.Photo))
                            mAdapter.notifyDataSetChanged()
                            mBinding.recyclerView.scrollToPosition(list.size)
                        }
                    }

                    REQUEST_CODE_VIDEO -> {
                        videoPath?.let {
                            Log.d(TAG, "videoPath: $it")
                            val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(Date())
                            val waterPath = "$FILE_PICTURES/water_$format.png"
                            val outPath = "$FILE_MOVIES/out_$format.mp4"
                            val strCommand = "-y -i $it -i $waterPath -filter_complex [0:v]scale=iw:ih[outv0];[1:0]scale=0.0:0.0[outv1];[outv0][outv1]overlay=W/2-w/2:H-h -preset superfast $outPath"
                            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Date())
                            OtherUtils.createImageByText(waterPath, time, Color.RED)
                            val loadingDialog = LoadingDialog(this)
                            loadingDialog.show()
                            //开始执行FFmpeg命令
                            FFmpegKit.executeAsync(strCommand) { session ->
                                loadingDialog.dismiss()
                                runOnUiThread {
                                    if (ReturnCode.isSuccess(session.returnCode)) {
                                        Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show()
                                        list.add(Media(it, MediaType.Video))
//                                        list.add(Media(outPath, MediaType.Video)) 不支持播放
                                        mAdapter.notifyDataSetChanged()
                                        mBinding.recyclerView.scrollToPosition(list.size)
                                    } else if (ReturnCode.isCancel(session.returnCode)) {
                                        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    companion object {
        private const val TAG = "CameraActivity"
        const val REQUEST_CODE_IMAGE = 1
        const val REQUEST_CODE_VIDEO = 2
        const val FILE_PROVIDER_AUTHORITY = "com.zrq.advancedlight.fileprovider"

        const val FILE_PICTURES = "/storage/emulated/0/Android/data/com.zrq.advancedlight/files/Pictures"
        const val FILE_MOVIES = "/storage/emulated/0/Android/data/com.zrq.advancedlight/files/Movies"
    }
}