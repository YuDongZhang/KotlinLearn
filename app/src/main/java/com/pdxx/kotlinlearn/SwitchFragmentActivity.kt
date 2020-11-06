package com.pdxx.kotlinlearn


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.RadioGroup
import com.pdxx.kotlinlearn.frag.ItemFragment
import com.pdxx.kotlinlearn.frag.ScrollingFragment
import com.pdxx.kotlinlearn.frag.ViewPagerFragment
import java.text.FieldPosition

class SwitchFragmentActivity : AppCompatActivity() {

    var mItemFragment: ItemFragment? =null
    var mViewPagerFragment:ViewPagerFragment?=null
    var mScrollingFragment: ScrollingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_fragment)
        initView()
    }

    private fun initView() {


        val mRadioGroup = findViewById<RadioGroup>(R.id.radioGroup)
//        mRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener())
        mRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_1->{
                        if (mItemFragment==null){
                            mItemFragment = ItemFragment();
                            addFragment(mItemFragment!!,R.id.frame_layout)
                        }else{
                            showFragment(mItemFragment!!)
                        }

                }

            }
        }


        supportFragmentManager.inTransaction {
            add(R.id.frame_layout, mItemFragment)
        }
    }


    /**
     * 切换fragment 根据下标
     */
    private fun switchFragment(position: Int){
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when(position){
            0->mItemFragment?.let {
                transaction.show(it)
            }?:ItemFragment().let {
                mItemFragment = it
                transaction.add(R.id.frame_layout,it)
            }
        }
    }



    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mItemFragment?.let { transaction.hide(it) }
        mViewPagerFragment?.let { transaction.hide(it) }
        mScrollingFragment?.let { transaction.hide(it) }
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
    现在再次对inTransaction函数进行一次升级，可以将传入的Lambda函数添加一个返回值，返回FragmentTransaction对象。这样会使得我们的代码更加简洁。
     */
    inline fun FragmentManager.inTransactionTwo(func: FragmentTransaction.() -> FragmentTransaction){
        beginTransaction().func().commit()
    }

    /*
    使用扩展函数来替代ActivityUtil , 对方法封装 用起来很方便
     */
    fun AppCompatActivity.addFragment(fragment:Fragment,frameId :Int){
        supportFragmentManager.inTransactionTwo {
            add(frameId,fragment)
        }
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment,frameId: Int){
        supportFragmentManager.inTransactionTwo {
            replace(frameId,fragment)
        }
    }

    fun AppCompatActivity.showFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.show(fragment)
    }

}