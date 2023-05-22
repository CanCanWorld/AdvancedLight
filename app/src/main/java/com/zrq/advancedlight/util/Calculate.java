package com.zrq.advancedlight.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.zrq.advancedlight.R;

import java.io.FileOutputStream;
import java.util.ArrayList;

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

    public static void writeImage(String path, ArrayList<String> data) {
        try {
            int height = data.size() * 20;     //图片高
            Bitmap bitmap = Bitmap.createBitmap(270, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);   //背景颜色

            Paint p = new Paint();
            p.setColor(Color.BLACK);   //画笔颜色
            p.setTextSize(15);         //画笔粗细
            for (int i = 0; i < data.size(); i++) {
                canvas.drawText(data.get(i), 20, (i + 1) * 20, p);
            }

            Log.e("path", path);
//将Bitmap保存为png图片
            FileOutputStream out = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            Log.e("done", "done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
