package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.databinding.ActivityKotlinBasicOneBinding

class KotlinBasicOneActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityKotlinBasicOneBinding>(
            this, R.layout.activity_kotlin_basic_one
        )
    }
}