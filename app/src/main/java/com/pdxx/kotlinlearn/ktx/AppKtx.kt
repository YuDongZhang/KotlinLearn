package com.pdxx.kotlinlearn.ktx

import android.app.Application

/**
 * Application扩展
 */
//这种扩展的写法, 可以直接用 application 小写来使用
val Application.application: Application
    get() = this