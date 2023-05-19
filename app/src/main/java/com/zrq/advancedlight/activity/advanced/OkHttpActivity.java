package com.zrq.advancedlight.activity.advanced;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.zrq.advancedlight.R;
import com.zrq.advancedlight.entity.CommentItem;
import com.zrq.advancedlight.util.Constants;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamClass;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    private static final String TAG = "OkHttpActivity";
    private Context mContext;
    private Button mBtnOkHttpGet;
    private Button mBtnOkHttpPost;
    private Button mBtnOkHttpUp;
    private Button mBtnOkHttpUpMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mBtnOkHttpGet = findViewById(R.id.btn_ok_http_get);
        mBtnOkHttpPost = findViewById(R.id.btn_ok_http_post);
        mBtnOkHttpUp = findViewById(R.id.btn_ok_http_up);
        mBtnOkHttpUpMore = findViewById(R.id.btn_ok_http_up_more);
    }

    private void initData() {
        mBtnOkHttpGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWithOkHttp(Constants.BASE_URL + "/get/text");
            }
        });
        mBtnOkHttpPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postWithOkHttp(Constants.BASE_URL + "/post/comment");
            }
        });
        mBtnOkHttpUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadWithOkHttp(Constants.BASE_URL + "/file/upload");
            }
        });
        mBtnOkHttpUpMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMoreWithOkHttp(Constants.BASE_URL + "/files/upload");
            }
        });
    }

    private void getWithOkHttp(String basePath) {
        //1.创建一个OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();
        //2.创建一个Request对象
        Request request = new Request.Builder()
                .get()
                .url(basePath)
                .build();
        //3.通过client创建请求任务
        Call call = client.newCall(request);
        //4.异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "code ===> " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "response.body().string() ===> " + response.body().string());
                }
            }
        });
    }

    private void postWithOkHttp(String s) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        //设置提交的内容
        CommentItem commentItem = new CommentItem("123456789", "哈哈哈哈");
        Gson gson = new Gson();
        String json = gson.toJson(commentItem);
        Log.d(TAG, "json ===> " + json);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .post(body)
                .url(s)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "code ===> " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "response.body().string() ===> " + response.body().string());
                }
            }
        });

    }

    private void uploadWithOkHttp(String s) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();
        File file = new File("/storage/self/primary/新文件夹/微信图片_202201221612095.jpg");
        MediaType fileType = MediaType.parse("image/png");
        RequestBody fileBody = RequestBody.create(file, fileType);
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("file", file.getName(), fileBody)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(s)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "response.body().string() ===>" + response.body().string());
                }
            }
        });
    }

    private void uploadMoreWithOkHttp(String s) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();
        File file1 = new File("/storage/self/primary/新文件夹/微信图片_202201221612095.jpg");
        File file2 = new File("/storage/self/primary/新文件夹/6105908c119b4aa694c3c6d0b9110139.jpg");
        File file3 = new File("/storage/self/primary/新文件夹/1651906855964.png");
        File file4 = new File("/storage/self/primary/新文件夹/pic_8.png");
        MediaType fileType = MediaType.parse("image/png");
        RequestBody fileBody1 = RequestBody.create(file1, fileType);
        RequestBody fileBody2 = RequestBody.create(file2, fileType);
        RequestBody fileBody3 = RequestBody.create(file3, fileType);
        RequestBody fileBody4 = RequestBody.create(file4, fileType);
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("files", file1.getName(), fileBody1)
                .addFormDataPart("files", file2.getName(), fileBody2)
                .addFormDataPart("files", file3.getName(), fileBody3)
                .addFormDataPart("files", file4.getName(), fileBody4)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(s)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "response.body().string() ===>" + response.body().string());
                }
            }
        });

    }
}

