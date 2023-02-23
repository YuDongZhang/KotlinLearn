package com.pdxx.kotlinlearn

import org.junit.Test


/**
Kotlin 对象表达式和对象声明

记忆 : 用一个对象对于类来进行改动 , 用表达式的方式来实现

Kotlin 用对象表达式和对象声明来实现创建一个对某个类做了轻微改动的类的对象，且不需要去声明一个新的子类。

关键字   object

Java当中匿名内部类在很多场景下大量使用。Kotlin的对象表达式就是为了解决匿名内部类的一些缺陷而产生的。

1.匿名内部类是没有名字的类
2.匿名内部类一定是继承了某个父类，或是实现了某个接口
3.Java运行时将该匿名内部类当做它所实现的接口或是所继承的父类看待。
 */

/*
对象表达式
通过对象表达式实现一个匿名内部类的对象用于方法的参数中：

window.addMouseListener(
    object : MouseAdapter() { //这个mouseadapter  作为一整块
        override fun mouseClicked(e: MouseEvent) {
            // ...
        }

        override fun mouseEntered(e: MouseEvent) {
            // ...
        }
    }
)
*/

/**
对象可以继承于某个基类，或者实现其他接口:
 */
open class SonOne(x: Int) {
    open val y: Int = x
    open var z: Int = x
    open fun printSon() {
        println("我是 SonOne 的方法")
    }
}

interface FatherOne {
    fun printFatherOne()
}

abstract class FatherTwo {
    abstract val age: Int
    abstract fun printFatherTwo()
}

var sonOne = object : FatherOne {
    override fun printFatherOne() {
        println("sonOne对象表达式")
    }
}

//这个证实用 , 隔开
//对象表达中 重写父类的方法才可以调用的
val ab: SonOne = object : SonOne(1), FatherOne {
    override val y = 15
    override fun printFatherOne() {
        println("ab 对象表达式")
    }

    override fun printSon() {
        println("我是 ab printSon 的方法")
    }
}

val c: SonOne = object : SonOne(3) {
    override var z = 13
}

class TestOne11 {
    @Test
    fun test1() {
        println(ab.y)
        ab.printSon()

        println(c.z)

        println(sonOne.printFatherOne())

    }

}

/**
如果超类型有一个构造函数，则必须传递参数给它。多个超类型和接口可以用逗号分隔。

通过 对象表达式可以越过类的定义  直接得到一个对象：

 */

class Test110 {
    @Test
    fun test3() {
        //这个地方很容易看出来 , 这个就是一个对象 ,object  其实就是一个类
        val site = object {
            var name: String = "菜鸟教程"
            var url: String = "www.runoob.com"
        }
        println(site.name)
        println(site.url)
    }
}


/**
请注意，匿名对象  可以用作只在 本地 和 私有作用域  中声明的类型。
如果你使用 匿名对象 作为 公有函数 的 返回类型 或者用作公有属性的类型，那么该 函数或属性的实际类型 会是匿名对象声明的 超类型，
如果你没有声明任何超类型，就会是 Any。在匿名对象 中添加的成员将无法访问。
 */
class C8 {
    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }

    // 公有函数，所以其返回类型是 Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x        // 没问题
        //val x2 = publicFoo().x  // 错误：未能解析的引用“x”
    }
}


//    匿名对象只能在局部变量范围内或是被private修饰的成员变量范围内才能被识别出真正的类型。如果将匿名对象当做一个public方法返回的类型或是public属性的类型，
//    那么该方法或是属性的真正类型，就是该匿名对象所声明的父类型，如果没有声明任何父类型，那么其类型就是Any,在这种情况下匿名对象中声明的任何成员都是无法访问的。
//    如下例子：
class MyClass {
    var i: Int = 0
    private var myobject = object {  //此处的private是非常重要的，不能去掉否则访问不到output方法
        fun output() = println("output invoked")
        fun addNumber() = i++ //Kotlin对象表达式中的代码可以访问外层的变量，Java外层必须声明为final
    }

    fun myTest() {
        println(myobject.javaClass)
        myobject.output()
    }
}

@Test
fun main() {
    val myClass = MyClass()
    myClass.myTest()
}

class Test111 {
    @Test
    fun test4() {
        var c = C8()
        println(c.publicFoo())
    }
}

/**
在对象表达中可以方便的访问到作用域中的其他变量:
 */

