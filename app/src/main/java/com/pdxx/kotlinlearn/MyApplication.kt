package com.pdxx.kotlinlearn


import com.pdxx.kotlinlearn.base.BaseApplication
import com.pdxx.kotlinlearn.ktx.application
import com.pdxx.kotlinlearn.utils.AssistantApp

class MyApplication : BaseApplication() {
    override fun initConfig() {
        super.initConfig()
        AssistantApp.initConfig(application)
    }
}