package com.pdxx.kotlinlearn

import org.junit.Test

class `05_方法进阶` {
    /**
     * 高阶函数是将函数用作参数或返回值的函数
     */
    //目标: 对集合求和 , 并且返回每一项
    //接收callback作为回调
    //           sum扩展  callback:(int)回调,(int)是集合元素 unit是没有返回值  : int是方法返回值
    fun List<Int>.sum(callback: (Int) -> Unit): Int {
        var result = 0
        for (v in this) {//this表示扩展的本身 , 代表list
            result += v
            callback(v)
        }
        return result;
    }

    @Test
    fun test1() {
        val list = listOf(1, 2, 3)
        var result = list.sum { println("it:${it}") }//这里打印的就是回调  , it 可以理解为你传入的这个函数的结果 (单次)
        println(result)
    }

    /**
     * 函数作为返回值
     * 需求：实现一个能够对集合元素进行求和的高阶函数，并且返回一个 声明为(scale: Int) -> Float的函数
     */
    // : 后面是返回的类型 (scale: Int) -> Float ,即是这个函数
    fun List<String>.toIntSum(): (scale: Int) -> Float {
        println("第一层函数")
        return fun(scale): Float {
            var result = 0f
            for (v in this) {
                result += v.toInt() * scale
            }
            return result
        }
    }

    //这是一个求List元素和的扩展，它返回一个(scale: Int) -> Float 函数而被成为高阶函数
    @Test
    fun test2() {
        val listString = listOf("1", "2", "3")
        val result2 = listString.toIntSum()(2)
        println("计算结果：${result2}")
    }


    /**
     * 闭包可以理解为可以读取其他方法内部变量的方法
     *
     * 将方法内部和外部链接起来的桥梁
     *
     * 方法可以作为另一个方法的返回值或参数，还可以作为一个变量的值。
     * 方法可以嵌套定义，即在一个方法内部可以定义另一个方法。

    加强模块化：闭包有益于模块化编程，它能以简单的方式开发较小的模块，从而提高开发速度和程序的可复用性
    抽象：闭包是数据和行为的组合，这使得闭包具有较好抽象能力
    灵活：闭包的应用是的编程更加的灵活 简化代码：闭包还能简化代码
     */

//    需求：实现一个接受一个testClosure方法，该方法要接受一个Int类型的v1参数，
//    同时能够返回一个声明为(v2: Int, (Int) -> Unit)的函数，
//    并且这个函数能够计算v1与v2的和
    //(Int, (Int) -> Unit) -> Unit 返回的类型

    fun testClosure(v1: Int): (Int, (Int) -> Unit) -> Unit { //(Int, (Int) -> Unit就是方法
        return fun(v2: Int, printer: (Int) -> Unit) {//和上面的类型对应的
            printer(v1 + v2)//printer 就是上面的方法
        }
    }
    //接收一个参数v1,然后返回了后面的函数(Int, (Int) -> Unit),返回的函数里面将这个(Int) -> Unit作为参数进行传递


    @Test
    fun test3() {
        testClosure(1)(2) {//后面这个函数用的 lamb表达式
            println(it)
        }
        //即是闭包也是高级函数
    }

//    testClosure接收一个Int类型的参数，返回一个带有如下参数的方法(Int, (Int) -> Unit)，该方法第一个参数是Int类型，
//    第二个参数是一个接收Int类型参数的方法。 testClosure也是高阶方法


}