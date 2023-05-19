package com.zrq.advancedlight.activity.advanced;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.adapter.SmsAdapter;
import com.zrq.advancedlight.entity.Sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSActivity extends AppCompatActivity {

    private static final String TAG = "SMSActivity";
    private static final int SMS_REQUEST_CODE = 1;
    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private Context mContext;
    private EditText mEtGetVerify;
    private Button mBtnGetVerify;
    private RecyclerView mRvGetSms;

    private static final int MATCH_CODE = 1;

    private List<Sms> mList;

    static {
        sUriMatcher.addURI("sms", "#", MATCH_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        mContext = this;
        checkSmsPermission();
        initView();
        initData();
        getVerify();
    }

    private void checkSmsPermission() {
        int smsPermissionResult = checkSelfPermission(Manifest.permission.READ_SMS);
        if (smsPermissionResult == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, SMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "request sms_read success");
            } else {
                Log.d(TAG, "request sms_read failure !");
            }
        }
    }

    private CountDownTimer timer = new CountDownTimer(6 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mBtnGetVerify.setEnabled(false);
            mBtnGetVerify.setText("重新发送(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            mBtnGetVerify.setEnabled(true);
            mBtnGetVerify.setText("获取验证码");
        }
    };

    private void initView() {
        mEtGetVerify = findViewById(R.id.et_get_verify);
        mBtnGetVerify = findViewById(R.id.btn_get_verify);
        mRvGetSms = findViewById(R.id.rv_get_sms);
    }

    private void initData() {
        getSms();
        mRvGetSms.setLayoutManager(new LinearLayoutManager(mContext));
        SmsAdapter adapter = new SmsAdapter(mContext, mList);
        mRvGetSms.setAdapter(adapter);

        mBtnGetVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
            }
        });
    }

    @SuppressLint("Range")
    private void getSms() {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = resolver.query(uri, new String[]{"date", "address", "body"}, null, null, null);
        mList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Sms sms = new Sms();
            long time = cursor.getLong(cursor.getColumnIndex("date"));
            Date date = new Date();
            date.setTime(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            sms.setDate(sdf.format(date));
            sms.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            sms.setBody(cursor.getString(cursor.getColumnIndex("body")));
            mList.add(sms);
        }
        cursor.close();
    }

    @SuppressLint("Range")
    private void getVerify() {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://sms/");
        resolver.registerContentObserver(uri, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri) {
                if (sUriMatcher.match(uri) == MATCH_CODE) {
                    Cursor cursor = resolver.query(uri, new String[]{"body"}, null, null, null);
                    while (cursor.moveToNext()) {
                        String body = cursor.getString(cursor.getColumnIndex("body"));
                        mEtGetVerify.setText(handlerMsg(body));
                    }
                    cursor.close();
                }
            }
        });
    }

    private String handlerMsg(String body) {
        if (body != null && body.startsWith("【zrq】")) {
            //符合条件
            //截取数字
            Pattern p = Pattern.compile("(?<![0-9])([0-9]{4})(?![0-9])");
            Matcher matcher = p.matcher(body);
            boolean contain = matcher.find();
            if (contain) {
                return matcher.group();
            }
        }
        return "";
    }
}