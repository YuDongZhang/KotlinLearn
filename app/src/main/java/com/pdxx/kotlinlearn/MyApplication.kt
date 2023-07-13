package com.pdxx.kotlinlearn


import com.pdxx.kotlinlearn.base.BaseApplication
import com.pdxx.kotlinlearn.di.cnModules
import com.pdxx.kotlinlearn.ktx.application
import com.pdxx.kotlinlearn.utils.AssistantApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : BaseApplication() {


    override fun initConfig() {
        super.initConfig()
        AssistantApp.initConfig(application)
    }
}