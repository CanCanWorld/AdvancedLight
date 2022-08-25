package com.zrq.advancedlight.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zrq.advancedlight.R;
import com.zrq.advancedlight.entity.GetTextItem;

import java.util.List;

public class GetResultAdapter extends RecyclerView.Adapter<GetResultAdapter.InnerViewHolder> {
    private Context mContext;
    private List<GetTextItem.DataBean> mList;

    public GetResultAdapter(Context mContext, List<GetTextItem.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_get_text, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvCover;
        private TextView mTvTitle;
        private TextView mTvAuthor;
        private TextView mTvTime;
        private TextView mTvComment;
        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvCover = itemView.findViewById(R.id.item_cover);
            mTvTitle = itemView.findViewById(R.id.item_title);
            mTvAuthor = itemView.findViewById(R.id.item_author);
            mTvTime = itemView.findViewById(R.id.item_time);
            mTvComment = itemView.findViewById(R.id.item_comment);
        }

        @SuppressLint("CheckResult")
        public void setData(int position) {
            Glide.with(mContext).load("http://192.168.1.3:9102"+mList.get(position).getCover()).into(mIvCover);
            mTvTitle.setText(mList.get(position).getTitle());
            mTvAuthor.setText(String.valueOf(mList.get(position).getUserName()));
            mTvTime.setText(String.valueOf(mList.get(position).getPublishTime()));
            mTvComment.setText(String.valueOf(mList.get(position).getCommentCount()));

        }
    }
}
