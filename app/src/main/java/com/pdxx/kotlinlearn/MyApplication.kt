package com.pdxx.kotlinlearn


import com.pdxx.kotlinlearn.base.BaseApplication
import com.pdxx.kotlinlearn.di.cnModules
import com.pdxx.kotlinlearn.moduleFunny.moduleJoke
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

class MyApplication : BaseApplication() {

    private val modules = mutableListOf<Module>(
        cnModules,
        moduleJoke
    )

    override fun initConfig() {
        super.initConfig()

//        AssistantApp.initConfig(application)

        //配置koin
        loadKoinModules(modules)

    }
}