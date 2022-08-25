package com.zrq.advancedlight.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zrq.advancedlight.R;

@SuppressLint("AppCompatCustomView")
public class InvalidTextView extends TextView {
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mColor;

    public InvalidTextView(Context context) {
        super(context);
        initDraw();
    }

    public InvalidTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.InvalidTextView);
        mColor = typedArray.getColor(R.styleable.InvalidTextView_lineColor, Color.BLACK);
        typedArray.recycle();
        initDraw();
    }

    public InvalidTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    @SuppressLint("ResourceAsColor")
    private void initDraw() {
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(1.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight()/2;
        canvas.drawLine(0, height, width, height, mPaint);
    }
}
