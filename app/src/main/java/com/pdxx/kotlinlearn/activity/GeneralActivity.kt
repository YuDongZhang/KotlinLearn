package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.adapter.GeneralAdapter
import com.pdxx.kotlinlearn.bean.GeneralBean
import kotlin.math.log

class GeneralActivity : AppCompatActivity() {

    var generalList = ArrayList<GeneralBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        initData()
        initView()
        setupExamples()
    }

    private fun initData() {
        generalList.add(GeneralBean("你好", "heheh"))
        generalList.add(GeneralBean("我好", "heheh"))
        generalList.add(GeneralBean("他好", "heheh"))
        generalList.add(GeneralBean("大家好", "heheh"))
    }

    private fun initView() {
        var recyclerView = findViewById<RecyclerView>(R.id.recycler)
        var generalAdapter = GeneralAdapter(generalList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = generalAdapter
//        generalAdapter.setOnItemClickListener(GeneralAdapter.OnItemClickListener{})
        generalAdapter!!.setOnItemClickListener(object : GeneralAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Log.d("TAG", "onItemClick: ")
            }
        })
    }

    /**
     * 设置示例按钮的点击事件
     */
    private fun setupExamples() {
        val lambdaButton = findViewById<Button>(R.id.btnLambdaExample)
        val higherOrderButton = findViewById<Button>(R.id.btnHigherOrderExample)
        val anonymousButton = findViewById<Button>(R.id.btnAnonymousExample)
        val resultTextView = findViewById<TextView>(R.id.tvResult)

        // Lambda 表达式示例
        lambdaButton.setOnClickListener {
            // Lambda 表达式是一种简化定义函数的方式
            // 它可以省略函数名，直接用 { 参数 -> 函数体 } 的形式定义
            val lambdaExample = "Lambda 表达式示例:\n" +
                    "定义一个简单的 Lambda: { x: Int, y: Int -> x + y }\n" +
                    "调用结果: ${calculateWithLambda(3, 5) { x, y -> x + y }}"

            resultTextView.text = lambdaExample
        }

        // 高阶函数示例
        higherOrderButton.setOnClickListener {
            // 高阶函数是将函数用作参数或返回值的函数
            // 这里我们定义一个函数作为参数传递给另一个函数
            val higherOrderExample = "高阶函数示例:\n" +
                    "定义一个计算器函数，接收另一个函数作为参数\n" +
                    "调用结果: ${performCalculation(10, 5, ::multiply)}"

            resultTextView.text = higherOrderExample
        }

        // 匿名函数示例
        anonymousButton.setOnClickListener {
            // 匿名函数是没有名字的函数，与 Lambda 表达式类似但有一些区别
            // 匿名函数使用 fun 关键字但不指定函数名
            val anonymousExample = "匿名函数示例:\n" +
                    "定义一个匿名函数并调用\n" +
                    "调用结果: ${calculateWithAnonymousFunction(7, 3)}"

            resultTextView.text = anonymousExample
        }
    }

    /**
     * Lambda 表达式示例：接收两个整数和一个 Lambda 函数，返回计算结果
     * Lambda 表达式可以作为参数传递
     */
    private fun calculateWithLambda(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
        // operation 是一个 Lambda 表达式参数
        // 它接收两个 Int 参数并返回一个 Int 结果
        return operation(a, b)
    }

    /**
     * 高阶函数示例：接收两个整数和一个函数，返回计算结果
     * 这是一个高阶函数，因为它接收一个函数作为参数
     */
    private fun performCalculation(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
        // operation 是一个函数参数
        // 这使得 performCalculation 成为一个高阶函数
        return operation(a, b)
    }

    /**
     * 乘法函数，用于演示函数引用
     */
    private fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    /**
     * 匿名函数示例：使用匿名函数进行计算
     * 匿名函数使用 fun 关键字但没有函数名
     */
    private fun calculateWithAnonymousFunction(a: Int, b: Int): Int {
        // 定义一个匿名函数
        val anonymousFunc = fun(x: Int, y: Int): Int {
            return x - y
        }

        // 调用匿名函数
        return anonymousFunc(a, b)
    }
}