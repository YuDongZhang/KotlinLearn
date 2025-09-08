package com.pdxx.kotlinlearn.koindemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.base.BaseActivity
import com.pdxx.kotlinlearn.databinding.ActivityKoinBinding

class KoinActivity : BaseActivity<ActivityKoinBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes() = R.layout.activity_koin

    override fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
}
