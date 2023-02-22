package com.pdxx.kotlinlearn

import org.junit.Test

/**
Kotlin 中所有类都继承该 Any 类，它是所有类的超类，对于没有超类型声明的类是默认超类：
class Example // 从 Any 隐式继承

Any 默认提供了三个函数：
equals()
hashCode()
toString()

注意：Any 不是 java.lang.Object。

如果一个类要被继承，可以使用 open 关键字进行修饰。

open class Base(p: Int)           // 定义基类
class Derived(p: Int) : Base(p)

 */


    /**
    格式

    open class 类名{
    ...
    open var/val 属性名 = 属性值
    ...
    open fun 函数名()
    ...
    }
     */

    open class Demo {

        open var num = 3

        open fun foo() = "foo"

        fun bar() = "bar"

    }


    class DemoTest : Demo() {
        //继承父类的方法用 override
        override fun foo(): String {
            return super.foo()
        }

        //没有 open 修饰的不能继承
//         override fun bar(): String {
//            return super.foo()
//        }
    }

    class Test1{
        @Test
        fun test1() {
            println(DemoTest().num)
            DemoTest().foo()
            DemoTest().bar()
        }
    }


    /*
    输出结果为：

    3
    foo
    bar
    分析：从上面的代码可以看出，DemoTest类只是继承了Demo类，
    并没有实现任何的代码结构。一样可以使用Demo类中的属性与函数。这就是继承的好处。
     */

    /** Kotlin类，可以有一个主构造函数，或者多个辅助函数。或者没有构造函数的情况 */

    /**这里当实现类无主构造函数，和存在主构造函数的情况。*/

    /**无主构造函数
     *
     * 当实现类无主构造函数时，则每个辅助构造函数必须使用super关键字初始化基类型，或者委托给另一个构造函数。
     * 请注意，在这种情况下，不同的辅助构造函数可以调用基类型的不同构造函数
     *
     * */

    class FatherOne constructor(index: Int) {

    }

    /* class MyView() : View() {

         constructor(context: Context) : super(context)

         constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

         constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
                 : super(context, attrs, defStyleAttr)

     }*/

    /** 可以看出，当实现类无主构造函数时，分别使用了super()去实现了基类的三个构造函数。*/

    /**
    存在主构造函数
    当存在主构造函数时，主构造函数一般实现基类型中参数最多的构造函数，参数少的构造函数则用this关键字引用即可了。
    这点在Kotlin——中级篇（一）：类（class）详解 这篇文章是讲解到的。
     */

    /*
    class MyView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
    : View(context, attrs, defStyleAttr) {

        constructor(context: Context?) : this(context,null,0)

        constructor(context: Context?,attrs: AttributeSet?) : this(context,attrs,0)
    }
     */


    /** 2.3、函数的重载与重写 */

    //在Kotlin中关于函数的重载与重写,和Java中是几乎是一样的，但是这里还是举例来说明一下。

    /**2.3.1、重写函数中的两点特殊用法

    不管是Java还是Kotlin，重写基类型里面的方法，则称为重写，或者是覆盖基类型方法。不过这里介绍两点Kotlin一点特殊的地方

    当基类中的函数，
    没有用open修饰符修饰的时候，
    实现类中出现的函数的函数名不能与基类中没有用open修饰符修饰的函数的函数名相同，不管实现类中的该函数有无override修饰符修饰。
    读着有点绕，直接看例子你就明白了。
     */

    open class Demo2 {
        fun test() {}   // 注意，这个函数没有用open修饰符修饰
    }

    class DemoTest2 : Demo2() {

        // 这里声明一个和基类型无open修饰符修饰的函数，且函数名一致的函数
        // fun test(){}   编辑器直接报红，根本无法运行程序
        // override fun test(){}   同样报红
    }

    /**
     * 当一个类不是用open修饰符修饰时，这个类默认是final的。即：
     */

    /* class A{}

     等价于

     final class A{}   // 注意，则的`final`修饰符在编辑器中是灰色的，因为Kotlin中默认的类默认是final的*/


