package com.zrq.advancedlight.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.zrq.advancedlight.databinding.DialogPictureBinding
import com.zrq.advancedlight.util.OtherUtils

/**
 * @Description:
 * @author zhangruiqian
 * @date 2023/5/19 15:17
 */
class PictureDialog(
    context: Context,
    private val activity: Activity,
    private val path: String
) :Dialog(context){
    private lateinit var mBinding : DialogPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setGravity(Gravity.CENTER)
        mBinding = DialogPictureBinding.inflate(LayoutInflater.from(context))
        initData()
        initEvent()
        setContentView(mBinding.root)
        setCanceledOnTouchOutside(true)
        if (window != null) {
            val lp = window?.attributes
            if (lp != null) {
                lp.width = OtherUtils.getWindowWidth(activity) / 8 * 7
                window!!.attributes = lp
            }
        }
    }

    private fun initData() {
        Log.d("TAG", "initData: $path")
        Glide.with(context).load(path).into(mBinding.ivCover)
    }

    private fun initEvent() {

    }

}