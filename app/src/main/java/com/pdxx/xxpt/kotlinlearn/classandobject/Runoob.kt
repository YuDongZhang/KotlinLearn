package com.pdxx.xxpt.kotlinlearn.classandobject
/*
Kotlin 类和对象
类定义
Kotlin 类可以包含：构造函数和初始化代码块、函数、属性、内部类、对象声明。

Kotlin 中使用关键字 class 声明类，后面紧跟类名：

class Runoob {  // 类名为 Runoob
    // 大括号内是类体构成
}
我们也可以定义一个空类：

class Empty
可以在类中定义成员函数：

class Runoob() {
    fun foo() { print("Foo") } // 成员函数
}
 */
class Runoob{
    fun foo(){print("foo")}//成员函数
    /*
    类的属性
    属性定义
    类的属性可以用关键字 var 声明为可变的，否则使用只读关键字 val 声明为不可变。
    */
    var name: String ="a"
    var url: String = "b"
    var city: String = "c"
    /*
    我们可以像使用普通函数那样使用构造函数创建类实例：
    val site = Runoob() // Kotlin 中没有 new 关键字
    要使用一个属性，只要用名称引用它即可
        site.name           // 使用 . 号来引用
        site.url

    Koltin 中的类可以有一个 主构造器，以及一个或多个次构造器，主构造器是类头部的一部分，位于类名称之后:

    class Person constructor(firstName: String) {}
    如果主构造器没有任何注解，也没有任何可见度修饰符，那么constructor关键字可以省略。

     class Person(firstName: String) {
     }

     getter 和 setter
属性声明的完整语法：
var <propertyName>[: <PropertyType>] [= <property_initializer>]
    [<getter>]
    [<setter>]

    getter 和 setter 都是可选

    如果属性类型可以从初始化语句或者类的成员函数中推断出来，那就可以省去类型，val不允许设置setter函数，因为它是只读的。
     */

    //var allByDefault: Int? // 错误: 需要一个初始化语句, 默认实现了 getter 和 setter 方法
    var initialized = 1    // 类型为 Int, 默认实现了 getter 和 setter
   // val simple: Int?       // 类型为 Int ，默认实现 getter ，但必须在构造函数中初始化
    val inferredType = 1   // 类型为 Int 类型,默认实现 getter

    /*
    实例
    以下实例定义了一个 Person 类，包含两个可变变量 lastName 和 no，lastName 修改了 getter 方法，no 修改了 setter 方法。
    详: 见person类
     */



}