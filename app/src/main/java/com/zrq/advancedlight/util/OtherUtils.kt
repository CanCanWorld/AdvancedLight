package com.zrq.advancedlight.util

import android.app.Activity
import android.util.DisplayMetrics

object OtherUtils {

    const val TAG = "OtherUtils"

    fun getWindowWidth(context: Activity): Int {
        val display = context.windowManager.defaultDisplay
        val dm = DisplayMetrics()
        display.getRealMetrics(dm)
        return dm.widthPixels
    }

    fun getWindowHeight(context: Activity): Int {
        val display = context.windowManager.defaultDisplay
        val dm = DisplayMetrics()
        display.getRealMetrics(dm)
        return dm.heightPixels
    }

}