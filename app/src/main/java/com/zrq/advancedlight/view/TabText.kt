package com.zrq.advancedlight.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView

@SuppressLint("AppCompatCustomView")
class TabText : TextView {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val rectColumn = Rect(0, 0, paddingLeft, height - paddingBottom)
            val rectRow = Rect(0, height - paddingBottom, width, height)
            val paint = Paint().apply {
                color = Color.GREEN
            }
            it.drawRect(rectColumn, paint)
            it.drawRect(rectRow, paint)
        }
    }


    companion object {
        private const val TAG = "TabText"
    }
}