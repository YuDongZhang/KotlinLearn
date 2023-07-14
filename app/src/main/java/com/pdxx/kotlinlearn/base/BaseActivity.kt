package com.pdxx.kotlinlearn.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.pdxx.kotlinlearn.ktx.bindView
import com.pdxx.kotlinlearn.ktx.viewLifecycleOwner


/**
 * Activity抽象基类
 */
abstract class BaseActivity<ActBinding : ViewDataBinding> : AppCompatActivity {

    //无参构造函数 调用无参
    constructor() : super()

    //有参构造函数
    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    //protected 作用于子类 , 子类可以取到对象
    protected lateinit var mBinding: ActBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //需要一个具体类型这样写是不行的 , 需要上面传过来
        //bindView<ViewDataBinding>(this,getLayoutRes())//bingview()做了扩展函数封装不需要写this
        mBinding = bindView<ActBinding>(getLayoutRes())
        initConfig()
        initView()
        initData()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    open fun initConfig() {}

    open fun initView() {}

    open fun initData() {}

    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }

    /**
     * 扩展LiveData的observe函数
     */                                        //接受代码块 匿名函数
    protected inline fun <T : Any> LiveData<T>.observerKt(crossinline block: (T?) -> Unit) {
        this.observe(viewLifecycleOwner) { data ->
            block.invoke(data)//执行代码块
        }
    }

    //和上面写法差不多                    //上面的灰色是未调用
    protected fun <T : Any> LiveData<T>.observeKt2(block: (T?) -> Unit) {
        this.observe(this@BaseActivity, Observer { data ->
            block(data)                       //这灰色是可以精简
        })
    }

    ////观察值
    //        liveAppleData.observe(this, Observer {
    //            binding.tvLiveDataActivity.text = it
    //            Log.d("LiveActivity", "LiveData在LiveActivity中 $it")
    //        })
}