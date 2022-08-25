package com.zrq.advancedlight.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private final int mHorizontalSpacing = 20;
    private final int mVerticalSpacing = 20;
    private List<List<View>> allLines = new ArrayList<>();
    private List<Integer> lineHeights = new ArrayList<>();

    //代码里面初始化new的时候会调用
    public FlowLayout(Context context) {
        super(context);
    }

    //xml文件通过反射调用
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //有不同的主题的时候调用
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //度量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int childCount = getChildCount();

        List<View> lineView = new ArrayList<>();    //保存一行中所有的View
        int lineWidthUsed = 0;  //记录这行已经使用了多宽的size
        int lineHeight = 0; //一行的行高

        int parentNeededWidth = 0;  //  measure过程中，子View要求父ViewGroup的宽
        int parentNeededHeight = 0; //  measure过程中，子View要求父ViewGroup的高

        //度量孩子
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams childLP = childView.getLayoutParams();

            int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLP.width);
            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childLP.height);
            childView.measure(childWidthSpec, childHeightSpec);

            //获取子View的宽高
            int childMeasuredWidth = childView.getMeasuredWidth();
            int childMeasuredHeight = childView.getMeasuredHeight();

            //换行
            if (lineWidthUsed + childMeasuredWidth + mHorizontalSpacing > selfWidth) {
                allLines.add(lineView);
                lineHeights.add(lineHeight);

                parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
                parentNeededHeight += lineHeight + mVerticalSpacing;

                lineView = new ArrayList<>();
                lineWidthUsed = 0;
                lineHeight = 0;
            }

            //记录每一行有哪些view
            lineView.add(childView);
            //每行的宽和高
            lineWidthUsed += childMeasuredWidth + mHorizontalSpacing;
            lineHeight = Math.max(lineHeight, childMeasuredHeight);
        }
        //度量自己
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int realWidth = widthMode == MeasureSpec.AT_MOST ? parentNeededWidth : selfWidth;
        int realHeight = heightMode == MeasureSpec.AT_MOST ? parentNeededHeight : selfHeight;
        setMeasuredDimension(realWidth, realHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allLines.size();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = allLines.get(i);
            int lineHeight = lineHeights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View view = lineViews.get(j);
                int left = paddingLeft;
                int right = paddingTop;
            }
        }
    }
}
