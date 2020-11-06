package com.pdxx.kotlinlearn.`5ClassAndObject`

import android.widget.Button
import org.junit.Test

class `14Lambda表达式详解` {
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

        // lambda代码
        val test = { println("无参数") }

        // 调用
        test()  //=> 结果为：无参数
    }


    /** 有参数的情况,这里举例一个两个参数的例子，目的只为大家演示*/
    @Test
    fun test2() {
        // 源代码
//        fun test(a: Int, b: Int): Int {
//            return a + b
//        }

        // lambda
        val test: (Int, Int) -> Int = { a, b -> a + b }  //这种直接说了返回类型
        //或者
        val test3 = { a: Int, b: Int -> a + b } //这种推断类型

        test(1, 2)
        test3(3, 4)
    }


    /** lambda表达式作为函数中的参数的时候 */
    @Test
    fun test3() {
        // 源代码
        fun test(a : Int , b : Int) : Int{
            return a + b
        }

        fun sum(num1 : Int , num2 : Int) : Int{
            return num1 + num2
        }

        // 调用
        test(10,sum(3,5)) // 结果为：18

        // lambda
        fun test(a : Int , b : (num1 : Int , num2 : Int) -> Int) : Int{
            return a + b.invoke(3,5)
        }

        // 调用
        test(10,{ num1: Int, num2: Int ->  num1 + num2 })  // 结果为：18
    }

    /**
    lambda表达式总是被大括号括着。
    定义完整的Lambda表达式如上面实例中的语法2，它有其完整的参数类型标注，与表达式返回值。当我们把一些类型标注省略的情况下，
    就如上面实例中的语法2的另外一种类型。当它推断出的返回值类型不为'Unit'时，它的返回值即为->符号后面代码的最后一个（或只有一个）表达式的类型。
    在上面例子中语法3的情况表示为：高阶函数，当Lambda表达式作为其一个参数时，只为其表达式提供了参数类型与返回类型，
    所以，我们在调用此高阶函数的时候我们要为该Lambda表达式写出它的具体实现。
    invoke()函数：表示为通过函数变量调用自身，因为上面例子中的变量b是一个匿名函数。
     */
}