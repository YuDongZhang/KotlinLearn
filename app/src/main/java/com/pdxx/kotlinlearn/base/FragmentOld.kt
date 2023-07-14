package com.pdxx.kotlinlearn.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pdxx.kotlinlearn.databinding.Fragment01Binding
import com.pdxx.kotlinlearn.vm.VmOne
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentOld : Fragment() {


    private var mBinding :Fragment01Binding?=null;

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
//        val view =  inflater.inflate(R.layout.fragment_01,container,false)
        val binding = Fragment01Binding.inflate(inflater,container,false)
        mBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = Fragment01Binding.bind(view)
        mBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

}