package com.pdxx.kotlinlearn.moduleFunny

import com.hym.netdemo.toLivedata

import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.base.BaseActivity
import com.pdxx.kotlinlearn.databinding.ActivityPagingBinding
import com.pdxx.kotlinlearn.net.KtRetrofit
import org.koin.androidx.viewmodel.ext.android.viewModel

class PagingActivity : BaseActivity<ActivityPagingBinding>(){

    private val viewModel:JokeViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_paging
    }

    override fun initView() {
        super.initView()

       val retrofitCall =  KtRetrofit.initConfig("https://api.apiopen.top/")
            .getService(IJokeService::class.java)
            .getVideo(1,1)

        val liveInfo = retrofitCall.toLivedata()

        liveInfo.observe(this) {
            mBinding.tvContent.text = liveInfo.value.toString()
        }

        mBinding.apply {
            btTest.setOnClickListener {
                viewModel.repoList()
            }
        }
    }

    override fun initConfig() {
        super.initConfig()

    }

}