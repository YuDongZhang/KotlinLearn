package com.pdxx.kotlinlearn

import org.junit.Test

/**
 * Kotlin 类和对象
 */
class `04_Person` {

    @Test
    fun test() {
        var person: Person = Person()
        person.lastName = "wang"
        println("lastName:${person.lastName}")

        person.no = 9
        println("no:${person.no}")

        person.no = 20
        println("no:${person.no}")
    }


    class Person {
        var lastName: String = "zhang"
            get() = field.toUpperCase()   // 将变量赋值后转换为大写
            set

        var no: Int = 100
            get() = field                /**后端变量 ,就是传入的值是可以改变的 , 判断的*/
            set(value) {
                if (value < 10) {       // 如果传入的值小于 10 返回该值
                    field = value
                } else {
                    field = -1         // 如果传入的值大于等于 10 返回 -1
                }
            }

        // Kotlin 中类不能有字段。提供了 Backing Fields(后端变量) 机制,备用字段使用field关键字声明,field 关键词只能用于属性的访问器，如以上实例：

        var heiht: Float = 145.4f
            private set
    }

    @Test
    fun test1_1() {


    }




//    非空属性必须在定义的时候初始化,kotlin提供了一种可以延迟初始化的方案,使用 lateinit 关键字描述属性：
//public class MyTest {
//    lateinit var subject: TestSubject
//
//    @SetUp
//    fun setup() {
//        subject = TestSubject()
//    }
//
//    @Test
//    fun test() {
//        subject.method()  // dereference directly
//    }
//}

    /**
     * 主构造器
    主构造器中不能包含任何代码，初始化代码可以放在初始化代码段中，初始化代码段使用 init 关键字作为前缀。
     */
    class Person2 constructor(firstName: String) {
        init {
            println("FirstName is $firstName")
        }
    }

//注意：主构造器的参数可以在初始化代码段中使用，也可以在类主体n定义的属性初始化代码中使用。
// 一种简洁语法，可以通过主构造器来定义属性并初始化属性值（可以是var或val）：

    class People(val firstName: String, val lastName: String) {
        //...
    }

    //如果构造器有注解，或者有可见度修饰符，这时constructor关键字是必须的，注解和修饰符要放在它之前。
    class Person3 constructor(name: String) {  // 类名为 Runoob
        // 大括号内是类体构成
        var url: String = "http://www.runoob.com"
        var country: String = "CN"
        var siteName = name

        init {
            println("初始化网站名: ${name}")
        }

        fun printTest() {
            println("我是类的函数")
        }

    }


    @Test
    fun test2() {
        val runoob = Person3("菜鸟教程")
        println(runoob.siteName)
        println(runoob.url)
        println(runoob.country)
        runoob.printTest()
    }


    /**
    次构造函数
    类也可以有二级构造函数，需要加前缀 constructor:
     */
    class Person4 {
        constructor(parent: Person4) {
//        parent.children.add(this)
        }
    }

    /**
    如果类有主构造函数，每个次构造函数都要，或直接或间接通过另一个次构造函数代理主构造函数。在同一个类中代理另一个构造函数使用 this 关键字：
     */
    class Person5(val name: String) {
        constructor (name: String, age: Int) : this(name) {
// 初始化...
        }
    }

    /**
    如果一个非抽象类没有声明构造函数(主构造函数或次构造函数)，它会产生一个没有参数的构造函数。构造函数是 public 。
    如果你不想你的类有公共的构造函数，你就得声明一个空的主构造函数：
     */
    class DontCreateMe private constructor() {
    }

    /**
     * 注意：在 JVM 虚拟机中，如果主构造函数的所有参数都有默认值，编译器会生成一个附加的无参的构造函数，
     * 这个构造函数会直接使用默认值。这使得 Kotlin 可以更简单的使用像 Jackson 或者 JPA 这样使用无参构造函数来创建类实例的库。
     * */

    class Customer(val customerName: String = "")

    /**
     * 测试
     */
    class Person6 constructor(name: String) {  // 类名为 Runoob
        // 大括号内是类体构成
        var url: String = "http://www.runoob.com"
        var country: String = "CN"
        var siteName = name

        init {
            println("初始化网站名: ${name}")
        }

        // 次构造函数
        constructor (name: String, alexa: Int) : this(name) {
            println("Alexa 排名 $alexa")
        }

        fun printTest() {
            println("我是类的函数")
        }
    }


    @Test
    fun test3() {
        val runoob = Person6("菜鸟教程", 10000)
        println(runoob.siteName)
        println(runoob.url)
        println(runoob.country)
        runoob.printTest()
    }


    /**
    抽象类
    抽象是面向对象编程的特征之一，类本身，或类中的部分成员，都可以声明为abstract的。抽象成员在类中不存在具体的实现。
    注意：无需对抽象类或抽象成员标注open注解。
     */
    open class Base {
        open fun f() {}
    }

    abstract class Derived : Base() {
        override abstract fun f()
    }

    /**
    嵌套类
    我们可以把类嵌套在其他类中，看以下实例：
     */

    class Outer {                  // 外部类
        private val bar: Int = 1

        class Nested {             // 嵌套类
            fun foo() = 2
        }
    }


    @Test
    fun test4() {
        val demo = Outer.Nested().foo() // 调用格式：外部类.嵌套类.嵌套类方法/属性
        println(demo)    // == 2
    }


    /**
    内部类
    内部类使用 inner 关键字来表示。

    内部类会带有一个对外部类的对象的引用，所以内部类可以访问外部类成员属性和成员函数。
     */

    class Outer2 {
        private val bar: Int = 1
        var v = "成员属性"

        /**嵌套内部类**/
        inner class Inner {
            fun foo() = bar  // 访问外部类成员
            fun innerTest() {
                var o = this@Outer2 //获取外部类的成员变量
                println("内部类可以引用外部类的成员，例如：" + o.v)
            }
        }
    }

    /**为了消除歧义，要访问来自外部作用域的 this，我们使用this@label，其中 @label 是一个 代指 this 来源的标签。*/

    @Test
    fun test5() {
        val demo = Outer2().Inner().foo()
        println(demo) //   1
        val demo2 = Outer2().Inner().innerTest()
        println(demo2)   // 内部类可以引用外部类的成员，例如：成员属性
    }


    /**
    匿名内部类
    使用对象表达式来创建匿名内部类：
     */
    class Test6 {
        var v = "成员属性"

        fun setInterFace(test: TestInterFace) {
            test.test()
        }
    }

    /**
     * 定义接口
     */
    interface TestInterFace {
        fun test()
    }


    @Test
    fun test7() {
        var test6 = Test6();
        /**
         * 采用对象表达式来创建接口对象，即匿名内部类的实例。
         */
        test6.setInterFace(object : TestInterFace {
            override fun test() {
                TODO("Not yet implemented")
                println("对象表达式创建匿名内部类的实例")
            }
        })
    }


    /**
    类的修饰符
    类的修饰符包括 classModifier 和_accessModifier_:

    classModifier: 类属性修饰符，标示类本身特性。

    abstract    // 抽象类
    final       // 类不可继承，默认属性
    enum        // 枚举类
    open        // 类可继承，类默认是final的
    annotation  // 注解类
    accessModifier: 访问权限修饰符

    private    // 仅在同一个文件中可见
    protected  // 同一个文件中或子类可见
    public     // 所有调用的地方都可见
    internal   // 同一个模块中可见

    实例
    // 文件名：example.kt
    package foo

    private fun foo() {} // 在 example.kt 内可见

    public var bar: Int = 5 // 该属性随处可见

    internal val baz = 6    // 相同模块内可见
     */
}