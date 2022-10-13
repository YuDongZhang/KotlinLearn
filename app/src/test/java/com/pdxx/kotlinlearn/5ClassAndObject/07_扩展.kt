package com.pdxx.kotlinlearn.`5ClassAndObject`

import org.junit.Test

class `07_扩展` {

    /**
    写测试类的时候 , 不能有main 的方法
     */

    /**
    Kotlin 扩展
    Kotlin 可以对一个类的属性和方法进行扩展，且不需要继承或使用 Decorator 模式。

    扩展是一种静态行为，对被扩展的类代码本身不会造成任何影响。

    扩展函数
    扩展函数可以在已有类中添加新的方法，不会对原类做修改，扩展函数定义形式：
    下面这个就是写法 :
     */

//fun receiverType.functionName(params){
//    body
//}

    /**
    receiverType：表示函数的接收者，也就是函数扩展的对象
    functionName：扩展函数的名称
    params：扩展函数的参数，可以为NULL

    以下实例扩展 User 类 ：
     */
    class User(var name: String)

    /**扩展函数**/
    fun User.Print() {
        print("用户名 $name")
    }


    @Test
    fun test1() {
        var user = User("Runoob")
        user.Print()//这里可以直接调用扩展函数 , 现实中调用了 已经定义好的系统的函数
    }


    /**
    实例执行输出结果为：

    用户名 Runoob
    下面代码为 MutableList 添加一个swap 函数：

     */

    @Test
    fun test2() {
        // 扩展函数 swap,调换不同位置的值
        fun MutableList<Int>.swap(index1: Int, index2: Int) {
            val tmp = this[index1]     //  this 对应该列表
            this[index1] = this[index2]
            this[index2] = tmp
        }


        val l = mutableListOf(1, 2, 3)
        // 位置 0 和 2 的值做了互换
        l.swap(0, 2) // 'swap()' 函数内的 'this' 将指向 'l' 的值

        println(l.toString())
    }


    /**
    实例执行输出结果为：

    [3, 2, 1]
    this关键字指代接收者对象(receiver object)(也就是调用扩展函数时, 在点号之前指定的对象实例)。
     */

    /**
     * 泛型的扩展 , 看源码 ,可以突破上一个的类型的限制
     */
    @Test
    fun test21() {

        fun <T> MutableList<T>.swap2(index1: Int, index2: Int) {
            val tmp = this[index1]     //  this 对应该列表
            this[index1] = this[index2]
            this[index2] = tmp
        }

        val listString = mutableListOf("A", "B", "C")
        listString.swap2(0, 2)
        println("$listString")

    }


    /**
    扩展函数是静态解析的

    扩展函数是静态解析的，并不是接收者类型的虚拟成员，在调用扩展函数时，具体被调用的的是哪一个函数，
    由调用函数的的对象表达式来决定的，而不是动态的类型决定的:
     */

    open class C2

    class D2 : C2()

    fun C2.foo() = "c2"   // c2扩展函数 foo

    fun D2.foo() = "d2"   // 扩展函数 foo

    fun printFoo(c2: C2) {
        println(c2.foo())  // 类型是 C 类
    }


    @Test
    fun Test3() {
        printFoo(D2())  //传入的D2 没有打印d2 , 具体的操作还是 调用的 上面的 , println 中的 c2 的 foo , 即 : println(c2.foo())  // 类型是 C 类
    }


    /**
    实例执行输出结果为：

    c
     */


    /**
    若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。

     */

    class C3 {
        fun foo() {
            println("成员函数")
        }
    }

    fun C3.foo() {
        println("扩展函数")
    }


    @Test
    fun test4() {
        var c3 = C3()
        c3.foo()
    }


    /**
    实例执行输出结果为：

    成员函数
    扩展一个空对象
    在扩展函数内， 可以通过 this 来判断接收者是否为 NULL,这样，即使接收者为 NULL,也可以调用扩展函数。例如:
     */
    fun Any?.toString(): String {
        if (this == null) return "null"
        // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
        // 解析为 Any 类的成员函数
        return toString()
    }


    @Test
    fun test5() {
        var t = null
        println(t.toString())
    }


    /**
    实例执行输出结果为：null
     */


    /**
    扩展属性
    除了函数，Kotlin 也支持属性对属性进行扩展:
     */

    val <T> List<T>.lastIndex: Int
        get() = size - 1

    /**
    扩展属性允许定义在类或者kotlin文件中，不允许定义在函数中。初始化属性因为属性没有后端字段（backing field），
    所以不允许被初始化，只能由显式提供的 getter/setter 定义。

    val Foo.bar = 1 // 错误：扩展属性不能有初始化器
    扩展属性只能被声明为 val。
     */

