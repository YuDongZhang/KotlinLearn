package com.pdxx.kotlinlearn

import org.junit.Test

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

class Test41 {
    @Test
    fun TestRun4() {
        //我们可以像使用普通函数那样使用构造函数创建类实例：
        val site = Runoob4() // Kotlin 中没有 new 关键字

        //要使用一个属性，只要用名称引用它即可
        var s = site.name           // 使用 . 号来引用
        var y = site.url
        val x = site.city
        println(s + "---" + y + "---" + x)
    }

}

/**构造器*/

/**Koltin 中的类可以有一个 主构造器，以及一个或多个次构造器，主构造器是类头部的一部分，位于类名称之后:*/
class Person5 constructor(firstName: String) {

}

/**如果主构造器没有任何注解，也没有任何可见度修饰符，那么constructor关键字可以省略。*/
class Person2(firstName: String) {

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

class Test42 {
    @Test
    fun test1_2() {
        var person6 = Person6(2)
    }
}


/**
1.4、什么时候constructor可以省略

在构造函数不具有注释符或者默认的可见性修饰符时，constructor关键字可以省略。
默认的可见性修饰符时public。可以省略不写。请阅读我的另一篇文章Kotlin——中级篇

（三）：可见性修饰符详解
 */

class Test9 private constructor(num: Int) {

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

如果类具有主构造函数，则每个辅助构造函数需要通过另一个辅助构造函数直接或间接地委派给主构造函数。
使用this关键字对同一类的另一个构造函数进行委派：
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

class Test43 {
    @Test
    fun test2_2() {
        var p1 = Person_7(1)
        println("---------------------------")
        var p2 = Person_7(2, 3)
    }
}

/*
说明：二级构造函数中的参数1(num)，是委托了主构造函数的参数num。

  可以看出，当实例化类的时候只传1个参数的时候，只会执行init代码块中的代码。当传2个参数的时候，
  除了执行了init代码块中代码外，还执行了二级构造函数中的代码。

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

/**
 * 静态类
 */
object NumUtil {
    fun double(num: Int): Int {
        return num * 2
    }
}

//通过这样可以实现java的工具类的方法
class Test44 {
    @Test
    fun test1() {
        NumUtil.double(2)
    }

}

/**
 * Kotlin 类和对象
 */

class Person41 {
    var lastName: String = "zhang"
        get() = field.toUpperCase()   // 将变量赋值后转换为大写
        set

    var no: Int = 100
        get() = field
        /**后端变量 ,就是传入的值是可以改变的 , 判断的 */
        set(value) {
            if (value < 10) {       // 如果传入的值小于 10 返回该值
                field = value
            } else {
                field = -1         // 如果传入的值大于等于 10 返回 -1
            }
        }

    // Kotlin 中类不能有字段。提供了 Backing Fields(后端变量) 机制,备用字段使用field关键字声明,
    // field 关键词只能用于属性的访问器，如以上实例：

    var heiht: Float = 145.4f
        private set
}

class Test45 {
    @Test
    fun test() {
        var person: Person41 = Person41()
        person.lastName = "wang"
        println("lastName set和get : ${person.lastName}")

        person.no = 9
        println("no的 set get 后端变量: ${person.no}")

        person.no = 20
        println("no的 set get 后端变量: ${person.no}")
    }
}


class TestSubject {
    fun method() {

    }
}

//    非空属性必须在定义的时候初始化,kotlin提供了一种可以 延迟初始化的方案,使用 lateinit 关键字描述属性：
class MyTest {
    lateinit var subject: TestSubject

    init {
        setup()
    }

    fun setup() {
        subject = TestSubject()
    }

    @Test
    fun test() {
        subject.method()  // dereference directly
    }
}


/**
 * 主构造器
 *
 *主构造器中不能包含任何代码，初始化代码可以放在初始化代码段中，初始化代码段使用 init 关键字作为前缀。
 */
class Person42 constructor(firstName: String) {
    init {
        println("FirstName is $firstName")
    }
}


//注意：主构造器的参数可以在初始化代码段中使用，也可以在类主体n定义的属性初始化代码中使用。
// 一种简洁语法，可以通过主构造器来定义属性并初始化属性值（可以是var或val）：

class People(val firstName: String, val lastName: String) {
    //...
}

class Test46 {
    @Test
    fun test1_1() {
        Person2("主构造函数 init 初始化")
    }
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

class Test47 {
    @Test
    fun test2() {
        val runoob = Person3("菜鸟教程")
        println(runoob.siteName)
        println(runoob.url)
        println(runoob.country)
        runoob.printTest()
    }
}


/**
次构造函数
类也可以有二级构造函数，需要加前缀 constructor:
 */
class Person44 {
    constructor(parent: String) {
        println("次级构造函数")
    }

