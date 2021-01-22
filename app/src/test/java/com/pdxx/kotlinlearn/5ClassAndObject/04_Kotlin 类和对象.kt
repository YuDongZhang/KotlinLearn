package com.pdxx.kotlinlearn.`5ClassAndObject`

import org.junit.Test

class `04_Kotlin 类和对象` {

    class Runoob {  // 类名为 Runoob
        // 大括号内是类体构成
    }

    //我们也可以定义一个空类：
    class Empty


    //可以在类中定义成员函数：

    @Test
    fun foo() {
        print("Foo")
    } // 成员函数


    /**
    类的属性
    属性定义
    类的属性可以用关键字
    var 声明为可变的，否则使用只读关键字
    val 声明为不可变。*/

    class Runoob4 {
        var name: String = "测试1"
        var url: String = "测试2"
        var city: String = "测试3"
    }

    @Test
    fun TestRun4() {
        //我们可以像使用普通函数那样使用构造函数创建类实例：
        val site = Runoob4() // Kotlin 中没有 new 关键字

        //要使用一个属性，只要用名称引用它即可
        var s = site.name           // 使用 . 号来引用
        var y = site.url
        println(s+"---"+y)
    }

    /**构造器*/

    /**Koltin 中的类可以有一个 主构造器，以及一个或多个次构造器，主构造器是类头部的一部分，位于类名称之后:*/
    class Person5 constructor(firstName: String) {

    }

    /**如果主构造器没有任何注解，也没有任何可见度修饰符，那么constructor关键字可以省略。*/
    class Person(firstName: String) {

    }

    /**
    1.2、构造函数中的初始化代码块

    构造函数中不能出现其他的代码，只能包含初始化代码。包含在初始化代码块中。
    关键字：init{...}
    值得注意的是，init{...}中能使用构造函数中的参数
     */
    class Person6 constructor(var num: Int) {
        init {
            num = 5
            println("num = $num")
        }
    }

    @Test
    fun test1_2() {
        var person6 = Person6(2)
    }


    /**
    1.4、什么时候constructor可以省略

    在构造函数不具有注释符或者默认的可见性修饰符时，constructor关键字可以省略。
    默认的可见性修饰符时public。可以省略不写。请阅读我的另一篇文章Kotlin——中级篇

    （三）：可见性修饰符详解
     */

    class Test9 private constructor(num: Int){
    }

    /*class Test8 @Inject constructor(num: Int){
    }*/

    /**
    2、辅助（二级）构造函数

    Kotlin中支持二级构造函数。它们以constructor关键字作为前缀。

    class Test{

    constructor(参数列表){

    }
    }
     */

    /**
    2.2、同时存在主构造函数和二级构造函数时的情况

    如果类具有主构造函数，则每个辅助构造函数需要通过另一个辅助构造函数直接或间接地委派给主构造函数。 使用this关键字对同一类的另一个构造函数进行委派：
     */

    // 这里是为了代码清晰，故而没有隐藏constructor关键字
    class Person_7 constructor(num: Int) {

        init {
            println("num = $num")
        }

        constructor(num: Int, num2: Int) : this(num) {  //这个地方用到了 this
            println(num + num2)
        }
    }

    @Test
    fun test2_2() {
        var p1 = Person_7(1)
        println("---------------------------")
        var p2 = Person_7(2, 3)
    }

    /*
    说明：二级构造函数中的参数1(num)，是委托了主构造函数的参数num。

      可以看出，当实例化类的时候只传1个参数的时候，只会执行init代码块中的代码。当传2个参数的时候，除了执行了init代码块中代码外，还执行了二级构造函数中的代码。

      输出结果为：

    num = 1
    num = 1
       3
     */

    /**
    2.3、当类的主构造函数都存在默认值时的情况

    在JVM上，如果类主构造函数的所有参数都具有默认值，编译器将生成一个额外的无参数构造函数，它将使用默认值。
    这使得更容易使用Kotlin与诸如Jackson或JPA的库，通过无参数构造函数创建类实例。

    同理可看出，当类存在主构造函数并且有默认值时，二级构造函数也适用
     */


    /*
    fun main(args: Array<String>) {
        var test = Test()
        var test1 = Test(1,2)
        var test2 = Test(4,5,6)
       }

        class Test constructor(num1: Int = 10 , num2: Int = 20){

            init {
                println("num1 = $num1\t num2 = $num2")
            }

            constructor(num1 : Int = 1, num2 : Int = 2, num3 : Int = 3) : this(num1 , num2){
                println("num1 = $num1\t num2 = $num2 \t num3 = $num3")
            }
        }
        输出结果为：

        num1 = 10	 num2 = 20
        num1 = 1	 num2 = 2
        num1 = 4	 num2 = 5
        num1 = 4	 num2 = 5 	 num3 = 6
        说明： 当实例化无参的构造函数时。使用了参数的默认值。
     */


    //getter 和 setter
    //属性声明的完整语法：

    //var <propertyName>[: <PropertyType>] [= <property_initializer>]
    //[<getter>]
    //[<setter>]

    /**getter 和 setter 都是可选*/

//如果属性类型可以从初始化语句或者类的成员函数中推断出来，那就可以省去类型，val不允许设置setter函数，因为它是只读的。

//var allByDefault: Int? // 错误: 需要一个初始化语句, 默认实现了 getter 和 setter 方法
//var initialized = 1    // 类型为 Int, 默认实现了 getter 和 setter
//val simple: Int?       // 类型为 Int ，默认实现 getter ，但必须在构造函数中初始化
//val inferredType = 1   // 类型为 Int 类型,默认实现 getter

//例子查看 Person

}