package coroutine

import kotlinx.coroutines.*
import org.junit.Test

//作者：业志陈
//链接：https://juejin.cn/post/6844903972755472391
//来源：稀土掘金
//著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
class `19_1协程` {

    @Test
    fun test1() {
        GlobalScope.launch {
            println("简单测试")
        }

        GlobalScope.launch { // 在后台启动一个新协程，并继续执行之后的代码
            delay(1000L) // 非阻塞式地延迟一秒
            println("World!") // 延迟结束后打印
        }
        println("Hello,") //主线程继续执行，不受协程 delay 所影响
        Thread.sleep(2000L) // 主线程阻塞式睡眠2秒，以此来保证JVM存活

        //delay() 是一个挂起函数（suspending function），挂起函数只能由协程或者其它挂起函数进行调度。
        // 挂起函数不会阻塞线程，而是会将协程挂起，在特定的时候才再继续运行

        //当协程 A 调用 delay(1000L) 函数来指定延迟1秒后再运行时，协程 A 所在的线程只是会转而去执行协程 B，
        // 等到1秒后再把协程 A 加入到可调度队列里。所以说，线程并不会因为协程的延时而阻塞，这样可以极大地提高线程
        // 的并发灵活度
    }


    //二、桥接阻塞与非阻塞的世界
    //在第一个协程程序里，混用了非阻塞代码 delay() 与阻塞代码 Thread.sleep() ，使得我们很容易就搞混当前程序
    // 是否是阻塞的。可以改用 runBlocking 来明确这种情形
    @Test
    fun test2() {
        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L)
            println("World!")
        }
        println("Hello,") // main thread continues here immediately

        //下面这个代码就是为了让 线程执行 完成
        runBlocking {// 这个表达式会阻塞主线程
            delay(2000L)// 在这里阻塞主线程2秒钟，以保持 JVM 存活
        }

    }

    @Test
    fun test3() {
        fun main() = runBlocking<Unit> { // 启动主协程
            GlobalScope.launch { // 在后台启动一个新协程并继续执行
                delay(1000L)
                println("World!")
            }
            println("Hello,") // 主协程立即继续执行这里的代码
            delay(2000L)        // 延迟2秒钟以保持 JVM 存活
        }
    }
    //设定是在主程序
    //这里 runBlocking<Unit> { ... }  作为用于启动顶层主协程的适配器。我们显式地指定它的返回类型 Unit，
    // 因为 kotlin 中 main 函数必须返回 Unit 类型，但一般我们都可以省略类型声明，因为编译器可以自动推导
    // （这需要代码块的最后一行代码语句没有返回值或者返回值为 Unit）


    //这也是为挂起函数编写单元测试的一种方法：
    //class MyTest {
    //    @Test
    //    fun testMySuspendingFunction() = runBlocking<Unit> {
    //        // here we can use suspending functions using any assertion style that we like
    //    }
    //}
    //复制代码
    //需要注意的是，runBlocking 代码块默认运行于其声明所在的线程，而 launch 代码块默认运行于线程池中，可以通过打印当前线程名来进行区分
    //


    //等待作业
    //延迟一段时间来等待另一个协程运行并不是一个好的选择，可以显式（非阻塞的方式）地等待协程执行完成

    @Test
    fun test4() = runBlocking {
        //Kotlin 协程中的 Job 对象提供了控制和监控协程的机制，可以使用它来等待协程的完成或取消。
        val job = GlobalScope.launch {  // 启动一个新的协程并保持对其 Job 的引用
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join() // 等待子协程完成
        //sampleEnd
    }


    //四、结构化并发
    // 比如协程中的代码被挂起（比如错误地延迟了太多时间），或者启动了太多协程导致内存不足。此时我们需要手动保留对
    // 所有已启动协程的引用以便在需要的时候停止协程，但这很容易出错

    //kotlin 提供了更好的解决方案。我们可以在代码中使用结构化并发。正如我们通常使用线程那样（线程总是全局的）
    // ，我们可以在特定的范围内来启动协程

    //在上面的示例中，我们通过 runBlocking 将 main() 函数转为协程。每个协程构造器（包括 runBlocking）
    // 都会将 CoroutineScope 的实例添加到其代码块的作用域中。我们可以在这个作用域中启动协程，而不必显式地 join，
    // 因为外部协程（示例代码中的 runBlocking）在其作用域中启动的所有协程完成之前不会结束。因此，我们可以简化示例代码：

    @Test
    fun test5() {
        fun main() = runBlocking { // this: CoroutineScope
            launch { // launch a new coroutine in the scope of runBlocking
                delay(1000L)
                println("World!")
            }
            println("Hello,")
        }
    }

    //launch 函数是 CoroutineScope 的扩展函数，而 runBlocking 的函数体参数也是被声明为 CoroutineScope 的扩展函数，
    // 所以 launch 函数就隐式持有了和 runBlocking 相同的协程作用域。此时即使 delay 再久， println("World!")
    // 也一定会被执行


    //作用域构建器

    //除了使用官方的几个协程构建器所提供的协程作用域之外，还可以使用 coroutineScope 来声明自己的作用域。
    // coroutineScope 用于创建一个协程作用域，直到所有启动的子协程都完成后才结束
    //runBlocking 和 coroutineScope 看起来很像，因为它们都需要等待其内部所有相同作用域的子协程结束后才会结束自己。
    // 两者的主要区别在于 runBlocking 方法会阻塞当前线程，而 coroutineScope 只是挂起并释放底层线程以供其它协程使用。
    // 由于这个差别，所以 runBlocking 是一个普通函数，而 coroutineScope 是一个挂起函数
    //

    @Test
    fun test6() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // This line will be printed before the nested launch
        }

        println("Coroutine scope is over") // This line is not printed until the nested launch completes
    }
