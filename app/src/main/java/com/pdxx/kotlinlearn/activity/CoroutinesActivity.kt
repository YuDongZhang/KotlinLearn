package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.didichuxing.doraemonkit.util.LogUtils
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.base.BaseActivity
import com.pdxx.kotlinlearn.databinding.ActivityCoroutinesBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//协陈 各个子任务协作运行
class CoroutinesActivity : BaseActivity<ActivityCoroutinesBinding>() {

    override fun getLayoutRes(): Int {
       return  R.layout.activity_coroutines
    }

    override fun initView() {
        super.initView()
        mBinding.apply {
            bt1.setOnClickListener {

            }
        }
    }


}