    constructor(parent: String, son: String) {
        println("次级构造函数")
    }
}

class Test48 {
    @Test
    fun test21() {
        Person44("11");
    }
}


/**
如果类有主构造函数，每个次构造函数都要，或直接或间接通过另一个次构造函数代理主构造函数。
在同一个类中代理另一个构造函数使用 this 关键字：
 */
class Person45(val name: String) {
    constructor (name: String, age: Int) : this(name) {
        // 初始化...
        println(name)
    }
}

class Test49 {
    @Test
    fun test22() {
        Person45("11")
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
class Person46 constructor(name: String) {  // 类名为 Runoob
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

class Test400 {
    @Test
    fun test3() {
        val runoob = Person46("菜鸟教程", 10000)
        println(runoob.siteName)
        println(runoob.url)
        println(runoob.country)
        runoob.printTest()
    }

}


/**
抽象类

抽象是面向对象编程的特征之一，类本身，或类中的部分成员，都可以声明为 abstract 的。抽象成员在类中不存在具体的实现。
注意：无需对抽象类或抽象成员标注open注解。
 */
open class Son {
    open fun f() {
        println("我是base.f()方法")
    }
}

abstract class Derived : Son() {
    override abstract fun f()
}

class Test401 {
    @Test
    fun test9() {
        var son = Son()
        println(son.f())
    }
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

class Test402 {
    @Test
    fun test4() {
        val demo = Outer.Nested().foo() // 调用格式：外部类.嵌套类.嵌套类方法/属性
        println(demo)    // == 2
    }
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
class Test403 {
    @Test
    fun test5() {
        val demo = Outer2().Inner().foo()
        println(demo) //   1
        val demo2 = Outer2().Inner().innerTest()
        println(demo2)   // 内部类可以引用外部类的成员，例如：成员属性
    }
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

class Test404 {

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


class `07_类的Getter和Setter` {

    /**
    getter()对应Java中的get()函数，setter()对应Java中的set()函数。不过注意的是，不存在Getter()与Setter()的，
    这只是Kotlin中的叫法而已，真是的写法，还是用get()、set()。可以看下面的例子。

    在Kotlin中，普通的类中一般是不提供getter()与setter()函数的，因为在普通的类中几乎用不到，这一点和Java是相同的，
    但是Java中在定义纯粹的数据类时，会用到get()与set()函数，但是Kotlin专门这种情况定义了数据类,这个特征。
    而数据类是系统已经为我们实现了get()和set()函数。
     */

    class GetAndSet {

        /*
         * getter 和 setter是可选的
         */

        // 当用var修饰时，必须为属性赋默认值 (特指基本数据类型，因为自定义的类型可以使用后期初始化属性，见后续)
        // 即使在用getter()的情况下,不过这样写出来，不管我们怎么去改变其值，其值都为`123`
        var test1: String = ""
            get() = "123"
            set(value) {
                field = value
            }

        // 用val修饰时，用getter()函数时，属性可以不赋默认值。但是不能有setter()函数。
        val test2: String
            get() = "123"       // 等价于：val test2 : String = "123"
    }

    /*
    在上面的代码中出现了set(value){field = value}这样的一句代码。其中value是Koltin写setter()函数时其参数的约定俗成的习惯。
    你也可以换成其他的值。而field是指属性本身。
     */

    /**
    2.1、自定义
    这里讲解属性的自定义getter()与setter()。由上面可知，使用val修饰的属性，是不能有setter()的。而使用var修饰的属性可以同时拥有自定义的getter()与setter()。
    通过两个实例来说明:

    例1：用val修饰的属性自定义情况
     */

    class Mime {
        // size属性
        private val size = 0

        // 即isEmpty这个属性，是判断该类的size属性是否等于0
        val isEmpty: Boolean
            get() = this.size == 0

        //有set()会报错
        //  set(value) {
        //   field = value
        //    }

        // 另一个例子
        val num = 2
            get() = if (field > 5) 10 else 0
    }

    // 测试
    @Test
    fun test2_1() {
        val mime = Mime()
        println("isEmpty = ${mime.isEmpty}")
        println("num = ${mime.num}")
    }

    /*
    输出结果为：

    isEmpty = true
    num = 0
     */

    class Mime_2 {

        var str1 = "test"
            get() = field   // 这句可以省略，kotlin默认实现的方式
            set(value) {
                field = if (value.isNotEmpty()) value else "null"
            }

        var str2 = ""
            get() = "随意怎么修改都不会改变"
            set(value) {
                field = if (value.isNotEmpty()) value else "null"
            }
    }

    // 测试
    @Test
    fun test2_2() {
        val mime = Mime_2()

        println("str = ${mime.str1}")
        mime.str1 = ""
        println("str = ${mime.str1}")
        mime.str1 = "kotlin"
        println("str = ${mime.str1}")

        println("str = ${mime.str2}")
        mime.str2 = ""
        println("str = ${mime.str2}")
        mime.str2 = "kotlin"
        println("str = ${mime.str2}")
    }

    /*
    经过上面的实例，我总结出了以下几点：

    使用了val修饰的属性，不能有setter().
    不管是val还是var修饰的属性，只要存在getter(),其值再也不会变化
    使用var修饰的属性，可以省略掉getter(),不然setter()毫无意义。当然get() = field除外。
    而get() = field是Koltin默认的实现，是可以省略这句代码的。
    故而，在实际的项目开发中，这个自定义的getter与setter的意义不是太大。
     */

    class Apple {
        var str1 = "kotlin_1"
            private set         // setter()访问器的私有化，并且它拥有kotlin的默认实现

        /*
        var test : String?
            @Inject set         // 用`Inject`注解去实现`setter()`

        val str2 = "kotlin_2"
            private set         // 编译错误，因为val修饰的属性，不能有setter

        var str3 = "kotlin_3"
            private get         // 编译出错，因为不能有getter()的访问器可见性
        */
    }

    //如果，属性访问器的可见性修改为private或者该属性直接使用private修饰时，我们只能手动提供一个公有的函数去修改其属性了。就像Java中的Bean的setXXXX()

    /** 三、后备字段 */

    /*
    Kotlin的类不能有字段。 但是，有时在使用自定义访问器时需要有一个后备字段。为了这些目的，Kotlin提供了可以使用字段标识符访问的自动备份字段

例：

        var count = 0   // 初始化值会直接写入备用字段
            set(value){
                field = if(value > 10) value else 0  // 通过field来修改属性的值。
            }
        注意：

        field标识符只能在属性的访问器中使用。这在上面提到过
        如果属性使用至少一个访问器的默认实现，或者自定义访问器通过field标识符引用它，则将为属性生成后备字段。
        看上面的例子，使用了getter()的默认实现。又用到了field标识符。

        例：不会生成后备字段的属性

        val size = 0

        /*
            没有后备字段的原因：
            1. 并且`getter()`不是默认的实现。没有使用到`field`标识符
            2. 使用`val`修饰，故而不存在默认的`setter()`访问器，也没有`field`修饰符
        */
        val isEmpty ：Boolean
            get() = this.size == 0
        不管是后备字段或者下面的后备属性，都是Kotlin对于空指针的一种解决方案，可以避免函数访问私有属性而破坏它的结构。

        这里值得强调的一点是，setter()中
     */


    /** 四、后备属性 */
    /*
        所谓后备属性，其实是对后备字段的一个变种，它实际上也是隐含试的对属性值的初始化声明，避免了空指针。
        我们根据一个官网的例子，进行说明：
     */

    class Orange {
        private var _table: Map<String, Int>? = null
        public val table: Map<String, Int>
            get() {
                if (_table == null) {
                    _table = HashMap() // 初始化
                }
                // ?: 操作符，如果_table不为空则返回，反之则抛出AssertionError异常
                return _table ?: throw AssertionError("Set to null by another thread")
            }
    }
    /*
    从上面的代码中我们可以看出：_table属性是私有的，我们不能直接的访问它。故而提供了一个公有的后备属性（table）去初始化我们的_table属性。

    通俗的讲，这和在Java中定义Bean属性的方式一样。因为访问私有的属性的getter和setter函数，会被编译器优化成直接反问其实际字段。因此不会引入函数调用开销。
     */

    /** 五、编译时常数 */
    /*
    在开发中，难免会遇到一些已经确定的值的量。在Java中，我们可以称其为常量。在kotlin中，我们称其为编译时常数。我们可以用const关键字去声明它。其通常和val修饰符连用

    关键字：const
    满足const的三个条件：
    对象的顶层或成员，即顶层声明。
    初始化为String类型或基本类型的值
    没有定制的getter()
    例：

    const val CONST_NUM = 5
    const val CONST_STR = "Kotlin"
    关于编译时常数这个知识点更详细的用法，我在讲解讲解变量的定义这一章节时，已经详细讲解过了。这里不做累赘。
     */

    /** 六、后期初始化属性 */
    /*
    通常，声明为非空类型的属性必须在构造函数中进行初始化。然而，这通常不方便。例如，可以通过依赖注入或单元测试的设置方法初始化属性。
    在这种情况下，不能在构造函数中提供非空的初始值设置，但是仍然希望在引用类的正文中的属性时避免空检查。故而，后期初始化属性就应运而生了。

    关键字 ： lateinit
    该关键字必须声明在类的主体内，并且只能是用var修饰的属性。并且该属性没有自定义的setter()与getter()函数。
    该属性必须是非空的值，并且该属性的类型不能为基本类型。
     */

    class User(var name: String)

    class Cherry {
        // 声明一个User对象的属性
        lateinit var user: User

        /*
            下面的代码是错误的。因为用lateinit修饰的属性，不能为基本类型。
            这里的基本类型指 Int、Float、Double、Short等，String类型是可以的
        */

        // lateinit var num : Int
    }
    //  关于后期初始化属性这一个知识点，我在讲解讲解变量的定义这一章节时，已经详细讲解过了。这里不做累赘。
    //  不过关于这一知识点，一般都是在Android开发中或者在依赖注入时会用到。

    class Shop {
        var address: String = ""
    }


    class TestShop {
        lateinit var shop: Shop
        fun setup() {
            shop = Shop()
            shop.address = "beijing"
        }

        fun test() {
            //::表示创建成员引用或者类引用
            if (::shop.isInitialized)
                println(shop.address)
        }
    }

    @Test
    fun test3() {
        var testShop = TestShop()
        testShop.setup()
        TestShop().test()
    }


}