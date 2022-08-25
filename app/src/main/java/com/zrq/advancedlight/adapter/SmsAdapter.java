package com.zrq.advancedlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.entity.Sms;

import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.InnerViewHolder> {

    private Context mContext;
    private List<Sms> mList;

    public SmsAdapter(Context mContext, List<Sms> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sms, parent, false);
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

        private TextView mTvAddress;
        private TextView mTvDate;
        private TextView mTvBody;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvAddress = itemView.findViewById(R.id.tv_address);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mTvBody = itemView.findViewById(R.id.tv_body);
        }

        public void setData(int position) {
            mTvAddress.setText(mList.get(position).getAddress());
            mTvDate.setText(mList.get(position).getDate());
            mTvBody.setText(mList.get(position).getBody());
        }
    }
}
