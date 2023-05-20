package com.zrq.advancedlight.activity.kotlin

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.zrq.advancedlight.adapter.PicAdapter
import com.zrq.advancedlight.databinding.ActivityCameraBinding
import com.zrq.advancedlight.entity.Media
import com.zrq.advancedlight.entity.MediaType
import com.zrq.advancedlight.view.CameraBottomDialog
import com.zrq.advancedlight.view.PictureDialog
import com.zrq.advancedlight.view.VideoDialog
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
        mAdapter = PicAdapter(this, list,
            //onDelete
            { position ->
                File(list[position].path).delete()
                list.removeAt(position)
                mAdapter.notifyDataSetChanged()
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

    private var videoPath: String? = null
    private var imagePath: String? = null
    private lateinit var mAdapter: PicAdapter
    private val list = mutableListOf<Media>()

    private fun initEvent() {
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
            //TODO:没有拍照或者录视频将不去创建
            when (requestCode) {
                REQUEST_CODE_IMAGE -> {
                    imagePath?.let {
                        list.add(Media(it, MediaType.Photo))
                    }
                    mAdapter.notifyDataSetChanged()
                    mBinding.recyclerView.scrollToPosition(list.size)
                }

                REQUEST_CODE_VIDEO -> {
                    videoPath?.let {
                        list.add(Media(it, MediaType.Video))
                    }
                    mAdapter.notifyDataSetChanged()
                    mBinding.recyclerView.scrollToPosition(list.size)
                }

                else -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        list.forEach {
            File(it.path).delete()
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