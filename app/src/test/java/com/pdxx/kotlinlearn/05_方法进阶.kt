package com.pdxx.kotlinlearn

import org.junit.Test

class `05_方法进阶` {
    /**
     * 高阶函数是将函数用作参数或返回值的函数
     */
    //目标: 对集合求和 , 并且返回每一项
    //接收callback作为回调
                //sum扩展  callback:(int)回调,(int)是集合元素 unit是没有返回值  : int是方法返回值
    fun List<Int>.sum(callback: (Int) -> Unit): Int { //callback: (Int) -> Unit：表示这个 sum 函数接收一个参数 callback，callback 是一个函数类型，接受一个 Int 参数并返回 Unit。
        var result = 0
        for (v in this) {//this表示扩展的本身 , 代表list
            result += v
            callback(v)
        }
        return result;
    }


    /*
    传递 lambda 表达式：

    { println("it:${it}") } 是传递给 sum 函数的 lambda 表达式。
    这个 lambda 表达式的类型是 (Int) -> Unit，即接受一个 Int 参数并返回 Unit。
    在 lambda 表达式中，it 是当前元素。
    执行过程
    当 list.sum { println("it:${it}") } 被执行时，sum 函数会遍历 list 中的每一个元素，并对每个元素执行以下操作：

    累加当前元素到 result。
    调用 callback，即传递的 lambda 表达式 { println("it:${it}") }，并将当前元素作为参数传递给它。
    */


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

    加强模块化：闭包有益于模块化编程，它能以简单的方式开发较小的模块，
    从而提高开发速度和程序的可复用性

    抽象：闭包是数据和行为的组合，这使得闭包具有较好抽象能力
    灵活：闭包的应用是的编程更加的灵活 简化代码：闭包还能简化代码
     */

//    需求：实现一个接受一个testClosure方法，该方法要接受一个Int类型的v1参数，
//    同时能够返回一个声明为(v2: Int, (Int) -> Unit)的函数，
//    并且这个函数能够计算v1与v2的和

    //(Int, (Int) -> Unit) -> Unit 返回的类型 , 这里没有说错，就是返回的类型是怎么样的。
    //可以看返回值就能看出来 。

    /*
    testClosure 是一个接受一个整数 v1 作为参数并返回一个函数的高阶函数。返回的函数接受两个参数：一个整数 v2 和一个函数 printer，
    该函数接受一个整数作为参数并返回 Unit。

    fun testClosure(v1: Int)：testClosure 接受一个整数参数 v1。
    return fun(v2: Int, printer: (Int) -> Unit)：返回一个匿名函数，该函数接受两个参数 v2 和 printer。
    printer(v1 + v2)：调用 printer 函数，并传递 v1 和 v2 的和作为参数
     */

