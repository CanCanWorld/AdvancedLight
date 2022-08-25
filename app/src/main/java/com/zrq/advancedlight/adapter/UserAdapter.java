package com.zrq.advancedlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.entity.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.InnerViewHolder> {

    private Context mContext;
    private List<User> mData;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public UserAdapter(Context mContext, List<User> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public User removeData(int position) {
        User element = mData.get(position);
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, mData.size());
        return element;
    }


    public void addData(int position, User user){
        mData.add(position,user);
        notifyItemChanged(position,user);
        notifyItemRangeChanged(0, mData.size());
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvID;
        private TextView mTvUsername;
        private TextView mTvPassword;
        private TextView mTvSex;
        private Button mBtnDelete;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvID = itemView.findViewById(R.id.tv_id);
            mTvUsername = itemView.findViewById(R.id.tv_username);
            mTvPassword = itemView.findViewById(R.id.tv_password);
            mTvSex = itemView.findViewById(R.id.tv_sex);
            mBtnDelete = itemView.findViewById(R.id.btn_delete);
        }

        public void setData(int position) {
            mTvID.setText(String.valueOf(mData.get(position).getId()));
            mTvUsername.setText(mData.get(position).getUsername());
            mTvPassword.setText(mData.get(position).getPassword());
            mTvSex.setText(mData.get(position).getSex());
            mBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(position);
                }
            });
        }
    }
}
