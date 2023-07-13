package com.pdxx.kotlinlearn.frag

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.bean.PersonEntity
import com.pdxx.kotlinlearn.vm.VmOne
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class Fragment01 : Fragment() {

    //以后viewmodel创建就这么来  实践拿到了数据
    private val vmOne :VmOne by viewModel()//要看包名

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_01,container,false)
        Log.d("test0", "onCreateView: "+vmOne.getNum())
        return view
    }
}