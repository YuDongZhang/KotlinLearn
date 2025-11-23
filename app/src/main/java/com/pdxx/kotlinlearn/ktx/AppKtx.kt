package com.pdxx.kotlinlearn.ktx

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.util.Log
import android.view.WindowManager

/**
 * Get screen width.
 */
val Context.screenWidth: Int
    get() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

/**
 * Get screen height.
 */
val Context.screenHeight: Int
    get() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.height
    }

/**
 * Get connectivity manager.
 */
val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

/**
 * Log debug message.
 */
fun Any.logd(message: String, tag: String = this.javaClass.simpleName) {
//    if (com.pdxx.kotlinlearn.BuildConfig.DEBUG) {
//        Log.d(tag, message)
//    }
}

/**
 * Log error message.
 */
fun Any.loge(message: String, tag: String = this.javaClass.simpleName) {
//    if (com.pdxx.kotlinlearn.BuildConfig.DEBUG) {
//        Log.e(tag, message)
//    }
}


/**
 * Application扩展
 */
//这种扩展的写法, 可以直接用 application 小写来使用
val Application.application: Application
    get() = this