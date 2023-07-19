package com.pdxx.kotlinlearn.moduleFunny

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.cainiaowo.netdemo.okhttp.CniaoService
import com.cainiaowo.netdemo.retrofit.KtRetrofit
import com.didichuxing.doraemonkit.util.LogUtils
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.base.BaseActivity
import com.pdxx.kotlinlearn.databinding.ActivityPagingBinding

class PagingActivity : BaseActivity<ActivityPagingBinding>(){

    override fun getLayoutRes(): Int {
        return R.layout.activity_paging
    }

    override fun initView() {
        super.initView()
        mBinding.apply {
            btTest.setOnClickListener {
                KtRetrofit.initConfig("https://course.api.cniao5.com/")
                    .getService(IFunnyService::class.java)
                    .getVideo(1,2).observe(this@PagingActivity, Observer {
                        LogUtils.d("mika retrofit liveRsp ${it.toString()}")
                    })
            }
        }
    }

    override fun initConfig() {
        super.initConfig()

    }

}