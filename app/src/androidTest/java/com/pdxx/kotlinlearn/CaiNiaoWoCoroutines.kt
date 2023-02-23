package com.pdxx.kotlinlearn

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

class CaiNiaoWoCoroutines {
    /*
     GlobalScope   是全生命周期的 , launch 和 async 差不多,返回的结果不一样

     runBlocking{}   阻塞线程 , 可以用于桥接普通函数和协程
     说明 : 所有的协程和挂起函数只能在协程调用 , 你写了一个协程 , 可以在一个地方用runblocking
     调用你写的协程 ,就是这一块的生命周期是有效的 , runblocking要执行完才能执行下一步 .

     runBlocking中是可以直接写launch 和 async 的 , 返回的对象是job , deferrod , 当他们调join方法,必须等这个
     协程执行完才执行下一步 ,调cancel()会直接取消执行 .

     withTimeout   超时会自动取消内部协程,并抛出异常
     withTimeoutOrNull      超时会自动取消内部协程,不抛异常

     await     可以获取async的异步Deferred结果,可以等待的

     suspend suspend 关键字可以帮助我们消除回调，用同步的写法写异步。挂起函数

     */
    @Test
    fun testKtCoroutines() {
      //  testLaunch()

//        testAsync()

//        testRunBlocking()

//        testCancelJoin()

//        testTimeout()

//        testAwait()
    }

    /**
     * 1. CoroutineScope.launch    异步,不阻塞线程,就是同时执行的.
     */
    @Test
     fun testLaunch() {
        val time = measureTimeMillis {
            GlobalScope.launch {
                Thread.sleep(1000)
                println("------------testLaunch第一个launch,currentThread:--> ${Thread.currentThread()}")
            }

            GlobalScope.launch {
                Thread.sleep(1000)
                println("-----------testLaunch第二个launch,currentThread:--> ${Thread.currentThread()}")
            }

            println("-----------testLaunch非launch部分,currentThread:--> ${Thread.currentThread()}")
            // 由于函数生命周期原因,执行完代码块后JVM销毁函数栈,所以如果没有Thread.sleep(1000),那么
            // 上面的两个launch的异步操作不会进行
            Thread.sleep(1000)
        }
        println("---------testLaunch耗时:--> $time")
    }

    /**
     * 2. CoroutineScope.async    异步,不阻塞线程
     *    返回Deferred
     */
    @Test
     fun testAsync() {
        val time = measureTimeMillis {
            GlobalScope.async {
                Thread.sleep(1000)
                println("testAsync第一个async,currentThread:--> ${Thread.currentThread()}")
            }
            GlobalScope.async {
                Thread.sleep(1000)
                println("testAsync第二个async,currentThread:--> ${Thread.currentThread()}")
            }
            println("testAsync非async部分,currentThread:--> ${Thread.currentThread()}")
            Thread.sleep(1000)
        }
        println("testAsync耗时:--> $time")
    }


    /**
     * 3. runBlocking   阻塞线程
     *    可以用于桥接普通函数和协程
     */
    @Test
     fun testRunBlocking() {
        val time = measureTimeMillis {
            runBlocking {
                println("testRunBlocking在runBlocking内部delay前,currentThread:---> ${Thread.currentThread()}")
                delay(2000)
                println("testRunBlocking在runBlocking内部delay后,currentThread:---> ${Thread.currentThread()}")
            }
            println("testRunBlocking非runBlocking部分,currentThread: --->${Thread.currentThread()}")
        }
        println("testRunBlocking耗时: --->$time")
    }

    /**
     * 4. cancel join
     *    runBlocking会等待内部协程执行完毕才结束
     *
     *    l1 , l2 都是job
     *    a2 是deferrod
     */
    @Test
     fun testCancelJoin() = runBlocking {
        val time = measureTimeMillis {
            val L1 = launch {
                println("testCancelJoin第一个launch,currentThread: ${Thread.currentThread()}")
            }
            val L2 = launch {
                println("testCancelJoin第二个launch,currentThread: ${Thread.currentThread()}")
            }
            val A1 = async {
                repeat(20) {
                    println("testCancelJoin第一个async,当前循环次数$it,currentThread: ${Thread.currentThread()}")
                    delay(200)
                }
            }

//            A1.join()   // 必须A1结束协程,才会往下执行

            val A2 = async {
                println("testCancelJoin第二个async,currentThread: ${Thread.currentThread()}")
                A1.cancel()     // 取消A1
            }
            println("testCancelJoin非launch和async部分,currentThread: ${Thread.currentThread()}")
        }
        println("testCancelJoin耗时: $time")
    }

    /**
     * 5. withTimeout   超时会自动取消内部协程,并抛出异常
     *    withTimeoutOrNull      超时会自动取消内部协程,不抛异常
     */
    private fun testTimeout() = runBlocking {
        // 超时会自动取消内部协程,并抛出异常
//        withTimeout(2000) {
//            repeat(10) {
//                println("withTimeout,当前循环次数$it,currentThread: ${Thread.currentThread()}")
//                delay(300)
//            }
//        }
        // 超时会自动取消内部协程,不抛异常
        withTimeoutOrNull(2000) {
            repeat(10) {
                println("withTimeout,当前循环次数$it,currentThread: ${Thread.currentThread()}")
                delay(300)
            }
        }
    }

    /**
     * 6. await     可以获取async的异步Deferred结果
     */
    @Test
     fun testAwait() = runBlocking {
        val time = measureTimeMillis {
            val A1 = async {
                println("testAwait第一个async,currentThread: ${Thread.currentThread()}")
                delay(2000)
                66666
            }

            val A2 = async {
//                println("testAwait第二个async,currentThread: ${Thread.currentThread()}")
//                delay(1000)
//                88888
                getA2Value()
            }
            //会等到都执行完再打印 , 上面那个时间是3000 , a1.getcomplete
            println("testAwait非async部分,currentThread: ${Thread.currentThread()},结果: ${A1.await()}  ${A2.await()}")
        }
        println("testAwait耗时: $time")
    }

    private suspend fun getA2Value(): Int {
        println("testAwait第二个async,currentThread: ${Thread.currentThread()}")
        // delay属于suspend函数,只能在协程或者其它suspend函数中调用
        delay(1000)
        return 88888
    }
}