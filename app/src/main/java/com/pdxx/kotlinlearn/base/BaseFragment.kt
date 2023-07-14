package com.pdxx.kotlinlearn.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

/**
 * Fragment抽象基类
 */
abstract class BaseFragment : Fragment {  //Fragment() 可以写带括号 带构造的
    //无参构造函数 调用无参
    constructor() : super()

    //有参构造函数
    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    // private var mBinding: FragmentCourseBinding? = null
    private var mBinding: ViewDataBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding = FragmentCourseBinding.inflate(inflater,container,false)
//        return binding.root
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mBinding = FragmentCourseBinding.bind(view)
        mBinding = bindView(view, savedInstanceState)
//        mBinding?.apply {
//            lifecycleOwner = viewLifecycleOwner
//        }
        //目前用到的逻辑就这个一个 就这样写
        mBinding?.lifecycleOwner = viewLifecycleOwner
        initConfig()
        initData()
    }

    //有的不用这个 所以不用
    open fun initConfig(){}

    open fun initData(){}

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }


    /**
     * 扩展LiveData的observe函数
     */
    protected inline fun <T : Any> LiveData<T>.observerKt(crossinline block: (T?) -> Unit) {
        this.observe(viewLifecycleOwner) { data ->
//            block.invoke(data)
            block(data)//这个是简写的
        }
    }

    /* 复制过来 到这边来修改
    private var mBinding: FragmentCourseBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentCourseBinding.bind(view)
        mBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCourseBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

     */
}