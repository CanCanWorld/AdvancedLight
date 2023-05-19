package com.zrq.advancedlight.activity.advanced;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.view.CustomView;
import com.zrq.advancedlight.view.MyBtnView;

public class AnimatorActivity extends AppCompatActivity {

    private Context mContext;
    private Button mBtnAnima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mBtnAnima = findViewById(R.id.btn_animator);
    }

    private void initData() {
        MyBtnView myBtnView = new MyBtnView(mBtnAnima);
        ObjectAnimator animator = ObjectAnimator.ofInt(myBtnView, "width", 500).setDuration(500);
        mBtnAnima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click is test", Toast.LENGTH_SHORT).show();
                animator.start();
            }
        });
    }
}