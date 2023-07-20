package com.pdxx.kotlinlearn.moduleFunny

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer

import com.didichuxing.doraemonkit.util.LogUtils
import com.hym.netdemo.toLivedata

import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.base.BaseActivity
import com.pdxx.kotlinlearn.databinding.ActivityPagingBinding
import com.pdxx.kotlinlearn.net.KtRetrofit

class PagingActivity : BaseActivity<ActivityPagingBinding>(){

    override fun getLayoutRes(): Int {
        return R.layout.activity_paging
    }

    override fun initView() {
        super.initView()

       val retrofitCall =  KtRetrofit.initConfig("https://api.apiopen.top/")
            .getService(IFunnyService::class.java)
            .getVideo(1,1)

        val liveInfo = retrofitCall.toLivedata()

        liveInfo.observe(this) {
            mBinding.tvContent.text = liveInfo.value.toString()
        }

        mBinding.apply {
            btTest.setOnClickListener {

            }
        }
    }

    override fun initConfig() {
        super.initConfig()

    }

}