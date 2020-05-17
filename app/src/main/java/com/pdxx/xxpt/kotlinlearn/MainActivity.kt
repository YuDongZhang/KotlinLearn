package com.pdxx.xxpt.kotlinlearn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
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

    fun sum(a: Int, b: Int): Int {// Int 参数，返回值 Int
        return a + b
    }

    //函数定义
    fun sum_a(a: Int, b: Int) = a + b

    //public 方法必须明确写出返回值的类型
    public fun sum_b(a: Int, b: Int): Int = a + b

    //无返回值的函数(类似Java中的void)：
    fun printSum(a: Int, b: Int): Unit {
        print(a + b)
    }

    // 如果是返回 Unit类型，则可以省略(对于public方法也是这样)：
    public fun printSumA(a: Int, b: Int) {
        print(a + b)
    }

    //可变长参数函数
    //函数的变长参数可以用 vararg 关键字进行标识：
    fun vars(vararg v: Int) {
        for (vt in v) {
            print(vt)
        }
    }

    //测试
    fun main(args: ArrayList<String>) {
        vars(1, 2, 3, 4)// 输出12345
    }

    @OnClick(R.id.tv1, R.id.tv2, R.id.tv3)
    fun onClickView(view: View) {
        when (view.id) {
            R.id.tv1 -> {
                sum(1, 2)
            }
            R.id.tv2 -> {

            }
        }
    }

    // lambda(匿名函数)
    // lambda表达式使用实例：
    fun mainA(args: ArrayList<String>) {
        val sumLambda: (Int, Int) -> Int = { x, y -> x + y }
        print(sumLambda(1, 2))// 输出 3
    }

    //定义常量与变量
    //可变变量定义：var 关键字
    //var <标识符> : <类型> = <初始化值>
    //不可变变量定义：val 关键字，只能赋值一次的变量(类似Java中final修饰的变量)
    //常量与变量都可以没有初始化值,但是在引用前必须初始化
    //编译器支持自动类型判断,即声明时可以不指定类型,由编译器判断。
    val d: Int = 1
    val b = 1    // 系统自动推断变量类型为Int
    //val c: Int  // 如果不在声明时初始化则必须提供变量类型
    //r=1       // 明确赋值

    var x = 5  // 系统自动推断变量类型为Int
    //r += 1  // 变量可修改

    /*
    字符串模板
    $ 表示一个变量名或者变量值

    $varName 表示变量值

    ${varName.fun()} 表示变量的方法返回值:
     */




    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }


}
