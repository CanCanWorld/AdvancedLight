package com.zrq.advancedlight.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.activity.AnimatorActivity;
import com.zrq.advancedlight.activity.CalendarActivity;
import com.zrq.advancedlight.activity.CardViewActivity;
import com.zrq.advancedlight.activity.ContactActivity;
import com.zrq.advancedlight.activity.CustomOtherViewActivity;
import com.zrq.advancedlight.activity.CustomViewActivity;
import com.zrq.advancedlight.activity.HorizontalViewActivity;
import com.zrq.advancedlight.activity.HttpActivity;
import com.zrq.advancedlight.activity.ImageActivity;
import com.zrq.advancedlight.activity.LoginActivity;
import com.zrq.advancedlight.activity.MediaActivity;
import com.zrq.advancedlight.activity.MyRecyclerViewActivity;
import com.zrq.advancedlight.activity.NoteContentActivity;
import com.zrq.advancedlight.activity.NotificationActivity;
import com.zrq.advancedlight.activity.OkHttpActivity;
import com.zrq.advancedlight.activity.PicLoadActivity;
import com.zrq.advancedlight.activity.PostActivity;
import com.zrq.advancedlight.activity.RequestTestActivity;
import com.zrq.advancedlight.activity.SMSActivity;
import com.zrq.advancedlight.activity.UpLoadFileActivity;
import com.zrq.advancedlight.activity.WebActivity;
import com.zrq.advancedlight.adapter.ItemAdapter;
import com.zrq.advancedlight.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private String title;
    private View rootView;
    private RecyclerView mRecyclerView;
    private List<String> mList;

    public ListFragment() {
    }

    public ListFragment(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_list, container, false);
        }
        initView();
        initData();
        return rootView;
    }

    private void initView() {
        mRecyclerView = rootView.findViewById(R.id.fragment_recycler_view);
    }

    private void initData() {
        mList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if ("安卓进阶之光".equals(title)) {
            mList.add("RecyclerView-GridView");
            mList.add("CardView");
            mList.add("Notification");
            mList.add("TextInputLayout");
            mList.add("CustomView");
            mList.add("Animator");
            mList.add("CustomOtherView");
            mList.add("HorizontalView");
            mList.add("Http");
            mList.add("LoadPicture");
            mList.add("BigImage");
            mList.add("PostText");
            mList.add("UploadFile");
            mList.add("GetNoteContent");
            mList.add("GetCalendar");
            mList.add("GetContact");
            mList.add("GetVerify");
            mList.add("MediaPicture");
            mList.add("WebView");
            mList.add("OkHttp");
            mList.add("Post");
        } else if ("暂无".equals(title)) {
            mList.add("暂无");
            mList.add("暂无");
            mList.add("暂无");
            mList.add("暂无");
            mList.add("暂无");
            mList.add("暂无");
            mList.add("暂无");
            mList.add("暂无");
            mList.add("暂无");
            mList.add("暂无");
            mList.add("暂无");
        }

        ItemAdapter itemAdapter = new ItemAdapter(getContext(), mList);
        mRecyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if ("安卓进阶之光".equals(title)) {
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(getContext(), MyRecyclerViewActivity.class);
                            break;
                        case 1:
                            intent = new Intent(getContext(), CardViewActivity.class);
                            break;
                        case 2:
                            intent = new Intent(getContext(), NotificationActivity.class);
                            break;
                        case 3:
                            intent = new Intent(getContext(), LoginActivity.class);
                            break;
                        case 4:
                            intent = new Intent(getContext(), CustomViewActivity.class);
                            break;
                        case 5:
                            intent = new Intent(getContext(), AnimatorActivity.class);
                            break;
                        case 6:
                            intent = new Intent(getContext(), CustomOtherViewActivity.class);
                            break;
                        case 7:
                            intent = new Intent(getContext(), HorizontalViewActivity.class);
                            break;
                        case 8:
                            intent = new Intent(getContext(), HttpActivity.class);
                            break;
                        case 9:
                            intent = new Intent(getContext(), PicLoadActivity.class);
                            break;
                        case 10:
                            intent = new Intent(getContext(), ImageActivity.class);
                            break;
                        case 11:
                            intent = new Intent(getContext(), RequestTestActivity.class);
                            break;
                        case 12:
                            intent = new Intent(getContext(), UpLoadFileActivity.class);
                            break;
                        case 13:
                            intent = new Intent(getContext(), NoteContentActivity.class);
                            break;
                        case 14:
                            intent = new Intent(getContext(), CalendarActivity.class);
                            break;
                        case 15:
                            intent = new Intent(getContext(), ContactActivity.class);
                            break;
                        case 16:
                            intent = new Intent(getContext(), SMSActivity.class);
                        case 17:
                            intent = new Intent(getContext(), MediaActivity.class);
                            break;
                        case 18:
                            intent = new Intent(getContext(), WebActivity.class);
                            break;
                        case 19:
                            intent = new Intent(getContext(), OkHttpActivity.class);
                            break;
                        case 20:
                            intent = new Intent(getContext(), PostActivity.class);
                            break;
                        default:
                            break;
                    }
                    startActivity(intent);
                } else if ("暂无".equals(title)) {
                    Toast.makeText(getContext(), "暂无", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
    }

}