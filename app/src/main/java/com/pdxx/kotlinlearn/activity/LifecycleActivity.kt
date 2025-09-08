package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.pdxx.kotlinlearn.R

class LifecycleActivity : AppCompatActivity() {

    private lateinit var tvLifecycleLog: TextView
    private lateinit var tvLifecycleInfo: TextView
    private val lifecycleLog = StringBuilder()

    companion object {
        private const val TAG = "LifecycleActivity"
    }

    // 1. onCreate: Activity 创建时调用
    // 这是 Activity 生命周期的第一个回调。
    // 通常在此方法中执行一次性的初始化操作，例如：
    // - 设置布局文件 (setContentView)
    // - 初始化 View 和变量
    // - 绑定数据到列表
    // - 将 ViewModel 与此 Activity 关联
    // - 实例化一些类作用域的变量
    // savedInstanceState: 如果 Activity 被系统销毁后重建，此 Bundle 对象会包含上次销毁前 onSaveInstanceState 中保存的状态。
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        tvLifecycleLog = findViewById(R.id.tv_lifecycle_log)
        tvLifecycleInfo = findViewById(R.id.tv_lifecycle_info)

        // 添加一个 LifecycleObserver 来监听生命周期事件
        lifecycle.addObserver(MyLifecycleObserver())

        log("onCreate")
        updateInfo()
    }

    // 2. onStart: Activity 即将对用户可见时调用
    // 当 Activity 进入 onStart() 状态，它就对用户可见了。
    // 这个回调很快就会完成，然后进入 onResume() 状态。
    // onStart() 和 onStop() 是成对出现的，对应着 Activity 是否对用户可见。
    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    // 3. onResume: Activity 开始与用户交互时调用
    // 此时 Activity 位于前台，并且用户可以与其进行交互。
    // 应用会一直停留在此状态，直到某些事件发生，例如：
    // - 接到电话
    // - 用户导航到另一个 Activity
    // - 设备屏幕关闭
    // onResume() 和 onPause() 是成对出现的，对应着 Activity 是否位于前台并可交互。
    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    // 4. onPause: 系统即将启动另一个 Activity 或当前 Activity 不再位于前台时调用
    // 通常在此方法中：
    // - 停止动画或其他消耗 CPU 的操作。
    // - 提交未保存的更改，但前提是用户希望在离开时自动保存这些更改。
    // - 释放系统资源，例如广播接收器、传感器句柄（如 GPS）或任何可能影响电池寿命的资源。
    // onPause() 的执行非常简短，不应执行耗时操作。
    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    // 5. onStop: Activity 对用户不再可见时调用
    // 这可能是因为：
    // - 启动了一个新的 Activity 并覆盖了当前 Activity。
    // - 当前 Activity 被切换到后台。
    // 在 onStop() 中，应用应释放或调整在应用对用户不可见时不需要的资源。
    // 例如，应用可以暂停动画效果，或从精确位置更新切换到粗略位置更新。
    // onStop() 方法必须始终在 onSaveInstanceState() 方法之后完成。
    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    // 6. onRestart: Activity 从 onStop 状态再次启动时调用
    // 当用户从后台切换回 Activity 时，会调用 onRestart()，然后紧接着调用 onStart()。
    override fun onRestart() {
        super.onRestart()
        log("onRestart")
    }

    // 7. onDestroy: Activity 被销毁前调用
    // 这是 Activity 生命周期中的最后一个回调。
    // 发生原因：
    // - Activity 即将结束（用户完全关闭了 Activity 或对 Activity 调用了 finish()）。
    // - 由于配置变更（例如设备旋转或多窗口模式），系统暂时销毁 Activity。
    // 在此方法中，应释放之前获取的所有资源，以避免内存泄漏。
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    /**
     * 使用 LifecycleObserver 监听生命周期
     * 这是另一种处理生命周期的方式，可以将生命周期逻辑从 Activity/Fragment 中分离出来，
     * 使代码更清晰、更易于管理和测试。
     * 任何类都可以实现 LifecycleObserver 接口，并通过 lifecycle.addObserver() 方法进行注册。
     */
    inner class MyLifecycleObserver : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreateEvent() {
            log("LifecycleObserver: ON_CREATE")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStartEvent() {
            log("LifecycleObserver: ON_START")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResumeEvent() {
            log("LifecycleObserver: ON_RESUME")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPauseEvent() {
            log("LifecycleObserver: ON_PAUSE")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStopEvent() {
            log("LifecycleObserver: ON_STOP")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroyEvent() {
            log("LifecycleObserver: ON_DESTROY")
        }

        // 它可以监听所有生命周期事件
        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onAnyEvent() {
            // Log.d(TAG, "LifecycleObserver: ON_ANY")
        }
    }


    private fun log(message: String) {
        Log.d(TAG, message)
        lifecycleLog.append(message).append("")
        tvLifecycleLog.text = lifecycleLog.toString()
    }

    private fun updateInfo() {
        val info = """
            Lifecycle 学习笔记:

            1. onCreate:
            Activity首次创建时调用。用于执行全局初始化，如设置视图、绑定数据等。这是生命周期的起点。

            2. onStart:
            Activity对用户可见时调用。此时Activity已准备好进入前台，但还不能与用户交互。

            3. onResume:
            Activity进入前台并可以与用户交互时调用。Activity会停留在此状态，直到有中断事件发生（如电话、切换应用）。

            4. onPause:
            Activity失去前台焦点时调用，但仍然可见（例如，被一个透明或非全屏的Activity覆盖）。应在此处暂停消耗资源的操作。

            5. onStop:
            Activity完全不可见时调用，进入后台。应在此处释放不再需要的资源。

            6. onRestart:
            Activity从后台重新返回前台时调用，在onStop之后，onStart之前。

            7. onDestroy:
            Activity被销毁前调用。用于执行最终的清理工作，释放所有资源，避免内存泄漏。

            ---------------------------------

            生命周期感知组件 (LifecycleObserver):
            通过实现 LifecycleObserver 接口，可以将生命周期相关的逻辑从Activity/Fragment中分离出来，使代码更整洁。
            如此例中的 MyLifecycleObserver 所示，它通过注解 (@OnLifecycleEvent) 自动接收生命周期事件。
        """.trimIndent()
        tvLifecycleInfo.text = info
    }
}
