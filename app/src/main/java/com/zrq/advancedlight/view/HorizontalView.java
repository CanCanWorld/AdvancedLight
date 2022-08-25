package com.zrq.advancedlight.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalView extends ViewGroup {

    private static final String TAG = "HorizontalView";
    private int lastX;
    private int lastY;
    private int lastInterceptX;
    private int lastInterceptY;
    private Scroller mScroller;
    private int currentIndex = 0;
    private int childWidth = 0;
    private VelocityTracker tracker;


    public HorizontalView(Context context) {
        super(context);
        init();
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        tracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            int paddingLeft = getPaddingLeft();
            int childWidth = getChildAt(0).getMeasuredWidth();
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(childWidth * getChildCount(), childHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            int childWidth = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(childWidth * getChildCount(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSize, childHeight * getChildCount());

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                childWidth = width;
                child.layout(left, 0, left + width, height);
                left += width;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = lastInterceptX - x;
                int deltaY = lastInterceptY - y;
                if (Math.abs(deltaX) - Math.abs(deltaY) > 0) {
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        lastX = x;
        lastY = y;
        lastInterceptX = x;
        lastInterceptY = y;
        Log.e(TAG, "onInterceptTouchEvent: " + x);
        Log.e(TAG, "onInterceptTouchEvent: " + y);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        int deltaX = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = lastX - x;
                scrollBy(deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int distance = getScrollX() - currentIndex * childWidth;
                if (Math.abs(distance) > childWidth / 2) {
                    if (distance > 0) {
                        currentIndex++;
                    } else {
                        currentIndex--;
                    }
                } else {
                    tracker.computeCurrentVelocity(1000);
                    float xV = tracker.getXVelocity();
                    Log.e(TAG, "onTouchEvent: xV =="+xV );
                    if (Math.abs(xV) > 50) {
                        if (xV > 0) {
                            Log.e(TAG, "left" );
                            currentIndex--;
                        } else {
                            Log.e(TAG, "right" );
                            currentIndex++;
                        }
                    }
                }
                if (currentIndex <= 0) {
                    currentIndex = 0;
                } else if (currentIndex >= getChildCount() - 1) {
                    currentIndex = getChildCount() - 1;
                }
                smoothScrollTo(currentIndex * childWidth, 0);
                tracker.clear();
                break;
        }
        lastX = x;
        lastY = y;
        return super.onTouchEvent(event);
    }

    private void smoothScrollTo(int destX, int destY) {
        Log.e(TAG, "smoothScrollTo: " + getScrollX());
        Log.e(TAG, "smoothScrollTo: " + getScrollY());
        mScroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
