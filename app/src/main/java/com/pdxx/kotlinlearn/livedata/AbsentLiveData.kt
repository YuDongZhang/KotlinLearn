package com.cainiaowo.common.livedata

import androidx.lifecycle.LiveData

/**
 * 创建一个空的LiveData的对象类
 */                          //构造函数私有化 , 你只能按照我们制定的来进行创建
class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {

    init {
        postValue(null)
    }

    //创建的时候伴生类
    companion object {
        fun <T : Any?> create(): LiveData<T> {
            return AbsentLiveData<T>()
        }
    }
}