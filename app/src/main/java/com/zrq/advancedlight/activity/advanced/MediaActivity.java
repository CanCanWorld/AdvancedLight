package com.zrq.advancedlight.activity.advanced;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.entity.Picture;
import com.zrq.advancedlight.util.PictureConfig;

import java.util.List;

public class MediaActivity extends AppCompatActivity {

    private static final String TAG = "MediaActivity";
    private Button mBtnMediaPic;
    private EditText mEtNum;
    private Context mContext;
    private PictureConfig picConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        mContext = this;
        initView();
        initData();
        initPicConfig();
    }

    private void initPicConfig() {
        picConfig = PictureConfig.getInstance();
        picConfig.setOnSelectedPicFinish(new PictureConfig.OnSelectedPicFinish() {
            @Override
            public void onSelectedPic(List<Picture> mSelectList) {
                for (Picture picture : mSelectList) {
                    Log.d(TAG, "onSelectedPic: " + picture.toString());
                }
            }
        });
    }

    private void initView() {
        mBtnMediaPic = findViewById(R.id.btn_media_pic);
        mEtNum = findViewById(R.id.et_selected_num);
    }

    private void initData() {
        mBtnMediaPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEtNum.getText())) {
                    picConfig.setMaxPicSelect(Integer.parseInt(mEtNum.getText().toString()));
                } else {
                    picConfig.setMaxPicSelect(9);
                }
                Intent intent = new Intent(mContext, PictureActivity.class);
                startActivity(intent);
            }
        });
    }

}