//注意，在 “Task from coroutine scope” 消息打印之后，在等待 launch 执行完之前 ，将执行并打印“Task from runBlocking”，
// 尽管此时 coroutineScope 尚未完成

    /*
    时间线
    0ms：runBlocking 开始，启动第一个 launch（延迟 200 毫秒）。
    0ms：coroutineScope 开始，启动第二个 launch（延迟 500 毫秒），并开始延迟 100 毫秒。
    100ms：coroutineScope 延迟结束，打印 "Task from coroutine scope"。
    200ms：第一个 launch 延迟结束，打印 "Task from runBlocking"。
    500ms：第二个 launch 延迟结束，打印 "Task from nested launch"。
    第二个 launch 完成后，coroutineScope 块结束，打印 "Coroutine scope is over"。
    结论
    launch { ... } 和 coroutineScope { ... } 内的 launch { ... } 是同时开始执行的，它们都是异步的。
    coroutineScope 块内部的代码会按照顺序执行，coroutineScope 会等待内部所有协程完成后才会结束。
    runBlocking 块会等待整个 coroutineScope 块完成后，才会继续执行并打印 "Coroutine scope is over"。
     */


    //六、提取函数并重构
    //抽取 launch 内部的代码块为一个独立的函数，需要将之声明为挂起函数。挂起函数可以像常规函数一样在协程中使用
    // ，但它们的额外特性是：可以依次使用其它挂起函数（如 delay 函数）来使协程挂起

    @Test
    fun test7() = runBlocking {
        launch { doWorld() }
        println("Hello,")
    }

    // this is your first suspending function
    suspend fun doWorld() {
        delay(1000L)
        println("World!")
    }

    //但是如果提取出的函数包含一个在当前作用域中调用的协程构建器的话该怎么办？
    // 在这种情况下，所提取函数上只有 suspend 修饰符是不够的。为 CoroutineScope 写一个扩展函数 doWorld
    // 是其中一种解决方案，但这可能并非总是适用的，因为它并没有使 API 更加清晰。 常用的解决方案是要么显式将
    // CoroutineScope 作为包含该函数的类的一个字段， 要么当外部类实现了 CoroutineScope 时隐式取得。
    // 作为最后的手段，可以使用 CoroutineScope(coroutineContext)，不过这种方法结构上并不安全，
    // 因为你不能再控制该方法执行的作用域。只有私有 API 才能使用这个构建器。


    //七、协程是轻量级的
    //运行以下代码：

    fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

    @Test
    fun test8() = runBlocking {
        repeat(100_000) { // launch a lot of coroutines
            launch {
                delay(1000L)
                log("--")
                print(".")
            }
        }
    }
    //复制代码
    //以上代码启动了10万个协程，每个协程延时一秒后都会打印输出。如果改用线程来完成的话，很大可能会发生内存不足异常，
// 但用协程来完成的话就可以轻松胜任
    //


    //八、全局协程类似于守护线程
    //以下代码在 GlobalScope 中启动了一个会长时间运行的协程，它每秒打印两次 "I'm sleeping" ，
    // 然后在延迟一段时间后从 main 函数返回
    //import kotlinx.coroutines.*
    //
    @Test
    fun test9() = runBlocking {
        //sampleStart
        GlobalScope.launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L) // just quit after delay
        //sampleEnd
    }
    //复制代码
    //你可以运行代码并看到它打印了三行后终止运行：
    //I'm sleeping 0 ...
    //I'm sleeping 1 ...
    //I'm sleeping 2 ...
    //复制代码
    //这是由于 launch 函数依附的协程作用域是 GlobalScope，而非 runBlocking 所隐含的作用域。
