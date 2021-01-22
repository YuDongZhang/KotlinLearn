package com.pdxx.kotlinlearn.adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.pdxx.kotlinlearn.bean.PersonEntity

class HaHaPagerAdapter(fragmentManager: FragmentManager,list: ArrayList<PersonEntity>) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}