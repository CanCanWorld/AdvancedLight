package com.zrq.advancedlight.activity.advanced;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zrq.advancedlight.R;
import com.zrq.advancedlight.entity.CommentItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RequestTestActivity extends AppCompatActivity {

    private static final String TAG = "PostTextActivity";
    //    private static final String BASE_URL = "http://192.168.1.3:9102";
    private static final String BASE_URL = "http://api.tianapi.com/dongman/index";

    private Context mContext;
    private Button mBtnPost;
    private Button mBtnGetParams;
    private Button mBtnPostParams;
    private TextView mTvPost;
    private TextView mTvGetParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_test);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mBtnPost = findViewById(R.id.btn_post_text);
        mBtnGetParams = findViewById(R.id.btn_get_with_params);
        mBtnPostParams = findViewById(R.id.btn_post_with_params);
        mTvPost = findViewById(R.id.tv_post);
        mTvGetParams = findViewById(R.id.tv_get_with_params);
    }

    private void initData() {
        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postRequest(BASE_URL + "/post/comment");
                test();
            }
        });
        mBtnGetParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("keyword", "我是关键字");
                params.put("page", "12");
                params.put("order", "0");
                startRequest(params, "GET", "/get/param");
            }
        });
        mBtnPostParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("string", "我是String");
                startRequest(params, "POST", "/post/string");
            }
        });
    }

    private void startRequest(Map<String, String> params, String method, String api) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                InputStream inputStream = null;
                try {
                    if (params != null && params.size() > 0) {
                        sb.append("?");

                        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> next = iterator.next();
                            sb.append(next.getKey());
                            sb.append("=");
                            sb.append(next.getValue());
                            if (iterator.hasNext()) {
                                sb.append("&");
                            }
                        }
                    }
                    String param = sb.toString();
                    Log.d(TAG, "param: " + param);
                    URL url;
                    if (param != null && param.length() > 0) {
                        url = new URL(BASE_URL + api + param);
                    } else {
                        url = new URL(BASE_URL + api);
                    }
                    Log.d(TAG, "url: " + url.toString());

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(method);
                    connection.setConnectTimeout(10000);
                    connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    connection.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9");
                    connection.connect();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String readLine = reader.readLine();
                        Log.d(TAG, "readLine: " + readLine);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvGetParams.setText(readLine);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }


    private void test() {
        new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            InputStream inputStream = null;
            Map<String, String> params = new HashMap<>();
            params.put("key", "ff239574c8553cb91b015bad49c2a115");
            params.put("num", "10");
            try {
                if (params != null && params.size() > 0) {
                    sb.append("?");

                    Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, String> next = iterator.next();
                        sb.append(next.getKey());
                        sb.append("=");
                        sb.append(next.getValue());
                        if (iterator.hasNext()) {
                            sb.append("&");
                        }
                    }
                }
                String param = sb.toString();
                Log.d(TAG, "param: " + param);
                URL url;
                url = new URL(BASE_URL + param);
                Log.d(TAG, "url: " + url);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(10000);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9");
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String readLine = reader.readLine();
                    Log.d(TAG, "readLine: " + readLine);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvGetParams.setText(readLine);
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }

    private void postRequest(String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream outputStream = null;
                InputStream inputStream = null;

                Map<String, String> params = new HashMap<>();
                params.put("key", "ff239574c8553cb91b015bad49c2a115");
                params.put("num", "10");

                StringBuilder sb = new StringBuilder();
                if (params != null && params.size() > 0) {
                    sb.append("?");

                    Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, String> next = iterator.next();
                        sb.append(next.getKey());
                        sb.append("=");
                        sb.append(next.getValue());
                        if (iterator.hasNext()) {
                            sb.append("&");
                        }
                    }
                }
                String param = sb.toString();
                try {
                    URL url = new URL(s + param);
                    //创建连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //connection参数的配置
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(10000);
                    connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    connection.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9");
                    CommentItem commentItem = new CommentItem("123132231", "我是评论内容");
                    //创建gson将实体对象转成json数据
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(commentItem);

                    Log.d(TAG, "param: " + param);
                    Log.d(TAG, "jsonStr: " + jsonStr);
                    //json转成byte[]字节数组
                    byte[] bytes = jsonStr.getBytes(StandardCharsets.UTF_8);
                    //设置连接的内容长度
                    connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
                    connection.connect();
                    //把数据给到服务
                    //通过连接获取输出流
                    outputStream = connection.getOutputStream();
                    //将数据（字节数组）写出
                    outputStream.write(bytes);
                    outputStream.flush();
                    //请求成功时
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        //通过连接获取输入流
                        inputStream = connection.getInputStream();
                        //创建读入流
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String readLine = reader.readLine();
                        Log.d(TAG, "readLine: " + readLine);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvPost.setText(readLine);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //关闭流
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        ).

                start();
    }
}