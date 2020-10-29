package com.pdxx.kotlinlearn


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.pdxx.kotlinlearn.frag.ItemFragment

class SwitchFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_fragment)
        initView()
    }

    private fun initView() {
        val itemFragment = ItemFragment()
        supportFragmentManager.inTransaction {
            add(R.id.frame_layout, itemFragment)
        }
    }


    /*
    现在书写一个FragmentManager的扩展函数，它将一个带有接受者的Lambda函数作为传入的参数，而这个FragmentTransaction就是接收者对象。
     */
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}