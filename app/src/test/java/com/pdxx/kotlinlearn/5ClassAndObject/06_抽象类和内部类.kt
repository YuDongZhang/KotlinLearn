package com.pdxx.kotlinlearn.`5ClassAndObject`

import org.junit.Test

class `06_抽象类和内部类` {
    /**
    抽象类

    关键字为：abstract
     */
    abstract class Lanauage {
        val TAG = this.javaClass.simpleName  // 自身的属性

        // 自身的函数
        fun test(): Unit {
            // exp
        }

        abstract var name: String           // 抽象属性
        abstract fun init()                  // 抽象方法
    }

    /**
     * 抽象类Lanauage 的 实现类TestAbstarctA
     */
    class TestAbstarctA : Lanauage() {

        override var name: String
            get() = "Kotlin"
            set(value) {}

        override fun init() {
            println("我是$name")
        }
    }

    /**
     * 抽象类Lanauage的实现类TestAbstarctB
     */
    class TestAbstarctB : Lanauage() {
        override var name: String
            get() = "Java"
            set(value) {}

        override fun init() {
            println("我是$name")
        }
    }

    @Test
    fun test1() {
        // val lanauage = Lanauage() 是错误的，因为抽象类不能直接被实例化

        val mTestAbstarctA = TestAbstarctA()
        val mTestAbstarctB = TestAbstarctB()

        println(mTestAbstarctA.name)
        mTestAbstarctA.init()

        println(mTestAbstarctB.name)
        mTestAbstarctB.init()
    }

    /*
    输出结果为：

    Kotlin
    我是Kotlin
    Java
    我是Java
     */

    /**
    抽象类本身具有普通类特性，以及组成部分。不过值得注意的是，抽象类不能直接被实例化
    其抽象了类的子类必须全部重写带abstract修饰的属性和方法。
    抽象成员只有定义，没有实现。都有abstract修饰符修饰。
    抽象类是为其子类定义了一个模板。不同是类实现不同的功能
     */

    /**
    抽象类的规则
    在Kotlin中的抽象类在顶层定义的时候只能使用public可见性修饰符修饰。
    抽象类中可以定义内部抽象类。
    只能继承一个抽象类。
    若要实现抽象类的实例化，需要依靠子类采用向上转型的方式处理。
    抽象类可以继承自一个继承类，即抽象类可以作为子类。不过，抽象类建议不用open修饰符修饰，因为可以覆写抽象类的父类的函数
     */

    /*
    open class Base{
            open fun init(){}
        }

        abstract class Lanauage : Base(){
            val TAG = this.javaClass.simpleName  // 自身的属性

            // 自身的函数
            fun test() : Unit{
                // exp
            }
            abstract var name : String           // 抽象属性
            abstract override fun init()         // 覆写父类的方法

            abstract class Name(){}              // 嵌套抽象类，可查看第二节中的嵌套类使用
        }

        fun main(args: Array<String>) {
            // 若要实现抽象类的实例化，需要依靠子类采用向上转型的方式处理。
            val mLanauage : Lanauage = TestAbstarctB()
        }
     */


    /**
    在Java的设计模式中，有一种设计模式叫模板设计模式，其定义为：

    定义一个操作中算法的骨架，而将一些步骤延迟到子类中，模板方法使得子类可以不改变算法的结构即可重定义该算法的某些特定步骤。
    通俗点的理解就是 ：完成一件事情，有固定的数个步骤，但是每个步骤根据对象的不同，而实现细节不同；
    就可以在父类中定义一个完成该事情的总方法，按照完成事件需要的步骤去调用其每个步骤的实现方法。每个步骤的具体实现，由子类完成。
    Kotlin和Java是互通的，说明Kotlin也是支持这种设计模式的。
     */


    /**嵌套类*/

    class Other {           // 外部类
        val numOuther = 1

        class Nested {      // 嵌套类
            fun init() {
                println("执行了init方法")
            }
        }
    }

    @Test
    fun test2() {
        Other.Nested().init()  // 调用格式为：外部类.嵌套类().嵌套类方法/属性
    }
    //嵌套类不能使用外部类的属性和成员


    /**内部类*/

//    在上面的例子中讲解了嵌套类的使用，而内部类和嵌套类还是有一定的区别的，而且内部类是有特定的关键字去声明的。
    /**
    声明一个内部类使用inner关键字。
    声明格式：inner class 类名(参数){}
     */


    class Other_2 {            // 外部类
        val numOther = 1

        inner class InnerClass {     // 嵌套内部类
            val name = "InnerClass"
            fun init() {
                println("我是内部类")
            }
        }
    }

    @Test
    fun test3() {
        Other_2().InnerClass().init()  // 调用格式为：外部类().内部类().内部类方法/属性
    }

    /**
    匿名内部类
    作为一名Android开发者，对匿名内部类都不陌生，因为在开发中，匿名内部类随处可见。比如说Button的OnClickListener，ListView的单击、长按事件等都用到了匿名内部类。

    一般的使用方式为定义一个接口，在接口中定义一个方法。

     重点 : 接口中的方法
     */

    class Other3 {

        lateinit private var listener: OnClickListener

        fun setOnClickListener(listener: OnClickListener) {
            this.listener = listener
        }

        fun testListener() {
            listener.onItemClick("我是匿名内部类的测试方法")
        }
    }

    interface OnClickListener {
        fun onItemClick(str: String)
    }

    @Test
    fun test4() {
        // 测试匿名内部类
        val other = Other3()
        other.setOnClickListener(object : OnClickListener {
            override fun onItemClick(str: String) {
                // todo
                println(str)
            }
        })
        other.testListener()
    }


    /**3、局部类*/

    class Other4{    // 外部类
        val numOther = 1

        fun partMethod(){
            var name : String = "partMethod"

            class Part{
                var numPart : Int = 2

                fun test(){
                    name = "test"
                    numPart = 5
                    println("我是局部类中的方法")
                }
            }

            val part = Part()
            println("name = $name \t numPart = " + part.numPart + "\t numOther = numOther")
            part.test()
            println("name = $name \t numPart = " + part.numPart + "\t numOther = numOther")
        }
    }

    @Test
    fun test5() {
        // 测试局部类
        Other4().partMethod()
    }

    /**局部类只能在定义该局部类的方法中使用。
    定义在实例方法中的局部类可以访问外部类的所有变量和方法。但不能修改
    局部类中的可以定义属性、方法。并且可以修改局部方法中的变量。*/


    /**
     * 熟悉Java的朋友都知道Java的静态类，或者说用static修饰符修饰的类。但是在Kotlin中，是不存在static关键字的。那么我们怎样去实现一个静态类呢？

    关于静态类的使用，以及静态类的语法。以及Koltin的单例模式实现。由于篇幅原因我在这里就不展示了。
    有兴趣的朋友请参见kotlin中的object更像是语法糖。这篇文章是别的大牛诠释静态类以及单例实现很好的文章。后面我会出一篇详细的文章为大家讲解。
     */
}