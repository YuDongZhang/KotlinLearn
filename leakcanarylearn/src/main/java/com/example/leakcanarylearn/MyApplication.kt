package com.example.leakcanarylearn

import android.app.Application
import leakcanary.AppWatcher
import leakcanary.LeakCanary

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        leakCanaryConfig()
    }

    private fun leakCanaryConfig() {
        //App 处于前台时检测保留对象的阈值，默认是 5
        LeakCanary.config = LeakCanary.config.copy(retainedVisibleThreshold = 3)
        //自定义要检测的保留对象类型，默认监测 Activity，Fragment，FragmentViews 和 ViewModels
        AppWatcher.config= AppWatcher.config.copy(watchFragmentViews = false)
//        AppWatcher.manualInstall(this)
        //隐藏泄漏显示活动启动器图标，默认为 true
        LeakCanary.showLeakDisplayActivityLauncherIcon(false)
    }

}