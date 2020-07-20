package com.pdxx.xxpt.kotlinlearn

import org.jetbrains.annotations.TestOnly

class KtTest {


    /* 字符串模板
     $ 表示一个变量名或者变量值
     $varName 表示变量值
     ${varName.fun()} 表示变量的方法返回值:*/



    fun test() {
        var a = 1;
        //// 模板中的简单名称：
        val s1 = "a is $a"
        a = 2
        val s2 = "${s1.replace("is", "was")},but now is $a"
    }

    /*
    NULL检查机制
    Kotlin的空安全设计对于声明可为空的参数，在使用时要进行空判断处理，有两种处理方式，
    字段后加!!像Java一样抛出空异常，另一种字段后加?可不做处理返回值为 null或配合?:做空判断处理
     */
    fun testNull() {
        //类型后面加?表示可为空
        var age: String? = "23"
        //抛出空指针异常
        val ages = age!!.toInt()
        //不做处理返回 null
        val ages1 = age?.toInt()
        //age为空返回-1
        val ages2 = age?.toInt() ?: -1
    }

    /*当一个引用可能为 null 值时, 对应的类型声明必须明确地标记为可为 null。
    当 str 中的字符串内容不是一个整数时, 返回 null:*/

    fun parseInt(str: String): Int? {
        // ...
        return null
    }
    //以下实例演示如何使用一个返回值可为 null 的函数:

    fun main(args: ArrayList<String>) {
        if (args.size < 2) {
            print("Two integers expected")
            return
        }

        var x = parseInt(args[0])
        var y = parseInt(args[1])
        // 直接使用 `x * y` 会导致错误, 因为它们可能为 null.
        if (x != null && y != null)
        // 在进行过 null 值检查之后, x 和 y 的类型会被自动转换为非 null 变量
            print(x * y)
    }

    /*
    类型检测及自动类型转换
    我们可以使用 is 运算符检测一个表达式是否某类型的一个实例(类似于Java中的instanceof关键字)。
     */
    fun getStringLength(obj: Any): Int? {
        if (obj is String){
            // 做过类型判断以后，obj会被系统自动转换为String类型
            return obj.length
        }

        //在这里还有一种方法，与Java中instanceof不同，使用!is
        // if (obj !is String){
        //   // XXX
        // }

        // 这里的obj仍然是Any类型的引用
        return null
    }

    //或者
    fun getStringLength1(obj:Any):Int?{
        //
        if (obj is String && obj.length>0){
            return obj.length

        }
        return null
    }

    //或者
    fun getStringLength2(obj: Any): Int? {
        if (obj !is String)
            return null
        // 在这个分支中, `obj` 的类型会被自动转换为 `String`
        return obj.length
    }

    /*
    区间
    区间表达式由具有操作符形式 .. 的 rangeTo 函数辅以 in 和 !in 形成。
    区间是为任何可比较类型定义的，但对于整型原生类型，它有一个优化的实现。以下是使用区间的一些示例:
     */
    fun testSection(){
        for (i in 1..4) print(i) // 输出“1234”

        for (i in 4..1) print(i) // 什么都不输出


        /*if (i in 1..10) { // 等同于 1 <= i && i <= 10
            println(i)
        }*/

// 使用 step 指定步长
        for (i in 1..4 step 2) print(i) // 输出“13”

        for (i in 4 downTo 1 step 2) print(i) // 输出“42”


// 使用 until 函数排除结束元素
        for (i in 1 until 10) {   // i in [1, 10) 排除了 10
            println(i)
        }
    }

}