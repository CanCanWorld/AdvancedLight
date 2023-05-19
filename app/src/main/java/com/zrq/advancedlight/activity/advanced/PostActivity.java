package com.zrq.advancedlight.activity.advanced;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.zrq.advancedlight.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostActivity extends AppCompatActivity {

    private Button mBtnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initView();
        initEvent();
    }

    private void initView() {
        mBtnPost = findViewById(R.id.btn_post);
    }

    private void initEvent() {
        new Thread(() -> {
            try {
                URL url = new URL("http://192.168.1.47:9102/post/comment");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}