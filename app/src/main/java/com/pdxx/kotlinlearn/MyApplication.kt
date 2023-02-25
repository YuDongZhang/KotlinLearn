package com.pdxx.kotlinlearn

import com.cainiaowo.common.BaseApplication
import com.cainiaowo.common.ktx.application
import com.cainiaowo.service.assistant.AssistantApp
import com.pdxx.kotlinlearn.base.BaseApplication

class MyApplication : BaseApplication() {
    override fun initConfig() {
        super.initConfig()
        AssistantApp.initConfig(application)
    }
}