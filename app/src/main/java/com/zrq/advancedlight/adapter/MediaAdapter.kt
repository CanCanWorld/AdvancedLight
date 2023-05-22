package com.zrq.advancedlight.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zrq.advancedlight.R
import com.zrq.advancedlight.databinding.ItemAddBinding
import com.zrq.advancedlight.databinding.ItemMediaBinding
import com.zrq.advancedlight.entity.Media
import com.zrq.advancedlight.entity.MediaType

/**
 * @Description:
 * @author zhangruiqian
 * @date 2023/5/18 18:07
 */
class MediaAdapter(
    private val context: Context,
    private val list: MutableList<Media> = mutableListOf(),
    private val onDelete: (Int) -> Unit,
    private val onAdd: () -> Unit,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<MediaAdapter.InnerViewHolder>() {
    inner class InnerViewHolder(val rootView: View) : ViewHolder(rootView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        return when (viewType) {
            END -> {
                val binding = ItemAddBinding.inflate(LayoutInflater.from(context), parent, false)
                InnerViewHolder(binding.root)
            }

            else -> {
                val binding = ItemMediaBinding.inflate(LayoutInflater.from(context), parent, false)
                InnerViewHolder(binding.root)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) END
        else OTHER
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        if (position == list.size) {
            //add
            val ivAdd = holder.rootView.findViewById<ImageView>(R.id.ivAdd)
            ivAdd.setOnClickListener {
                onAdd()
            }
        } else {
            val media = list[position]

            val ivCover = holder.rootView.findViewById<ImageView>(R.id.ivCover)
            val ivDelete = holder.rootView.findViewById<ImageView>(R.id.ivDelete)
            val ivPlay = holder.rootView.findViewById<ImageView>(R.id.ivPlay)

            when (media.type) {
                MediaType.Photo -> {
                    ivPlay.visibility = View.GONE
                    val options = BitmapFactory.Options()
                    options.inJustDecodeBounds = true
                    BitmapFactory.decodeFile(media.path, options)
                    options.inSampleSize = 10
                    options.inJustDecodeBounds = false
                    val bitmap = BitmapFactory.decodeFile(media.path, options)
                    ivCover.setImageBitmap(bitmap)
                }

                MediaType.Video -> {
                    ivPlay.visibility = View.VISIBLE
                    val mediaMetadataRetriever = MediaMetadataRetriever()
                    mediaMetadataRetriever.setDataSource(media.path)
                    ivCover.setImageBitmap(mediaMetadataRetriever.frameAtTime)
                }
            }
            ivDelete.setOnClickListener {
                onDelete(position)
            }
            ivCover.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    companion object {
        const val OTHER = 1
        const val END = 2
    }
}