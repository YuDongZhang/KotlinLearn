package com.pdxx.kotlinlearn.activity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.databinding.ActivityMvvmOneBinding
import com.pdxx.kotlinlearn.vm.CounterViewModel

class MvvmOneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmOneBinding//声明绑定类的变量
    private lateinit var viewModel: CounterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvvmOneBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(CounterViewModel::class.java)

        binding.apply {
            bt1.setOnClickListener{
                viewModel.incrementCounter()
            }
        }

        viewModel.counter.observe(this) { count ->
            binding.tv1.text = count.toString()
        }

    }


}