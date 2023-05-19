package com.zrq.advancedlight.activity.kotlin

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.util.Log
import androidx.core.content.FileProvider
import com.zrq.advancedlight.adapter.PicAdapter
import com.zrq.advancedlight.databinding.ActivityCameraBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initData()
        initEvent()
    }

    private fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 110)
        }
        mAdapter = PicAdapter(this)
        mBinding.recyclerView.adapter = mAdapter
    }

    private var videoPath: String? = null
    private var imagePath: String? = null
    private lateinit var mAdapter: PicAdapter

    private fun initEvent() {

        mBinding.btnImage.setOnClickListener {
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
        mBinding.btnVideo.setOnClickListener {
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
    }

    private fun createImageFile(): File? {
        val format = SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(Date().time)
        val fileName = "PIC_$format"
        val file = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return try {
            File.createTempFile(fileName, ".jpg", file)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun createVideoFile(): File? {
        val format = SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(Date().time)
        val fileName = "VIDEO_$format"
        val file = getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        return try {
            File.createTempFile(fileName, ".mp4", file)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE -> {
                    val bitmap = BitmapFactory.decodeFile(imagePath)
                    mBinding.ivImage.setImageBitmap(bitmap)
                }

                REQUEST_CODE_VIDEO -> {
                    val media = MediaMetadataRetriever()
                    media.setDataSource(videoPath)
                    mBinding.ivVideo.setImageBitmap(media.frameAtTime)
                }

                else -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        imagePath?.let {
            File(it).delete()
        }
        videoPath?.let {
            File(it).delete()
        }
    }

    companion object {
        private const val TAG = "CameraActivity"
        const val REQUEST_CODE_IMAGE = 1
        const val REQUEST_CODE_VIDEO = 2
        const val FILE_PROVIDER_AUTHORITY = "com.zrq.advancedlight.fileprovider"
    }
}