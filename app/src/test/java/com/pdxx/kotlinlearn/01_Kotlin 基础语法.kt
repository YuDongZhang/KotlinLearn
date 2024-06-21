package com.pdxx.kotlinlearn


import org.junit.Test

class `01_Kotlin 基础语法` {

    @Test
    fun addition_isCorrect() {

    }

    /**
     * 函数定义
     */
    @Test
    fun function_definition() {
        // println(sum3(2, 3))
//        printSum(1,1)
//        printSum_1(1, 2)
//        vars(1,3,5)
        lambda_test()
    }

    /**
        : 的意思就是参数后面有类型 , 一般都是指类型
     */
    // Int 参数，返回值 Int
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    //表达式作为函数体，返回类型自动推断：
    fun sum1(a: Int, b: Int) = a + b;

    // public 方法则必须明确写出返回类型
    public fun sum3(a: Int, b: Int): Int = a + b


    //无返回值的函数(类似Java中的void)：
    fun printSum(a: Int, b: Int): Unit {
        print(a + b)
    }

    // 如果是返回 Unit类型，则可以省略(对于public方法也是这样)：
    public fun printSum_1(a: Int, b: Int) {
        print(a + b)
    }

// 可变长参数函数
// 函数的变长参数可以用 vararg 关键字进行标识：
    fun vars(vararg v: Int) {
        for (vt in v) {
            print(vt)
        }
    }

//    lambda(匿名函数)
//    lambda表达式使用实例：
    @Test
    fun lambda_test() {
        val sumLambda: (Int, Int) -> Int = { x, y -> x + y }
        println(sumLambda(1, 2))  // 输出 3
    }

    /**
     * 定义常量与变量
     *
     * var : 就是 变量
     */
    /**
    可变变量定义：var 关键字

    var <标识符> : <类型> = <初始化值>
    不可变变量定义：val 关键字，只能赋值一次的变量(类似Java中final修饰的变量)

    val <标识符> : <类型> = <初始化值>
    常量与变量都可以没有初始化值,但是在引用前必须初始化

    编译器支持自动类型判断,即声明时可以不指定类型,由编译器判断。*/

    @Test
    fun variable_test() {
        var b: Int = 2;
        var c: Int;
        val e = 1;//给 e 重新赋值会报错

        var a = 1
        // 模板中的简单名称：
        val s1 = "a is $a"

        a = 2
        // 模板中的任意表达式：
        val s2 = "${s1.replace("is", "was")}, but now is $a"

        print(s2)

    }

    /**
     * NULL检查机制
     * Kotlin的空安全设计对于声明可为空的参数，在使用时要进行空判断处理，有两种处理方式，字段后加!!像Java一样抛出空异常，
     * 另一种字段后加?可不做处理返回值为 null或配合?:做空判断处理
     */
    @Test
    fun null_test() {
        //类型后面加?表示可为空
        var age: String? = null;
        //抛出空指针异常
        val age1 = age!!.toInt()
        //不做处理返回 null
        val age2 = age?.toInt()
        //age为空返回-1
        val age3 = age?.toInt() ?: -1
        println(age);
        println(age1);
        println(age2);
        println(age3);
    }


    /**
     * 类型检测及自动类型转换
    我们可以使用 is 运算符检测一个表达式是否某类型的一个实例(类似于Java中的instanceof关键字)。
     */
    @Test
    fun typeConversion() {
        val stringLength = getStringLength(11)
        println(stringLength)


    }

    fun getStringLength(obj: Any): Int? {
        if (obj is String) {
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

//    或者

    fun getStringLength2(obj: Any): Int? {
        if (obj !is String)
            return null
        // 在这个分支中, `obj` 的类型会被自动转换为 `String`
        return obj.length
    }
//    甚至还可以

    fun getStringLength3(obj: Any): Int? {
        // 在 `&&` 运算符的右侧, `obj` 的类型会被自动转换为 `String`
        if (obj is String && obj.length > 0)
            return obj.length
        return null
    }

    /** 区间
    区间表达式由具有操作符形式 .. 的 rangeTo 函数辅以 in 和 !in 形成。

    区间是为任何可比较类型定义的，但对于整型原生类型，它有一个优化的实现。以下是使用区间的一些示例:*/

    @Test
    fun testQj() {
        for (i in 1..4) print(i) // 输出“1234”

        for (i in 4..1) print(i) // 什么都不输出

        var i = 0
        if (i in 1..10) { // 等同于 1 <= i && i <= 10
            println(i)
        }

// 使用 step 指定步长
        for (i in 1..4 step 2) print(i) // 输出“13”

        for (i in 4 downTo 1 step 2) print(i) // 输出“42”


// 使用 until 函数排除结束元素
        for (i in 1 until 10) {   // i in [1, 10) 排除了 10
            println(i)
        }
    }


}

