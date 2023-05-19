package com.zrq.advancedlight.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zrq.advancedlight.databinding.ItemPicBinding

/**
 * @Description:
 * @author zhangruiqian
 * @date 2023/5/18 18:07
 */
class PicAdapter(
    private val context: Context
) : RecyclerView.Adapter<PicAdapter.InnerViewHolder>() {
    inner class InnerViewHolder(itemView: View) : ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val binding = ItemPicBinding.inflate(LayoutInflater.from(context), parent, false)
        return InnerViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {

    }
}