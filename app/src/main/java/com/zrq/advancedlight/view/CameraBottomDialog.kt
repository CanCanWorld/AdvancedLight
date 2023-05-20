package com.zrq.advancedlight.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zrq.advancedlight.R
import com.zrq.advancedlight.databinding.DialogBottomCameraBinding
import com.zrq.advancedlight.util.OtherUtils

/**
 * @Description:
 * @author zhangruiqian
 * @date 2023/5/19 16:08
 */
class CameraBottomDialog(
    context: Context,
    private val activity: Activity,
    private val onPhotoClick: () -> Unit,
    private val onVideoClick: () -> Unit
) : BottomSheetDialog(context, R.style.BottomSheetDialog) {

    private lateinit var mBinding: DialogBottomCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogBottomCameraBinding.inflate(LayoutInflater.from(context))
        initData()
        initEvent()
        setContentView(mBinding.root)
        val layoutParams = mBinding.root.layoutParams

        if (window != null) {
            val lp = window?.attributes
            if (lp != null) {
                lp.width = OtherUtils.getWindowWidth(activity)
                lp.height = layoutParams.height
                window!!.attributes = lp
            }
        }
        mBinding.root.layoutParams = layoutParams


        setCanceledOnTouchOutside(true)
    }

    private fun initData() {

    }

    private fun initEvent() {
        mBinding.apply {
            tvPhoto.setOnClickListener {
                onPhotoClick()
                cancel()
            }
            tvVideo.setOnClickListener {
                onVideoClick()
                cancel()
            }
            tvCancel.setOnClickListener {
                cancel()
            }
        }
    }

}