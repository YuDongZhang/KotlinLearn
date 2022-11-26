package com.pdxx.kotlinlearn.activity

import android.os.Bundle

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.adapter.GeneralAdapter
import com.pdxx.kotlinlearn.bean.GeneralBean
import kotlin.math.log

class GeneralActivity : AppCompatActivity() {

    var generalList = ArrayList<GeneralBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        initData()
        initView()
    }

    private fun initData() {
        generalList.add(GeneralBean("你好", "heheh"))
        generalList.add(GeneralBean("我好", "heheh"))
        generalList.add(GeneralBean("他好", "heheh"))
        generalList.add(GeneralBean("大家好", "heheh"))

    }

    private fun initView() {
        var recyclerView = findViewById<RecyclerView>(R.id.recycler)
        var generalAdapter = GeneralAdapter(generalList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = generalAdapter
//        generalAdapter.setOnItemClickListener(GeneralAdapter.OnItemClickListener{})
        generalAdapter!!.setOnItemClickListener(object : GeneralAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Log.d("TAG", "onItemClick: ")
            }
        })
    }


}