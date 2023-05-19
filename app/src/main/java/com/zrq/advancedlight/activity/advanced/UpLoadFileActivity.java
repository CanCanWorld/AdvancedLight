package com.zrq.advancedlight.activity.advanced;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zrq.advancedlight.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpLoadFileActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://10.8.29.31:9102";
    private static final String TAG = "UpLoadPicActivity";
    private Context mContext;
    private Button mBtnUpload;
    private Button mBtnDownload;
    private Button mBtnOkGet;
    private static final int PERMISSION_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_file);
        mContext = this;
        checkPermission();
        initView();
        initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        int result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    /**
     * 处理请求结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initView() {
        mBtnUpload = findViewById(R.id.btn_upload);
        mBtnDownload = findViewById(R.id.btn_download);
        mBtnOkGet = findViewById(R.id.btn_okhttp_get);
    }

    private void initData() {
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(BASE_URL + "/file/upload");
            }
        });
        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down(BASE_URL + "/download/10");
            }
        });
        mBtnOkGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGetRequestWithOkHttp("https://www.baidu.com");
            }
        });
    }

    private void sendGetRequestWithOkHttp(String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(s)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d(TAG, "response.body().toString() ===> " + response.body().string());
                    }
                });

            }
        }).start();
    }

    private void down(String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    connection.setRequestProperty("Accept", "*/*");
                    connection.connect();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        Map<String, List<String>> headerFields = connection.getHeaderFields();
                        Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
                        for (Map.Entry<String, List<String>> entry : entries) {
                            Log.d(TAG, entry.getKey() + " ==> " + entry.getValue());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void uploadFile(String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream outputStream = null;
                FileInputStream is = null;
                BufferedReader reader = null;
                try {
                    File file = new File("/storage/self/primary/新文件夹/微信图片_202201221612095.jpg");
                    String fileKey = "file";
                    String fileName = file.getName();
                    String fileType = "image/jpeg";
                    String boundary = "--------------------------880836586269539220328137";
                    URL url = new URL(s);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(10000);
                    connection.setRequestProperty("User-Agent", "Android/" + Build.VERSION.SDK_INT);
                    connection.setRequestProperty("Accept", "*/*");
                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                    connection.setRequestProperty("Connection", "keep-alive");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.connect();

                    //发送头部信息
                    StringBuilder headerSbInfo = new StringBuilder();
                    headerSbInfo.append("--");
                    headerSbInfo.append(boundary);
                    headerSbInfo.append("\r\n");
                    headerSbInfo.append("Content-Disposition: form-data; name=\"" + fileKey + "\"; filename=\"" + fileName + ".png\"\n");
                    headerSbInfo.append("\r\n");
                    headerSbInfo.append("Content-Type: " + fileType);
                    headerSbInfo.append("\r\n");
                    headerSbInfo.append("\r\n");
                    byte[] bytes = headerSbInfo.toString().getBytes(StandardCharsets.UTF_8);
                    outputStream = connection.getOutputStream();
                    outputStream.write(bytes);
                    //发送文件内容
                    is = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                        Log.d(TAG, "len: " + len);
                        outputStream.write(buffer, 0, len);
                    }
                    //发送尾部信息
                    StringBuilder footerSbInfo = new StringBuilder();
                    footerSbInfo.append("\r\n");
                    footerSbInfo.append("--");
                    footerSbInfo.append(boundary);
                    footerSbInfo.append("--");
                    footerSbInfo.append("\r\n");
                    footerSbInfo.append("\r\n");
                    outputStream.write(footerSbInfo.toString().getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                    //获取返回的结果
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        String readLine = reader.readLine();
                        Log.d(TAG, "readLine: " + readLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //关闭流对象
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}