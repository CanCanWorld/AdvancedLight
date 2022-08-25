package com.zrq.advancedlight.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.zrq.advancedlight.R;
import com.zrq.advancedlight.adapter.GetResultAdapter;
import com.zrq.advancedlight.entity.GetTextItem;
import com.zrq.advancedlight.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpActivity extends AppCompatActivity {

    private static final String TAG = "HttpActivity";
    private Context mContext;
    private Button mBtnGet;
    private RecyclerView mRvResult;
    private List<GetTextItem.DataBean> mList;
    private GetResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mBtnGet = findViewById(R.id.btn_get);
        mRvResult = findViewById(R.id.rv_result);
    }

    private void initData() {
        mList = new ArrayList<>();
        mRvResult.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new GetResultAdapter(mContext,mList);
        mRvResult.setAdapter(adapter);

        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localJson();
            }
        });
    }

    public void localJson() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                InputStream inputStream = null;
                try {
                    //1.创建url
                    URL url = new URL(Constants.BASE_URL);
                    //2.根据url打开HttpURLConnection连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //3.设置连接的属性和请求方式
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    connection.setRequestProperty("Accept", "*/*");
                    //4.打开连接
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    //5.判断是否连接成功
                    if (responseCode == 200) {
                        Map<String, List<String>> headerFields = connection.getHeaderFields();
                        Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
                        for (Map.Entry<String, List<String>> entry : entries) {
                            Log.d(TAG, "localJson: key==" + entry.getKey() + "value==" + entry.getValue());
                        }
                        //6.根据连接获取输入流
                        inputStream = connection.getInputStream();
                        //7.通过BufferReader使用输入流
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        //8.获取读写流读取到的数据
                        String json = reader.readLine();
                        Log.d(TAG, "run: " + json);
                        //9.使用gson解析获取到的数据，将其转化为自定义实体类对象
                        Gson gson = new Gson();
                        GetTextItem getTextItem = gson.fromJson(json, GetTextItem.class);
                        updateUI(getTextItem);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //关闭流对象
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

    private void updateUI(GetTextItem getTextItem) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mList.addAll(getTextItem.getData());
                for (GetTextItem.DataBean dataBean : mList) {
                    Log.d(TAG, "run: "+dataBean.getTitle());

                }
                adapter.notifyDataSetChanged();
            }
        });
    }

}