/*
fun countClicks(window: JComponent) {
    var clickCount = 0
    var enterCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++   //这里就是作用域其他的变量
        }

        override fun mouseEntered(e: MouseEvent) {
            enterCount++   //这里就是作用域其他的变量
        }
    })
}
*/




/**
对象声明

Kotlin 使用   object 关键字来声明一个对象。

Kotlin 中我们可以方便的通过对象声明来获得一个单例。
 */

//菜鸟教程 例子太垃圾了
object DataTest {
    fun provider(a: Int): Int {
        return a
    }
}

class Test113 {
    @Test
    fun test5() {

        val boy = object {
            var x: Int = 11
            var y: Int = 12
        }
        println(boy.x + boy.y)

        var c = DataTest.provider(9)
        println(c)
    }
}
/*
引用该对象，我们直接使用其名称即可：

DataTest.provider(9)
*/

/**
当然你也可以定义一个变量来获取获取这个对象，当时当你定义两个不同的变量来获取这个对象时，你会发现你并不能得到两个不同的变量。
也就是说通过这种方式，我们获得一个单例。

实例
以下实例中，两个对象都输出了同一个 url 地址：
 */
object Site1 {
    var url: String = ""
    val name: String = "菜鸟教程"
}

@Test
fun test6() {
    var s1 = Site1
    var s2 = Site1
    Site1.url = "www.runoob.com"
    println(Site1.url)
    println(Site1.url)
    println(s1 == s2)
}

/*
输出结果为:

www.runoob.com
www.runoob.com

*/


/** 对象可以有超类型：*/

interface MouseAdapter {
    fun mouseClicked() {
    }

    fun mouseEntered() {

    }
}

//对象的声明 , 他的超类型就是 MouseAdapter
object DefaultListener : MouseAdapter {
    override fun mouseClicked() {
        // ……
    }

    override fun mouseEntered() {
        // ……
    }
}


/**
与对象表达式不同，当对象声明在另一个类的内部时，
这个对象并不能通过外部类的实例访问到该对象，

而只能通过类名来访问，

同样该对象也不能直接访问到外部类的方法和变量。
 */

class Site3 {
    var name = "菜鸟教程"

    object DeskTop {
        var url = "www.runoob.com"
        fun showName() {
//            print { "desk legs $name" } // 错误，不能访问到外部类的方法和变量
        }
    }
}

@Test
fun test7() {
    var site = Site3()
    // site.DeskTop.url // 错误，不能通过外部类的实例访问到该对象 , //要注意这个属于上面的那个对象
    Site3.DeskTop.url // 正确  //这个是直接的对象来调用 可以 , 上面的不能调用才注销的
    site.name

}

/**
 * 总结
 *
 * 对象表达式 , 更像一个等式 , 而对象声明就是一个单纯的对象
 */


/**
伴生对象

类内部的对象声明可以用

    companion

关键字标记，这样它就与外部类关联在一起，
我们就可以直接通过外部类访问到对象的内部元素。
 */

class MyClass1 {
    companion object Factory {
        fun create(): MyClass1 = MyClass1()
        fun update() {
            println("update 方法")
        }
    }
}

@Test
fun test8() {
    val instance = MyClass1.create()   // 访问到对象的内部元素
    println(MyClass1.update())
}


/**我们可以省略掉该对象的对象名，然后使用 Companion 替代需要声明的对象名：*/

class MyClass2 {
    companion object {
    }
}

val x = MyClass2

/**注意：

一个类里面只能声明一个内部关联对象，即关键字 companion 只能使用一次。

请伴生对象的成员看起来像其他语言的静态成员，但在运行时他们仍然是真实对象的实例成员。

例如还可以实现接口：
 */

interface Factory<T> {
    fun create(): T
}


class MyClass3 {
    //在一个类中 companion 只能用一次
    companion object : Factory<MyClass3> {
        override fun create(): MyClass3 = MyClass3()
    }
}

/**
 * 这个是需要总结的
 *

 *对象表达式和对象声明之间的语义差异

 *对象表达式和对象声明之间有一个重要的语义差别：

对象表达式是在使用他们的地方立即执行的

对象声明是在第一次被访问到时延迟初始化的

伴生对象的初始化是在相应的类被加载（解析）时，与 Java 静态初始化器的语义相匹配
 */

