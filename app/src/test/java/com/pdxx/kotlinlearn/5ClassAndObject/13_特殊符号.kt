package com.pdxx.kotlinlearn.`5ClassAndObject`

import org.junit.Test

class `13_特殊符号` {
    /** 一、? 与 !! 的比较 */
    val a: String = "aa"
    /** 直接这样定义 , a 是 非空类型 , 比如 a = null , 是报错的*/

    /**
     * ？声明是可空类型，可以赋值为null , 可以查看测试项
     * */
    var b: String? = "bb"

    @Test
    fun test() {
        b = null

        /**
         * b是可空类型，直接访问可空类型将编译不通过，需要通过?.或者!!.来访问
         * */
        // b.length  这样是错误 , 编译都不能通过

        b?.length
        b!!.length

        /**
        对于一个不可为空类型：
        如果直接给不可为空类型赋值一个可能为空的对象就在编译阶段就不能通过
        对于一个可空类型：
        通过？声明，在访问该类型的时候直接访问不能编译通过，需要通过?.或者!!.

        ?. 代表着如果该类型为空的话就返回null不做后续的操作，如果不为空的话才会去访问对应的方法或者属性
        !!. 代表着如果该类型为空的话就抛出NullPointerException，如果不为空就去访问对应的方法或者属性，
        所以只有在很少的特定场景才用这种符号，代表着程序不处理这种异常的case了，会像java代码一样抛出NullPointerException。
        而且代码中一定不用出现下面这种代码，会让代码可读性很差而且如果有空指针异常，我们也不能马上发现是哪空了：
         */
    }

    /** 二、let语句简化对可空对象对访问 */
    var c: String? = "ccc"
    var d: String? = null

    @Test
    fun test2() {
        /**
         * let函数默认当前这个对象作为闭包的it参数，返回值是函数里面最后一行，或者指定return。
         *
         * 通过let语句，在?.let之后，如果为空不会有任何操作，只有在非空的时候才会执行let之后的操作
         * */

        var e = c?.let {
            it.javaClass
            it.length
        }
        print(e)

        d?.let {
            it.length
        }

    }


    /** 三、?: 简化对空值的处理 */

    var f: String? = null

    @Test
    fun test3() {
        /** ?:符号会在符号左边为空的情况才会进行下面的处理，不为空则不会有任何操作。
         *   跟?.let正好相反，例如我们可以用两行代码来简化上面从操作：
         */
        var g = f?.length ?: -1
        print(g)
    }

    /**当调用函数的时候，不给参数。此时defaultValue() 进行默认参数赋值。*/
    @Test
    fun test4() {
        fun defaultValue(h: Int = 1, i: Int = 2): Int {
            return h + i
        }
        print(defaultValue())
    }


    /**
    ::  成员引用或类引用或函数引用
    这个符号不能理解 , 可以看下 startactivity   startActivity(Intent(this@KotlinActivity, MainActivity::class.java)) , 的到 类class 对象
     */
    @Test
    fun test5() {
        fun isOdd(x: Int) = x % 2 != 0
        fun isOdd(s: String) = s == "brillig" || s == "slithy" || s == "tove"
        val numbers: List<Int> = listOf(1, 2, 3)
        println(numbers.filter(::isOdd)) // refers to is0dd (x: Int)[1，3]
    }

    @Test
    fun test6() {
        var xx: Int? = null

        xx?.let { println("不为空") } ?: 5.let { println("为空" + it) }
    }


}