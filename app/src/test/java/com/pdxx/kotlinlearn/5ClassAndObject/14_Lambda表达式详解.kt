package com.pdxx.kotlinlearn.`5ClassAndObject`

import android.widget.Button
import org.junit.Test

class `14_Lambda表达式详解` {
    /** Lambda表达式的本质其实是匿名函数，因为在其底层实现中还是通过匿名函数来实现 */
    var bt: Button? = null

    /** 2.1、Lambda表达式的特点*/
    /**
    Lambda表达式总是被大括号括着
    其参数(如果存在)在 -> 之前声明(参数类型可以省略)
    函数体(如果存在)在 -> 后面。
     * */

    /** 2.2、Lambda语法*/

    /**
    1. 无参数的情况 ：
    val/var 变量名 = { 操作的代码 }

    2. 有参数的情况
    val/var 变量名 : (参数的类型，参数类型，...) -> 返回值类型 = {参数1，参数2，... -> 操作参数的代码 }

    可等价于
    // 此种写法：即表达式的返回值类型会根据操作的代码自推导出来。
    val/var 变量名 = { 参数1 ： 类型，参数2 : 类型, ... -> 操作参数的代码 }

    3. lambda表达式作为函数中的参数的时候，这里举一个例子：
    fun test(a : Int, 参数名 : (参数1 ： 类型，参数2 : 类型, ... ) -> 表达式返回类型){
    ...
    }
     * */
    @Test
    fun test1() {
        // 源代码 , 可以注销看
        fun test() {
            println("无参数")
        }

        // lambda代码 , 写法
        val test = { println("无参数") }

        // 调用
        test()  //=> 结果为：无参数
    }


    /** 有参数的情况,这里举例一个两个参数的例子，目的只为大家演示*/
    @Test
    fun test2() {
        // 源代码
        fun test(a: Int, b: Int): Int {
            return a + b
        }

        // lambda 写法
        val test2: (Int, Int) -> Int = { a, b -> a + b }  //这种直接说了返回类型
        //或者
        val test3 = { a: Int, b: Int -> a + b } //这种推断类型

        /** 可不可以这么理解 , 箭头指向的都是结果 ,
         * 第一行 : 箭头指向 Int 其实就是结果是一个 Int , 而这个 Int 等于的就是 大括号 里面的东西
         * 第二行 : 大括号中的箭头 , 指向是 a + b  , 其实就是 a + b之后的结果
         */
        test(1, 2)
        test2(5, 6)
        test3(3, 4)
    }


    /** lambda表达式作为函数中的参数的时候 */
    @Test
    fun test3() {
        // 源代码
        fun test(a: Int, b: Int): Int {
            return a + b
        }

        fun sum(num1: Int, num2: Int): Int {
            return num1 + num2
        }

        // 调用
        test(10, sum(3, 5)) // 结果为：18

        // lambda 方式
        fun test(a: Int, b: (num1: Int, num2: Int) -> Int): Int {
            return a + b.invoke(3, 5) //invoke()函数：表示为通过函数变量调用自身，因为上面例子中的变量b是一个匿名函数。
        }

        // 调用
        test(10, { num1: Int, num2: Int -> num1 + num2 })  // 结果为：18
    }

    /**
    1. lambda表达式总是被大括号括着。
    定义完整的Lambda表达式如上面实例中的语法
    2，它有其完整的参数类型标注，与表达式返回值。当我们把一些类型标注省略的情况下，
    就如上面实例中的语法2的另外一种类型。当它推断出的返回值类型不为'Unit'时，它的返回值即为->符号后面代码的最后一个（或只有一个）表达式的类型。
    在上面例子中语法3的情况表示为：高阶函数，当Lambda表达式作为其一个参数时，只为其表达式提供了参数类型与返回类型，
    所以，我们在调用此高阶函数的时候我们要为该Lambda表达式写出它的具体实现。
    invoke()函数：表示为通过函数变量调用自身，因为上面例子中的变量b是一个匿名函数。
     */

    /** 3、Lambda实践 */

