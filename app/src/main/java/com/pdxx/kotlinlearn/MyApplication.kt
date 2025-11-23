package com.pdxx.kotlinlearn


import com.pdxx.kotlinlearn.base.BaseApplication
import com.pdxx.kotlinlearn.di.cnModules
import com.pdxx.kotlinlearn.koindemo.di.appModule
import leakcanary.AppWatcher
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MyApplication : BaseApplication() {

    private val modules = mutableListOf<Module>(
        cnModules,
        appModule
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

//    基本语法	     main 函数、println、注释、代码结构
//    变量与常量	  val vs var、类型推断
//    数据类型	    Int、Double、Boolean、String、Char
//    运算符	    算术、逻辑、比较、区间
//    控制流    	 if、when、for、while
//    函数	        参数默认值、命名参数、返回值、Unit


    private fun leakCanaryConfig() {
        //App 处于前台时检测保留对象的阈值，默认是 5
        LeakCanary.config = LeakCanary.config.copy(retainedVisibleThreshold = 3)

        //自定义要检测的保留对象类型，默认监测 Activity，Fragment，FragmentViews 和 ViewModels
        AppWatcher.config= AppWatcher.config.copy(watchFragmentViews = false)

        //隐藏泄漏显示活动启动器图标，默认为 true
        LeakCanary.showLeakDisplayActivityLauncherIcon(true)
    }
}