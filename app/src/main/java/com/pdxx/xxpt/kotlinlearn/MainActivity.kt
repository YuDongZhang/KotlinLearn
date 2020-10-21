package com.pdxx.xxpt.kotlinlearn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import org.jetbrains.annotations.TestOnly


class MainActivity : AppCompatActivity() {

    @BindView(R.id.content)
    lateinit var tvContent: TextView
    var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        unbinder = ButterKnife.bind(this)
    }

    @OnClick(R.id.tv1, R.id.tv2, R.id.tv3)
    fun onClickView(view: View) {
        when (view.id) {
            R.id.tv1 -> {
                Toast.makeText(this, "点击了按钮1", Toast.LENGTH_SHORT).show()
            }
            R.id.tv2 -> {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }


}
