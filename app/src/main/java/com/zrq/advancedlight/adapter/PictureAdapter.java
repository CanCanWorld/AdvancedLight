package com.zrq.advancedlight.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.zrq.advancedlight.R;
import com.zrq.advancedlight.entity.Picture;

import java.util.ArrayList;
import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.InnerViewHolder> {

    private static final String TAG = "PictureAdapter";
    private Context mContext;
    private List<Picture> mList;
    private List<Picture> mSelectList = new ArrayList<>();
    private int MAX_PIC_SELECT = 9;

    private OnClickListener onClickListener;
    private OnSelectItemChangeListener onSelectItemChangeListener;

    public List<Picture> getmSelectList() {
        return mSelectList;
    }

    public void setmSelectList(List<Picture> mSelectList) {
        this.mSelectList = mSelectList;
    }

    public int getMaxPicSelect() {
        return MAX_PIC_SELECT;
    }

    public void setMaxPicSelect(int maxPicSelect) {
        MAX_PIC_SELECT = maxPicSelect;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnSelectItemChangeListener(OnSelectItemChangeListener onSelectItemChangeListener) {
        this.onSelectItemChangeListener = onSelectItemChangeListener;
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public interface OnSelectItemChangeListener {
        void onSelectItemChange(List<Picture> mSelectList);
    }

    public PictureAdapter(Context mContext, List<Picture> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false);
        Point point = new Point();
        ((WindowManager) parent.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(point.x / 3, point.x / 3);
        view.setLayoutParams(layoutParams);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }


    public class InnerViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPicture;
        private View mCoverView;
        private CheckBox mCbCheck;


        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvPicture = itemView.findViewById(R.id.iv_picture);
            mCoverView = itemView.findViewById(R.id.image_cover);
            mCbCheck = itemView.findViewById(R.id.cb_check);
            mCoverView.setVisibility(View.INVISIBLE);
            mCbCheck.setChecked(false);
        }

        public void setData(int position) {
            Picture picture = mList.get(position);
            Glide.with(mContext).load(picture.getPath()).into(mIvPicture);
            if (mSelectList.contains(picture)) {
                mCoverView.setVisibility(View.VISIBLE);
                mCbCheck.setChecked(true);
            } else {
                mCoverView.setVisibility(View.GONE);
                mCbCheck.setChecked(false);
            }
            mCbCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectList.contains(picture)) {
                        mCbCheck.setChecked(false);
                        mSelectList.remove(picture);
                        mCoverView.setVisibility(View.GONE);
                    } else if (mSelectList.size() < MAX_PIC_SELECT) {
                        mCbCheck.setChecked(true);
                        mSelectList.add(picture);
                        mCoverView.setVisibility(View.VISIBLE);
                    } else {
                        mCbCheck.setChecked(false);
                    }
                    onSelectItemChangeListener.onSelectItemChange(mSelectList);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(position);
                }
            });
        }
    }
}
