package com.zrq.advancedlight.view;

import android.view.View;

public class MyBtnView {
    private View mTarget;

    public MyBtnView(View mTarget) {
        this.mTarget = mTarget;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}
