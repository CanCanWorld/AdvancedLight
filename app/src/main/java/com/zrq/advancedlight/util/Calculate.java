package com.zrq.advancedlight.util;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import com.zrq.advancedlight.R;

public class Calculate {

    private static String TAG = "Calculate";

    public static int calculateInSampleSize(BitmapFactory.Options options, View view) {
        int size = 1;
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        if (outWidth > measuredWidth || outHeight > measuredHeight) {
            int subWidth = outWidth / measuredWidth;
            int subHeight = outHeight / measuredHeight;
            size = Math.max(subWidth, subHeight);
        }
        Log.d(TAG, "size: " + size);
        return size;
    }
}