    //为String添加一个lastChar属性，用于获取字符串的最后一个字符
    val String.lastChar: Char get() = this.get(this.length - 1)
  //为List添加一个last属性用于获取列表的最后一个元素，this可以省略
     val <T>List<T>.last: T get() = get(size - 1)

    @Test
    fun Test22() {

        println("123456".lastChar)

        val listString = listOf("Android Q", "Android N", "Android M")
        println("listString.last${listString.last}")

    }


    /**
    伴生对象的扩展
    如果一个类定义有一个伴生对象 ，你也可以为伴生对象定义扩展函数和属性。

    伴生对象通过"类名."形式调用伴生对象，伴生对象声明的扩展函数，通过用类名限定符来调用：
     */
    class MyClass {
        companion object {}  // 将被称为 "Companion"
    }

    fun MyClass.Companion.foo() {
        println("伴随对象的扩展函数")
    }

    val MyClass.Companion.no: Int
        get() = 10


    @Test
    fun test6() {
        println("no:${MyClass.no}")
        MyClass.foo()
    }

    /**
    实例执行输出结果为：

    no:10
    伴随对象的扩展函数
    扩展的作用域
    通常扩展函数或属性定义在顶级包下:

    package foo.bar

    fun Baz.goo() { …… }
    要使用所定义包之外的一个扩展, 通过import导入扩展的函数名进行使用:

    package com.example.usage

    import foo.bar.goo // 导入所有名为 goo 的扩展
    // 或者
    import foo.bar.*   // 从 foo.bar 导入一切

    fun usage(baz: Baz) {
    baz.goo()
    }
     */

    /**
    扩展声明为成员
    在一个类内部你可以为另一个类声明扩展。

    在这个扩展中，有个多个隐含的接受者，其中扩展方法定义所在类的实例称为分发接受者，而扩展方法的目标类型的实例称为扩展接受者。
     */
    class D4 {
        fun bar() {
            println("D bar")
        }
    }

    class C4 {
        fun baz() {
            println("C baz")
        }

        fun D4.foo() {
            bar()   // 调用 D.bar
            baz()   // 调用 C.baz
        }

        fun caller(d4: D4) {
            d4.foo()   // 调用扩展函数
        }
    }


    @Test
    fun test7() {
        val c4: C4 = C4()
        val d4: D4 = D4()
        c4.caller(d4)
    }


    /**
    实例执行输出结果为：

    D bar
    C baz
    在 C 类内，创建了 D 类的扩展。此时，C 被成为分发接受者，而 D 为扩展接受者。从上例中，可以清楚的看到，在扩展函数中，可以调用派发接收者的成员函数。
     */

    /**
    假如在调用某一个函数，而该函数在 分发接受者 和 扩展接受者 均存在，则以扩展接收者优先，要引用分发接收者的成员你可以使用限定的 this 语法。
     */

    class D5 {
        fun bar() {
            println("D bar")
        }
    }

    class C5 {
        fun bar() {
            println("C bar")
        }  // 与 D 类 的 bar 同名

        fun D5.foo() {
            bar()         // 调用 D.bar()，扩展接收者优先
            this@C5.bar()  // 调用 C.bar()
        }

        fun caller(d5: D5) {
            d5.foo()   // 调用扩展函数
        }
    }


    @Test
    fun test8() {
        val c5: C5 = C5()
        val d5: D5 = D5()
        c5.caller(d5)
        println("----------------")

    }


/*
实例执行输出结果为：

D bar
C bar
*/
    /**
    以成员的形式定义的扩展函数, 可以声明为 open , 而且可以在子类中覆盖. 也就是说, 在这类扩展函数的派 发过程中,
    针对分发接受者是虚拟的(virtual), 但针对扩展接受者仍然是静态的。
     */

    open class D6 {
    }

    class D16 : D6() {
    }

    open class C6 {
        open fun D6.foo() {
            println("D6.foo in C6")
        }

        open fun D16.foo() {
            println("D1.foo in C")
        }

        fun caller(d6: D6) {
            d6.foo()   // 调用扩展函数
        }
    }

    class C16 : C6() {
        override fun D6.foo() {
            println("D.foo in C1")
        }

        override fun D16.foo() {
            println("D16.foo in C16")
        }
    }


    @Test
    fun test9() {
        C6().caller(D6())   // 输出 "D.foo in C"
        C16().caller(D6())  // 输出 "D.foo in C1" —— 分发接收者虚拟解析
        C6().caller(D16())  // 输出 "D.foo in C" —— 扩展接收者静态解析

    }

/*
实例执行输出结果为：

D.foo in C
D.foo in C1
D.foo in C

*/

}