    fun testClosure(v1: Int): (Int, (Int) -> Unit) -> Unit {
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




//从gpt 上进行学习和理解  , 提问的时候 ： 让其详细说明一下
    /*
    1. 函数签名
    函数作为参数：你可以将一个函数传递给另一个函数。例如，map、filter和forEach等标准库函数都接受函数类型的参数。

    fun <T>：这是一个泛型函数，<T>表示函数可以处理任何类型的元素。
    List<T>.customFilter(predicate: (T) -> Boolean)：这是一个扩展函数，扩展了List类。
            predicate参数是一个函数类型 (T) -> Boolean，表示接收一个类型为T的参数并返回一个Boolean值的函数。
    : List<T>：函数返回一个List<T>类型的列表。

    2. 函数体
    val result = mutableListOf<T>()：创建一个空的可变列表来存储过滤后的结果。
    for (item in this)：遍历列表中的每一个元素，this引用的是调用customFilter方法的那个列表。
    if (predicate(item)) { result.add(item) }：使用predicate函数来测试每个元素。如果predicate返回true，则将该元素添加到结果列表中。
    return result：返回过滤后的结果列表。
     */
    fun <T> List<T>.customFilter(predicate: (T) -> Boolean): List<T> {
        val result = mutableListOf<T>()
        for (item in this) {
            if (predicate(item)) {
                result.add(item)
            }
        }
        return result
    }

    /*
    1. 创建一个列表
            val numbers = listOf(1, 2, 3, 4, 5, 6)：创建一个包含数字1到6的不可变列表。
    2. 调用customFilter函数
        val evenNumbers = numbers.customFilter { it % 2 == 0 }：调用customFilter扩展函数，
        并传入一个lambda表达式 { it % 2 == 0 } 作为参数。
            it 是lambda表达式中单个参数的默认名称，表示列表中的每一个元素。
            it % 2 == 0 计算元素是否为偶数，如果是则返回true，否则返回false。

            在这个例子中，customFilter函数是一个高阶函数，因为它接受了一个函数类型 (T) -> Boolean 作为参数。通过传递不同的函数或lambda表达式，
            可以灵活地改变过滤逻辑，而不需要修改customFilter函数的内部实现。这使得代码更加模块化和可重用。
     */



    @Test
    fun test4() {
        val numbers = listOf(1, 2, 3, 4, 5, 6)
        val evenNumbers = numbers.customFilter { it % 2 == 0 }
        println(evenNumbers)  // 输出: [2, 4, 6]
    }


    /*
    函数作为返回值
    函数作为返回值：一个函数可以返回另一个函数。例如，你可以编写一个工厂函数来生成其他函数。


    1. 函数签名
    fun createMultiplier(factor: Int): (Int) -> Int：这是一个函数，接受一个Int类型的参数factor，返回一个函数。
    返回的函数接受一个Int类型的参数，并返回一个Int类型的值。
            factor: Int：表示传入的参数factor是一个整数。
            : (Int) -> Int：表示返回的是一个函数类型，该函数接收一个Int参数并返回一个Int结果。
    2. 函数体
    return { number -> number * factor }：返回一个lambda表达式。这个lambda表达式表示一个匿名函数，接收一个整数参数number，
    返回number与factor的乘积。
    { number -> number * factor }：是一个lambda表达式，number是它的参


     */
    fun createMultiplier(factor: Int): (Int) -> Int {
        return { number -> number * factor }
    }


    /*
    1. 调用createMultiplier函数
         val multiplyBy2 = createMultiplier(2)：调用createMultiplier函数并传入参数2。这将返回一个新的函数，该函数接受一个整数并将其乘以2。
                createMultiplier(2)返回的函数实际上是{ number -> number * 2 }。
    2. 调用返回的函数
         println(multiplyBy2(5))：调用返回的函数multiplyBy2并传入参数5。这将计算5 * 2并返回10，然后打印结果。
            multiplyBy2(5)等价于执行{ number -> number * 2 }(5)，即计算5 * 2。
    详细解释高阶函数
    在这个例子中，createMultiplier函数是一个高阶函数，因为它返回一个函数。通过传入不同的factor值，可以生成不同的乘法器函数，而不需要重复编写乘法逻辑。这使得代码更加灵活和模块化。

    工作流程
    1,调用createMultiplier时，传入一个因子，例如2。
    2,createMultiplier返回一个lambda表达式，这个lambda表达式将每次调用时乘以因子。
    3,保存返回的lambda表达式到变量，例如multiplyBy2。
    4,使用保存的lambda表达式，例如multiplyBy2(5)，来执行乘法操作，并输出结果。
    这展示了Kotlin高阶函数的强大功能，可以返回和操作函数，从而实现代码的灵活性和可重用性。
    */
    @Test
    fun test5() {
        val multiplyBy2 = createMultiplier(2)
        println(multiplyBy2(5))  // 输出: 10
    }


    /*
    Lambda表达式：Kotlin提供了简洁的语法来定义匿名函数，即lambda表达式。lambda表达式可以很方便地传递给高阶函数。
     */
    @Test
    fun test6() {
        val numbers = listOf(1, 2, 3, 4, 5)
        numbers.forEach { println(it) }
    }


    /*
内联函数：为了优化高阶函数的性能，Kotlin提供了inline关键字，将函数体直接插入调用处，减少函数调用的开销。

1. inline关键字
inline：表示这个函数是一个内联函数。当调用这个函数时，编译器会将函数的代码体直接插入到调用点，减少函数调用的开销。这在测量时间的场景中尤为重要，因为可以避免不必要的性能损耗。
2. 函数签名
fun <T> measureTime(block: () -> T): T：这是一个泛型函数，接受一个返回类型为T的无参函数block作为参数，并返回一个类型为T的结果。
<T>：表示泛型类型参数，可以是任意类型。
block: () -> T：表示接受一个无参数并返回类型为T的函数。
: T：表示函数返回类型为T。
3. 函数体
val start = System.nanoTime()：记录当前时间的纳秒值。
val result = block()：执行传入的代码块block并将其结果存储在变量result中。
val end = System.nanoTime()：记录当前时间的纳秒值。
println("Execution time: ${end - start} ns")：打印代码块的执行时间，单位为纳秒。
return result：返回代码块的执行结果。

     */

    inline fun <T> measureTime(block: () -> T): T {
        val start = System.nanoTime()
        val result = block()
        val end = System.nanoTime()
        println("Execution time: ${end - start} ns")
        return result
    }


    /*
    1. 调用measureTime函数
val result = measureTime { ... }：调用measureTime函数，并传入一个lambda表达式作为参数。lambda表达式中包含需要测量执行时间的代码块。
代码块内容是(1..1000000).sum()，这段代码创建一个从1到1000000的整数范围，并计算它们的和。
2. 打印结果
println("Result: $result")：打印代码块的执行结果。
工作流程
调用measureTime时，传入一个代码块（lambda表达式）。
measureTime记录开始时间。
执行传入的代码块并记录结果。
记录结束时间。
打印执行时间。
返回代码块的执行结果。
示例解释
measureTime被调用，并传入代码块(1..1000000).sum()。
measureTime记录当前时间start。
执行代码块，计算从1到1000000的和，结果为500000500000。
记录当前时间end。
打印执行时间，例如Execution time: 12345678 ns（具体时间根据机器性能和负载情况而定）。
返回计算结果500000500000。
打印结果Result: 500000500000。
完整执行流程
measureTime函数记录开始时间start。
执行代码块(1..1000000).sum()并得到结果500000500000。
记录结束时间end。
打印执行时间Execution time: ... ns。
返回结果500000500000。
main函数打印结果Result: 500000500000。
这段代码展示了Kotlin内联高阶函数的强大功能，利用measureTime可以方便地测量任意代码块的执行时间，同时保持代码简洁和高效。
     */
    @Test
    fun test7() {
        val result = measureTime {
            // 需要测量执行时间的代码块
            (1..1000000).sum()
        }
        println("Result: $result")
    }


}