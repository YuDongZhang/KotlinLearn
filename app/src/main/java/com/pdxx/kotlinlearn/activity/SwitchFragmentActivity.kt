package com.pdxx.kotlinlearn.activity


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.RadioGroup
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.frag.*

class SwitchFragmentActivity : AppCompatActivity() {

    var mItemFragment: ItemFragment? = null
    var mViewPagerFragment: ViewPagerFragment? = null
    var mScrollingFragment: ScrollingFragment? = null

    var mFragment01: Fragment01? = null
    var mFragment02: Fragment02? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_fragment)
        initView()

        // testManager()  测试 第一个 扩展方法

//        test03()


    }

    private fun initView() {

        val mRadioGroup = findViewById<RadioGroup>(R.id.radioGroup)
//        mRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener())
        mRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_1 -> {
                    /* if (mItemFragment==null){
                         mItemFragment = ItemFragment();
                         addFragment(mItemFragment!!,R.id.frame_layout)
                     }else{
                         showFragment(mItemFragment!!)
                     }*/
                    switchFragment(0)


                }
                R.id.rb_2 -> {
                    switchFragment(1)
                }
                R.id.rb_3 -> {
                    switchFragment(2)
                }

            }
        }
    }


    /**
     * 切换fragment 根据下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> mItemFragment?.let {
                transaction.show(it)
            } ?: ItemFragment().let {
                mItemFragment = it
                transaction.add(R.id.frame_layout, it)
            }
            1 -> mFragment01?.let {
                transaction.show(it)
            } ?: Fragment01().let {
                transaction.add(R.id.frame_layout, it)
            }
            2 -> mFragment02?.let {
                transaction.show(it)
            } ?: Fragment02().let {
                transaction.add(R.id.frame_layout, it)
            }
        }
        transaction.commit()
    }


    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     * //里面的it 代指前面的 , fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mItemFragment?.let { transaction.hide(it) }
        mViewPagerFragment?.let { transaction.hide(it) }
        mScrollingFragment?.let { transaction.hide(it) }
        mFragment01?.let { transaction.hide(it) }
        mFragment02?.let { transaction.hide(it) }
    }


    /*
    现在书写一个FragmentManager的扩展函数，它将一个带有接受者的Lambda函数作为传入的参数，而这个FragmentTransaction就是接收者对象。
     */
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()

        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
    /*
    上面这段代码是一个扩展函数，他接收的参数是一个带有接收者的Lambda函数。这个Lambda函数没有任何参数并且返回的是Unit。
    在这个inTransaction扩展函数体内首先调用beginTransaction()获取FragmentTransaction对象，然后在执行Lambda函数之后执行commit()方法。

    //用debug 方式跟踪了一下 , 发现在执行到 abcd 时候到了 , 上面的 add 方法中 , 看到流程 就是执行了 这 lambda 函数后到了 commit方法

    可以这么理解其实是传入了函数 , 将一个函数作为参数传进来 , 而这个函数 名 为 func ,其实是 传入的 add
    扩展函数对于原始方法修改 , 这个比较牛 , 继承都不用写
     */

    /**
     * 在 创建方法中测试这个方法 就可以了
     */
    fun test01() {
        supportFragmentManager.inTransaction {
            add(R.id.frame_layout, Fragment01())
        }
    }


    /*
    现在再次对inTransaction函数进行一次升级，可以将传入的Lambda函数添加一个返回值，返回FragmentTransaction对象。这样会使得我们的代码更加简洁。
     */
    inline fun FragmentManager.inTransactionTwo(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }
    /*
    这可以这么理解 , 就是 func 方法返回了一个 fragmnetTransaction, 然后直接调用 commit , 直接对比上面的方法看
     */

    //这个和test01的方法一样
    fun test02() {
        supportFragmentManager.inTransactionTwo {
            add(R.id.frame_layout, Fragment01())
        }
    }

    /*
    使用扩展函数来替代ActivityUtil , 对方法封装 用起来很方便
     */
    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransactionTwo {
            add(frameId, fragment)
        }
    }

    fun test03() {
        addFragment(Fragment01(), R.id.frame_layout)
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransactionTwo {
            replace(frameId, fragment)
        }
    }

    fun test04() {
        replaceFragment(Fragment02(), R.id.frame_layout)
    }

    fun AppCompatActivity.showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.show(fragment)
    }


}