// 在 GlobalScope 中启动的协程无法使进程保持活动状态，它们就像守护线程（当主线程消亡时，守护线程也将消亡）
    //


    @Test
    fun test10() {

        runBlocking {
            /*
            runBlocking：创建一个新的协程并阻塞当前线程，直到其内部所有协程完成。
            用途：通常用于在主线程或单元测试中运行协程，以确保在代码继续执行之前，所有协程都已完成。
            行为：由于它会阻塞当前线程，所以不适合在需要保持线程响应的地方使用（例如，Android 的主线程）。
             */

        }


        runBlocking {
            launch {
                /*
                runBlocking 内的 launch：launch 创建一个新的协程，并在 runBlocking 的上下文中异步执行。
                用途：用于在阻塞的协程上下文中启动异步任务。
                行为：launch 内部的协程不会阻塞 runBlocking，但 runBlocking 会等待所有子协程完成后才结束。
                 */
            }
        }

        suspend fun testSus() {
            coroutineScope {
                launch {
                    // 这个代码块中的代码将在一个新的协程中异步执行
                }
                /*
                coroutineScope：创建一个新的协程作用域，但不会阻塞当前线程。它会等待其内部所有协程完成后才继续执行外部代码。
                用途：用于在挂起函数中创建局部协程作用域。
                行为：coroutineScope 会等待所有子协程完成后才结束。
                 */
            }
        }

        GlobalScope.launch {
            /*
            GlobalScope.launch：在全局作用域启动一个新的协程，不绑定到任何特定的协程作用域。
            用途：用于启动全局生命周期的协程，通常用于应用程序级别的长期任务。
            行为：GlobalScope.launch 不会阻塞当前线程，且不受调用上下文的影响，因此不适合使用 GlobalScope 来启动短生命周期的任务。
             */
        }


        suspend fun fetchData(): String {
            // 在 IO 线程池中执行网络请求
            return withContext(Dispatchers.IO) {//表示切换到io线程
                // 模拟网络请求
                delay(1000L)
                "Data from network"
            }
        }

        fun main() = runBlocking {
            val data = fetchData()
            println("Fetched data: $data")
        }

        /*
        suspend 关键字：用于标记挂起函数，使其能够在协程中非阻塞地执行任务。
        挂起函数只能在协程内部或其他挂起函数中调用：确保它们在正确的上下文中执行。
        挂起操作：可以挂起协程执行，而不会阻塞线程。
        上下文切换：通过使用 withContext 等函数，可以轻松切换协程上下文。
         */


    }

    @Test
    fun test11(){
         runBlocking {
            // 在主线程上执行
            println("Main program starts: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.Default) {
                // 切换到 Default 调度器，适合 CPU 密集型任务
                println("Fake work starts: ${Thread.currentThread().name}")
                delay(1000)
                println("Fake work finished: ${Thread.currentThread().name}")
                "Result"
            }

            println("Main program ends: ${Thread.currentThread().name}")
            println("Result: $result")
        }
        /*
        withContext 与 launch 的对比
        withContext：用于在协程中切换上下文并等待结果。withContext 会挂起当前协程，等待其内部代码执行完毕后再恢复。
        launch：用于启动一个新的协程，并在指定的上下文中异步执行代码。launch 不会等待结果，而是立即返回。
         */

        fun main() = runBlocking {
            println("Main program starts: ${Thread.currentThread().name}")

            launch(Dispatchers.Default) {
                // 启动新的协程，切换到 Default 调度器
                println("Fake work starts: ${Thread.currentThread().name}")
                delay(1000)
                println("Fake work finished: ${Thread.currentThread().name}")
            }

            println("Main program ends: ${Thread.currentThread().name}")
        }

        /*
        在这个示例中，launch(Dispatchers.Default) 启动了一个新的协程，并切换到 Default 调度器执行任务。主协程不会等待这个任务完成，
        而是立即继续执行。

        总结
        上下文切换：通过 withContext 和不同的调度器（如 Dispatchers.IO、Dispatchers.Default）来指定协程的执行环境。
        优化性能：合理地选择调度器，可以提高任务执行的效率和响应性。
        资源管理：避免阻塞主线程和重要工作线程，充分利用系统资源。
        通过理解和使用上下文切换，可以更灵活地管理协程的执行，提高应用程序的并发性能
         */


    }


}