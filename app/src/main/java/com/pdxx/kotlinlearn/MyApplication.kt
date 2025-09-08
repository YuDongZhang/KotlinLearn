package com.pdxx.kotlinlearn


import com.pdxx.kotlinlearn.base.BaseApplication
import com.pdxx.kotlinlearn.di.cnModules
import leakcanary.AppWatcher
import leakcanary.LeakCanary
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

class MyApplication : BaseApplication() {

    private val modules = mutableListOf<Module>(
        cnModules,
//        moduleJoke
    )

    override fun initConfig() {
        super.initConfig()

//        AssistantApp.initConfig(application)

        //配置koin
        loadKoinModules(modules)

        //配置 内存泄漏检测
        leakCanaryConfig()

    }


    private fun leakCanaryConfig() {
        //App 处于前台时检测保留对象的阈值，默认是 5
        LeakCanary.config = LeakCanary.config.copy(retainedVisibleThreshold = 3)

        //自定义要检测的保留对象类型，默认监测 Activity，Fragment，FragmentViews 和 ViewModels
        AppWatcher.config= AppWatcher.config.copy(watchFragmentViews = false)

        //隐藏泄漏显示活动启动器图标，默认为 true
        LeakCanary.showLeakDisplayActivityLauncherIcon(true)
    }
}