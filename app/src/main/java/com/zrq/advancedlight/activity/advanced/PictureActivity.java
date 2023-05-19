package com.zrq.advancedlight.activity.advanced;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zrq.advancedlight.R;
import com.zrq.advancedlight.adapter.PictureAdapter;
import com.zrq.advancedlight.entity.Picture;
import com.zrq.advancedlight.util.PictureConfig;

import java.util.ArrayList;
import java.util.List;

public class PictureActivity extends AppCompatActivity {

    private static final String TAG = "PictureActivity";
    private Context mContext;
    private ImageView mIvBack;
    private RecyclerView mRvPic;
    private static final int LOADER_ID = 1;
    private List<Picture> pictures;
    private PictureAdapter adapter;
    private TextView mTvNum;

    private View view;
    private ImageView mIvBigPic;
    private AlertDialog.Builder builder;
    private PictureConfig picConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        mContext = this;
        initView();
        initData();
        initPicConfig();
    }

    private void initPicConfig() {
        picConfig = PictureConfig.getInstance();
        adapter.setMaxPicSelect(picConfig.getMaxPicSelect());
    }

    private void initView() {
        mIvBack = findViewById(R.id.iv_back);
        mRvPic = findViewById(R.id.rv_pic);
        mTvNum = findViewById(R.id.tv_select_num);
    }

    private void initData() {
        pictures = new ArrayList<>();
        getMediaPic();
        mRvPic.setLayoutManager(new GridLayoutManager(mContext, 3));
        adapter = new PictureAdapter(mContext, pictures);
        mRvPic.setAdapter(adapter);
        adapter.setOnClickListener(new PictureAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                view = LayoutInflater.from(mContext).inflate(R.layout.show_picture, null);
                mIvBigPic = view.findViewById(R.id.iv_big_picture);
                builder = new AlertDialog.Builder(mContext);
                Glide.with(mContext).load(pictures.get(position).getPath()).into(mIvBigPic);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        adapter.setOnSelectItemChangeListener(new PictureAdapter.OnSelectItemChangeListener() {
            @Override
            public void onSelectItemChange(List<Picture> mSelectList) {
                mTvNum.setText("已选择（" + mSelectList.size() + "/9）");
            }
        });

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureConfig.OnSelectedPicFinish onSelectedPicFinish = picConfig.getOnSelectedPicFinish();
                onSelectedPicFinish.onSelectedPic(adapter.getmSelectList());
            }
        });
    }

    @SuppressLint("Range")
    private void getMediaPic() {
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                if (id == LOADER_ID) {
                    return new CursorLoader(mContext, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new String[]{"_data", "_display_name", "date_added"},
                            null, null, "date_added desc");
                }
                return null;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Picture picture = new Picture();
                        picture.setPath(cursor.getString(cursor.getColumnIndex("_data")));
                        picture.setTitle(cursor.getString(cursor.getColumnIndex("_display_name")));
                        picture.setDate(cursor.getLong(cursor.getColumnIndex("date_added")));
                        pictures.add(picture);
                        Log.d(TAG, "onLoadFinished: " + picture.toString());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {

            }
        });
    }
}