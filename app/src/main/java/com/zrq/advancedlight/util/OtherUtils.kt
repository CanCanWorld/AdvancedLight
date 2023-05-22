package com.zrq.advancedlight.util

import android.app.Activity
import android.graphics.*
import android.util.DisplayMetrics
import androidx.annotation.ColorInt
import com.zrq.advancedlight.entity.Media
import com.zrq.advancedlight.entity.MediaType
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

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

    fun addWaterMaskByPath(path:String , text: String , @ColorInt color: Int){
        var bitmap = BitmapFactory.decodeFile(path)
        bitmap = addWaterMask(bitmap, text, color)
        val file = File(path)
        val ops = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ops)
    }

    fun addWaterMask(bitmap: Bitmap, text: String, @ColorInt color: Int): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.apply {
            setColor(color)
            textSize = 120f
        }
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)

        var bitmapConfig = bitmap.config
        paint.isDither = true
        paint.isFilterBitmap = true
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888
        }
        val newBitmap = bitmap.copy(bitmapConfig, true)
        val canvas = Canvas(newBitmap)
        val x = (newBitmap.width - bounds.width()) / 2f
        val y = newBitmap.height - bounds.height() / 2f
        canvas.drawText(text, x, y, paint)
        return newBitmap
    }
}