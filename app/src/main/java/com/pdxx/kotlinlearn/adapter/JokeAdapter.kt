package com.pdxx.kotlinlearn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.moduleFunny.JokeBean

class JokeAdapter : PagingDataAdapter<JokeBean.Result.Desc, JokeViewHolder>(

    object : DiffUtil.ItemCallback<JokeBean.Result.Desc>() {

        override fun areItemsTheSame(
            oldItem: JokeBean.Result.Desc,
            newItem: JokeBean.Result.Desc
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(
            oldItem: JokeBean.Result.Desc,
            newItem: JokeBean.Result.Desc
        ): Boolean {
            TODO("Not yet implemented")
        }

    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pic_video, parent, false)
        return JokeViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        TODO("Not yet implemented")
        val desc = getItem(position)
        desc?.let {
            holder.bind(desc)
        }
    }

}

class JokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(desc: JokeBean.Result.Desc) {

    }
}