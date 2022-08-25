package com.zrq.advancedlight.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zrq.advancedlight.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PicLoadActivity extends AppCompatActivity {

    private Context mContext;
    private Button mBtnLoad;
    private ImageView mIvPic1;
    private ImageView mIvPic2;
    private ImageView mIvPic3;
    private EditText mEtScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_load);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mBtnLoad = findViewById(R.id.btn_load);
        mIvPic1 = findViewById(R.id.iv_pic);
        mEtScale = findViewById(R.id.et_scale);
    }

    private void initData() {
        mBtnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPic("https://i0.hdslb.com/bfs/album/0f8d4070b45b51a12289872530cbff7148684ed7.jpg@1295w.webp");
            }
        });
    }

    private void loadPic(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL mUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    connection.setRequestProperty("Accept", "*/*");
                    connection.connect();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        //转成Bitmap
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        try {
                            options.inSampleSize = "".equals(mEtScale.getText().toString()) ? 1 : Integer.valueOf(mEtScale.getText().toString());
                        }catch (Exception e){
                            e.printStackTrace();
                            options.inSampleSize = 1;
                            mEtScale.setText("");
                        }

                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);

                        //拿到图片大小
//                        BitmapFactory.Options options2 = new BitmapFactory.Options();
//                        options1.inSampleSize = 1;
//                        Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream, null, options1);
//                        options2.inJustDecodeBounds = true;
//                        int outHeight = options2.outHeight;
//                        int outWidth = options2.outWidth;
//                        //拿到控件的尺寸
//                        int measuredWidth = mIvPic2.getMeasuredWidth();
//                        int measuredHeight = mIvPic2.getMeasuredHeight();
//                        //图片的宽和高分别除以控件的宽和高，取最大值
//                        if (outHeight > measuredHeight || outWidth > measuredWidth) {
//                            int subWidth = outWidth / measuredWidth;
//                            int subHeight = outHeight / measuredHeight;
//                            options2.inSampleSize = subWidth > subHeight ? subWidth : subHeight;
//                        }
//                        options2.inJustDecodeBounds = false;
//                        Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream, null, options2);
//                        Bitmap bitmap3 = BitmapFactory.decodeStream(inputStream, null, options3);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mIvPic1.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}