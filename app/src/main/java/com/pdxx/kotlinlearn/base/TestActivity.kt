package com.pdxx.kotlinlearn.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pdxx.kotlinlearn.R
//有一种方式 class TestActivity : BaseActivity(R.layout.activity_test)
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}