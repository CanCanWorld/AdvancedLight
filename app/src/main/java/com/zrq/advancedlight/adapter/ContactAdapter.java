package com.zrq.advancedlight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zrq.advancedlight.R;
import com.zrq.advancedlight.entity.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.InnerViewHolder> {

    private Context mContext;
    private List<Contact> mList;

    public ContactAdapter(Context mContext, List<Contact> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public InnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
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

        private TextView mTvName;
        private TextView mTvNumber;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvNumber = itemView.findViewById(R.id.tv_number);
        }

        public void setData(int position) {
            mTvName.setText(mList.get(position).getName());
            mTvNumber.setText(mList.get(position).getNumber());
        }
    }
}
