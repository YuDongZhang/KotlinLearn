package com.pdxx.kotlinlearn.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.base.BaseActivity
import com.pdxx.kotlinlearn.databinding.ActivityMviBinding
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MviActivity : BaseActivity<ActivityMviBinding>() {

    private val viewModel = TodoListModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_mvi
    }

    override fun initView() {
        super.initView()
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        val adapter = TodoAdapter()
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter
//
//        viewModel.todoListLiveData.observe(this, { todoList ->
//            adapter.submitList(todoList)
//        })
//
//        val addButton = findViewById<Button>(R.id.addButton)
//        addButton.setOnClickListener {
//            val title = "New Task" // 你可以根据用户输入获取标题
//            viewModel.addTodo(title)
//        }
    }


}