//    那么当一个基类去继承另外一个基类时，第二个基类不想去覆盖掉第一个基类的方法时，第二个基类的该方法使用final修饰符修饰。

    /**
    构造函数

    子类有主构造函数
    如果子类有主构造函数， 则基类必须在主构造函数中立即初始化。
     */

    open class Person(var name: String, var age: Int) {// 基类

    }

    class Student(name: String, age: Int, var no: String, var score: Int) : Person(name, age) {

    }


    /**
    子类没有主构造函数

    如果子类没有主构造函数，则必须在每一个二级构造函数中用 super 关键字初始化基类，或者在代理另一个构造函数。
    初始化基类时，可以调用基类的不同构造方法。
     */
    open class Person12 {

    }

    class Student1 : Person12() {

//    constructor(ctx: Context) : super(ctx) {
//    }
//
//    constructor(ctx: Context, attrs: AttributeSet) : super(ctx,attrs) {
//    }
    }


    @Test
    fun test() {
        val s = Student("Runoob", 18, "S12346", 89)
        println("学生名： ${s.name}")
        println("年龄： ${s.age}")
        println("学生号： ${s.no}")
        println("成绩： ${s.score}")
    }


    /**
     * 例子
     */

    /**用户基类**/
    open class Person13(name: String) {
        /**次级构造函数**/
        constructor(name: String, age: Int) : this(name) {
            //初始化
            println("-------基类次级构造函数---------")
        }
    }

    /**子类继承 Person 类**/
    class Student2 : Person13 {

        /**次级构造函数**/
        constructor(name: String, age: Int, no: String, score: Int) : super(name, age) {
            println("-------继承类次级构造函数---------")
            println("学生名： ${name}")
            println("年龄： ${age}")
            println("学生号： ${no}")
            println("成绩： ${score}")
        }
    }

    class Test2 {
        @Test
        fun test() {
            var s = Student2("Runoob", 18, "S12345", 89)
        }
    }


    /**
    2.3.2、方法重载

    重写
    在基类中，使用fun声明函数时，此函数默认为 final 修饰，不能被子类重写。
    如果允许子类重写该函数，那么就要手动添加 open 修饰它, 子类重写方法使用 override 关键词：
     */

    /**用户基类**/
    open class Person14 {
        open fun study() {       // 允许子类重写
            println("我毕业了")
        }
    }

    /**子类继承 Person 类**/
    class Student4 : Person14() {
        override fun study() {    // 重写方法
            println("我在读大学")
        }
    }

    class Test4{
        @Test
        fun test4() {
            val s = Student4()
            s.study();
        }

    }


    /**
    如果有多个相同的方法（继承或者实现自其他类，如A、B类），则必须要重写该方法，使用super范型去选择性地调用父类的实现。
     */
    open class A {
        open fun f() {
            print("A.f()")
        }

        fun a() {
            print("A.a()")
        }
    }

    interface B {
        fun f() {
            print("B.f()")
        } //接口的成员变量默认是 open 的

        fun b() {
            print("B.b()")
        }
    }

    class C() : A(), B {
        //C后面可以加() 也可以不加
        override fun f() {
            super<A>.f()//调用 A.f()
            super<B>.f()//调用 B.f()
        }
    }

    /**
     * C 继承自 a() 或 b(), C 不仅可以从 A 或则 B 中继承函数，而且 C 可以继承 A()、B() 中共有的函数。
     * 此时该函数在中只有一个实现，为了消除歧义，该函数必须调用A()和B()中该函数的实现，并提供自己的实现。

    输出结果为:
     */

    class Test5 {
        @Test
        fun test5() {
            val c = C()
            c.f()
            c.a()
        }
    }

    /**
     * 属性重写
    属性重写使用 override 关键字，属性必须具有兼容类型，每一个声明的属性都可以通过初始化程序或者getter方法被重写：

    你可以用一个var属性重写一个val属性，但是反过来不行。因为val属性本身定义了getter方法，重写为var属性会在衍生类中额外声明一个setter方法

    你可以在主构造函数中使用 override 关键字作为属性声明的一部分:
     */

    /** 例子 1 */
    interface Foo {
        val count: Int
    }

    class Bar1(override val count: Int) : Foo //构造函数中重写

    class Bar2 : Foo {
        override var count: Int = 0 //这里就用到了 override
    }

    /*
    2.4.2、Getter()函数慎用super关键字

在这里值得注意的是，在实际的项目中在重写属性的时候不用get() = super.xxx,因为这样的话，不管你是否重新为该属性赋了新值，还是支持setter(),在使用的时候都调用的是基类中的属性值。

例： 继上面中的例子

class DemoTest : Demo(){

    /*
     * 这里介绍重写属性是，getter()函数中使用`super`关键字的情况
     */

    override var valStr: String = "abc"、
        get() = super.valStr
        set(value){field = value}
}

fun main(arge: Array<String>>){
    println(DemoTest().valStr)

    val demo = DemoTest()
    demo.valStr = "1212121212"
    println(demo.valStr)
}
输出结果为：

我是用val修饰的属性
我是用val修饰的属性
切记：重写属性的时候慎用super关键字。不然就是上面例子的效果
     */

    /**
     * 对于Kotlin中继承类这一个知识点，在项目中用到的地方是很常见的。当你认真的学习完上面的内容，我相信你可以能很轻易的用于项目中，不过对一个类来说，继承的代价较高，
     * 当实现一个功能不必用到太多的集成属性的时候，可以用
     *
     * 对象表达式
     *
     * 这一个高级功能去替代掉继承。
     *
     * 如果你有过其他面向对象语言的编程经验的话，你只要掌握其

     * 关键字、属性/函数重写、以及覆盖规则

     * 这三个知识点就可以了。
     */

