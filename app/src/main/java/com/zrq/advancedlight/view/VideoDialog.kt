package com.zrq.advancedlight.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import com.zrq.advancedlight.databinding.DialogVideoBinding
import com.zrq.advancedlight.util.OtherUtils
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videocontroller.component.CompleteView
import xyz.doikki.videocontroller.component.ErrorView
import xyz.doikki.videocontroller.component.GestureView
import xyz.doikki.videocontroller.component.PrepareView
import xyz.doikki.videocontroller.component.TitleView

/**
 * @Description:
 * @author zhangruiqian
 * @date 2023/5/19 15:17
 */
class VideoDialog(
    context: Context,
    private val activity: Activity,
    private val path: String
) : Dialog(context) {
    private lateinit var mBinding: DialogVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setGravity(Gravity.CENTER)
        mBinding = DialogVideoBinding.inflate(LayoutInflater.from(context))
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
        val controller = StandardVideoController(context)

        val completeView = CompleteView(context)
        val errorView = ErrorView(context)
        val prepareView = PrepareView(context)
        prepareView.setClickStart()
        val titleView = TitleView(context)
        titleView.setTitle("视频")
        controller.addControlComponent(completeView, errorView, prepareView, titleView)
        controller.addControlComponent(MyVodControlView(context))
        controller.addControlComponent(GestureView(context))
        controller.setCanChangePosition(true)

        mBinding.videoView.apply {
            setVideoController(controller)
            release()
            setUrl(path)
            start()
        }
    }

    private fun initEvent() {

    }

}