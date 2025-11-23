package com.pdxx.kotlinlearn.mvi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.pdxx.kotlinlearn.databinding.ListItemImageBinding
import com.pdxx.kotlinlearn.mvi.model.ImageItem

/**
 * Step 6.1: 创建RecyclerView Adapter
 * Adapter负责将数据显示在RecyclerView的Item上。
 */
class ImageAdapter(private var images: List<ImageItem>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    // ViewHolder持有对Item视图的引用
    class ImageViewHolder(val binding: ListItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        // 使用ViewBinding加载Item布局
        val binding = ListItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        holder.binding.tvImageTitle.text = image.title

        //做这一步的原因是为伪装为浏览器请求，不然请求不到图片
        // 1. 创建带请求头的 GlideUrl
        val glideUrl = GlideUrl(image.url, LazyHeaders.Builder()
            // 添加 Referer，通常是网站主域名
            .addHeader("Referer", "https://pic.netbian.com/")
            // 模拟一个常见的浏览器 User-Agent
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36")
            .build())


        // 使用Glide加载图片
        Glide.with(holder.itemView.context)
            .load(glideUrl)
            .into(holder.binding.ivImage)
    }

    override fun getItemCount(): Int = images.size

    // 用于更新Adapter数据的函数
    fun updateData(newImages: List<ImageItem>) {
        this.images = newImages
        // 提醒Adapter数据已变更，需要刷新整个列表
        // 在实际项目中，为了获得更好的性能，推荐使用DiffUtil来做差异化更新。
        notifyDataSetChanged()
    }
}
