package com.zrq.advancedlight.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zrq.advancedlight.R;

public class TitleBar extends RelativeLayout {

    private int mColor;
    private int mTextColor;
    private RelativeLayout mRelativeLayout;
    private TextView mTextView;
    private ImageView mIvBack;
    private ImageView mIvMore;

    public TitleBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mTextColor = typedArray.getColor(R.styleable.TitleBar_titleColor, Color.BLACK);
        mColor = typedArray.getColor(R.styleable.TitleBar_backgroundColor, Color.YELLOW);
        typedArray.recycle();
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_title_bar, this, true);
        mRelativeLayout = findViewById(R.id.custom_bar);
        mTextView = findViewById(R.id.custom_title);
        mIvBack = findViewById(R.id.custom_back);
        mIvMore = findViewById(R.id.custom_more);

        mRelativeLayout.setBackgroundColor(mColor);
        mTextView.setTextColor(mTextColor);
    }

    public void setTitle(String title) {
        mTextView.setText(title);
    }

    public void setLeftListener(OnClickListener onClickListener) {
        mIvBack.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener) {
        mIvMore.setOnClickListener(onClickListener);
    }
}
