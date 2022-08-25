package com.zrq.advancedlight.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.zrq.advancedlight.R;
import com.zrq.advancedlight.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Context mContext;
    private List<String> mList;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        rootView = getWindow().getDecorView();
        mList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.my_recycler_view);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mList.add(String.valueOf(i));
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        MyAdapter myAdapter = new MyAdapter(mContext, mList);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mContext, "click is " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position) {
                new AlertDialog.Builder(mContext)
                        .setTitle("确定要删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String element = myAdapter.removeData(position);
                                Snackbar.make(rootView, "删除成功", Snackbar.LENGTH_LONG)
                                        .setAction("撤回", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                myAdapter.addData(position, element);
                                            }
                                        })
                                        .show();
                            }
                        })
                        .show();
            }
        });
    }
}