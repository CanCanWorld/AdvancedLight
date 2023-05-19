package com.zrq.advancedlight.activity.advanced;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class HorizontalViewActivity extends AppCompatActivity {

    private Context mContext;
    private List<String> mList1;
    private List<String> mList2;
    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_view);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView1 = findViewById(R.id.recycler_view1);
        mRecyclerView2 = findViewById(R.id.recycler_view2);
    }

    private void initData() {
        mList1 = new ArrayList<>();
        mList2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList1.add(i, String.valueOf(i));
            mList2.add(i, String.valueOf((char) (i + 64)));

        }
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
        MyAdapter myAdapter1 = new MyAdapter(mContext, mList1);
        MyAdapter myAdapter2 = new MyAdapter(mContext, mList2);
        mRecyclerView1.setAdapter(myAdapter1);
        mRecyclerView2.setAdapter(myAdapter2);
    }
}