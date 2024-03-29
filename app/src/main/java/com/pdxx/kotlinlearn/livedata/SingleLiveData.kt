package com.cainiaowo.common.livedata

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 单事件响应的LiveData,只有一个接收者能接收到信息,可以避免不必要的业务的场景中的事件消费通知
 * 只有调用call的时候,Observer才能收到通知
 */
class SingleLiveData<T> : MutableLiveData<T>() {
    //原子化的标记
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "多个观察者存在的时候,只会有一个被通知到数据更新")
        }

        super.observe(owner, Observer {
            //是否被set值了, 之后 ,这样才会去通知
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    //可以可以传空值
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private const val TAG = "SingleLiveData"
    }
}