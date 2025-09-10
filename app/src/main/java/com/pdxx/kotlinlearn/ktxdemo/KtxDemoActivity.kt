package com.pdxx.kotlinlearn.ktxdemo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.databinding.ActivityKtxDemoBinding

class KtxDemoActivity : AppCompatActivity() {

    private val viewModel: KtxDemoViewModel by viewModels()
    private lateinit var binding: ActivityKtxDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ktx_demo)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
