package com.zrq.advancedlight.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.view.TitleBar;

public class CustomOtherViewActivity extends AppCompatActivity {

    private Context mContext;
    private TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_other_view);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setTitle("自定义标题栏");
    }

    private void initData() {
        mTitleBar.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTitleBar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了右边的按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }
}