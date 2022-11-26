package com.pdxx.kotlinlearn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.bean.PersonEntity

/**
 *
 */

class MyAdapter(var personList : List<PersonEntity>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item,null);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = personList.get(position)
        holder.nameTv!!.text = person.name
        holder.ageTv!!.text = person.age.toString()
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(holder.itemView,position)
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{
        var nameTv : TextView? =null
        var ageTv : TextView? =null

        constructor(itemView:View) : super(itemView){
            nameTv = itemView.findViewById(R.id.name)
            ageTv = itemView.findViewById(R.id.age)
        }
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

}