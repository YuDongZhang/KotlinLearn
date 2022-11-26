package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.frag.Fragment01
import com.pdxx.kotlinlearn.frag.Fragment02

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var viewPager : ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        viewPager = findViewById(R.id.viewPager)
        var adapter = MyAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }


    class MyAdapter : FragmentPagerAdapter {

        var fragments: MutableList<Fragment> = ArrayList()

        constructor(fm: FragmentManager) : super(fm) {
            fragments.add(Fragment01())
            fragments.add(Fragment02())
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int = fragments.size
    }




}