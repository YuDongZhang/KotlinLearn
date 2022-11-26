package com.pdxx.kotlinlearn.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.bean.GeneralBean

class GeneralAdapter(var generalList: List<GeneralBean>) : RecyclerView.Adapter<GeneralAdapter.ViewHolder>() {

    var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_general, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var generalBean = generalList.get(position)
        holder.tvTitle?.text = generalBean.title
        holder.tvDetail?.text = generalBean.detail
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(holder.itemView, position)
            Log.e("TAG", "onBindViewHolder: ")
        }
    }

    override fun getItemCount(): Int {
        return generalList.size
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var tvTitle: TextView? = null
        var tvDetail: TextView? = null

        //        constructor(itemView: View?, tvTitle: TextView?, tvDetail: TextView?) : super(itemView) {
        constructor(itemView: View) : super(itemView) {
            this.tvTitle = itemView.findViewById(R.id.tv_title)
            this.tvDetail = itemView.findViewById(R.id.tv_detail)
        }
    }

}