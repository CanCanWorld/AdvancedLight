package com.zrq.advancedlight.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.util.Calculate;

public class ImageActivity extends AppCompatActivity {

    private static final String TAG = "ImageActivity";
    private Context mContext;
    private ImageView mIvImg;
    private Button mBtnImg;
    private SeekBar mSbChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();
        initData();
    }

    private void initView() {
        mIvImg = findViewById(R.id.iv_img);
        mBtnImg = findViewById(R.id.btn_img);
        mSbChange = findViewById(R.id.seek_change);
    }

    private void initData() {
        ViewGroup.LayoutParams layoutParams = mIvImg.getLayoutParams();
        int width = layoutParams.width;
        int height = layoutParams.height;

        mBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.mipmap.bg5, options);
                options.inSampleSize = Calculate.calculateInSampleSize(options, mIvImg);
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg5, options);
                mIvImg.setImageBitmap(bitmap);
            }
        });

        mSbChange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = 1 - progress / 100f;
                layoutParams.width = (int) (width * scale);
                layoutParams.height = (int) (height * scale);
                mIvImg.setLayoutParams(layoutParams);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}