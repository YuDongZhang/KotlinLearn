package com.pdxx.kotlinlearn.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.pdxx.kotlinlearn.R

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var viewPager :ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        viewPager = findViewById(R.id.viewPager)

    }
}