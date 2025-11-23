package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.databinding.ActivityAndroidBasicOneBinding

class AndroidBasicOneActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = DataBindingUtil.setContentView<ActivityAndroidBasicOneBinding>(
            this, R.layout.activity_android_basic_one
        )
    }
}