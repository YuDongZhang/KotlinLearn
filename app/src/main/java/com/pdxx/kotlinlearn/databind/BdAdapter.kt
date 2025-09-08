package com.pdxx.kotlinlearn.databind

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pdxx.kotlinlearn.databinding.ItemRvBinding


class BdAdapter : RecyclerView.Adapter<BdAdapter.ItVH>() {

    private val mList = mutableListOf<ItemBean>()

    init {
        for (i in 0..5) {
            mList.add(ItemBean(i, "艾特木 $i"))
        }
    }

    //itemrvbingding就是布局 , 可以直接使用
    class ItVH(private val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bean: ItemBean) {
            binding.info = bean
            binding.executePendingBindings() //让binding数据生效 必须写的一个函数回调
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItVH {
        return ItVH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: ItVH, position: Int) {
        holder.bind(mList[position])
    }
}

data class ItemBean(val type: Int, val text: String)