    /**
    3.1、it
    it并不是Kotlin中的一个关键字(保留字)。

    it是在当一个高阶函数中Lambda表达式的参数只有一个的时候可以使用it来使用此参数。it可表示为单个参数的隐式名称，
    是Kotlin语言约定的。
    例1：
     */
    @Test
    fun test4() {
        //例1：
        val it: Int = 0  // 即it不是`Kotlin`中的关键字。可用于变量名称

        //例2：单个参数的隐式名称

        // 这里举例一个语言自带的一个高阶函数filter,此函数的作用是过滤掉不满足条件的值。
        val arr = arrayOf(1, 3, 5, 7, 9)
        // 过滤掉数组中元素小于2的元素，取其第一个打印。这里的it就表示每一个元素。
        println(arr.filter { it < 5 }.component1()) //这里取第一个

        fun test(num1: Int, bool: (Int) -> Boolean): Int {
            return if (bool(num1)) {
                num1
            } else 0
        }

        println(test(10, { it > 5 }))
        println(test(4, { it > 5 }))
        //代码讲解：上面的代码意思是，在高阶函数test中，其返回值为Int类型，Lambda表达式以num1位条件。其中如果Lambda表达式的值为false的时候返回0，反之返回num1。
        // 故而当条件为num1 > 5这个条件时，当调用test函数，num1 = 10返回值就是10，num1 = 4返回值就是0。
    }

    /** 3.2、下划线（_）*/
    /*
    在使用Lambda表达式的时候，可以用下划线(_)表示未使用的参数，表示不处理这个参数。
    同时在遍历一个Map集合的时候，这当非常有用。
     */
    @Test
    fun test5() {
        val map = mapOf("key1" to "value1", "key2" to "value2", "key3" to "value3")
        map.forEach { key, value ->
            println("$key \t $value")
        }

        // 不需要key的时候
        map.forEach { _, value ->
            println("$value")
        }
    }

    /** 3.3 匿名函数 */
    /*
    匿名函数的特点是可以明确指定其返回值类型。
    它和常规函数的定义几乎相似。他们的区别在于，匿名函数没有函数名。
     */
    @Test
    fun test6() {
        // 常规函数：
        fun test(x: Int, y: Int): Int {
            return x + y
        }

        // 匿名函数：
        fun(x: Int, y: Int): Int {
            return x + y
        }

        /*

        在前面的Kotlin——初级篇（七）：函数（方法）基础总结我们讲解过单表达式函数。故而，可以简写成下面的方式。

        常规函数 ： fun test(x : Int , y : Int) : Int = x + y
        匿名函数 ： fun(x : Int , y : Int) : Int = x + y
        从上面的两个例子可以看出，匿名函数与常规函数的区别在于一个有函数名，一个没有。

         实例演练：
         */


        val test1 = fun(x: Int, y: Int) = x + y  // 当返回值可以自动推断出来的时候，可以省略，和函数一样
        val test2 = fun(x: Int, y: Int): Int = x + y
        val test3 = fun(x: Int, y: Int): Int {
            return x + y
        }

        //lambda 表达式
        val test4 = { a: Int, b: Int -> a + b }
        val test5: (Int, Int) -> Int = { a, b -> a + b }

        println(test1(3, 5))
        println(test2(4, 6))
        println(test3(5, 7))

        println(test4(7, 8))
        println(test5(8, 9))

        /*
        输出结果为：
        8
        10
        12

        从上面的代码我们可以总结出匿名函数与Lambda表达式的几点区别：

        匿名函数的参数传值，总是在小括号内部传递。而Lambda表达式传值，可以有省略小括号的简写写法。
        在一个不带标签的return语句中，匿名函数时返回值是返回自身函数的值，而Lambda表达式的返回值是将包含它的函数中返回。

        比较像 : 要做区分
        */

    }

    /** 3.4、带接收者的函数字面值 */

    /*
    在kotlin中，提供了指定的接受者对象调用Lambda表达式的功能。在函数字面值的函数体中，可以调用该接收者对象上的方法而无需任何额外的限定符。
    它类似于扩展函数，它允你在函数体内访问接收者对象的成员。
     */

    @Test
    fun test7() {
        /**
        匿名函数作为接收者类型
        匿名函数语法允许你直接指定函数字面值的接收者类型，如果你需要使用带接收者的函数类型声明一个变量，并在之后使用它，这将非常有用。
        例：
         */
        val iop = fun Int.(other: Int): Int = this + other

        println(iop.invoke(1, 3))
    }

}