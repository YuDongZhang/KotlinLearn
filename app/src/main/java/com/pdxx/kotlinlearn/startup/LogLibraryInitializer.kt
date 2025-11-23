package com.pdxx.kotlinlearn.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

/**
 * 示例 Initializer：模拟初始化日志库
 *
 * App Startup 允许您定义共享单个内容提供程序的组件初始化程序。
 * 这样可以显著缩短应用启动时间。
 */
class LogLibraryInitializer : Initializer<Unit> {

    /**
     * 执行初始化操作
     */
    override fun create(context: Context) {
        // 模拟初始化耗时操作
        Log.d("AppStartup", "LogLibraryInitializer: 初始化开始")
        Thread.sleep(100) // 仅作演示，实际不要在主线程 sleep
        Log.d("AppStartup", "LogLibraryInitializer: 初始化完成")
    }

    /**
     * 定义依赖项
     * 如果此 Initializer 依赖于其他 Initializer，则在此列表中返回它们。
     */
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
