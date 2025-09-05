package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pdxx.kotlinlearn.databinding.ActivityCoroutinesBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * CoroutinesActivity: 全面演示Kotlin协程在Android中的用法
 * 
 * 1.  **协程是什么?**
 *     协程（Coroutines）是"Cooperative Routines"的缩写，可以看作是可被挂起和恢复的计算实例，即轻量级的线程。
 *     在Android中，它解决了两大痛点：
 *     a. **主线程阻塞 (UI Freezing)**: 避免在主线程执行长时间任务（如网络请求、数据库读写），防止ANR。
 *     b. **回调地狱 (Callback Hell)**: 用顺序、同步的代码风格写出异步、非阻塞的代码，极大提高了可读性。
 * 
 * 2.  **核心概念**
 *     - **CoroutineScope (协程作用域)**: 每个协程都在一个作用域内启动。作用域可以管理协程的生命周期。Android中最常用的是`lifecycleScope`和`viewModelScope`，它们能感知组件生命周期，在组件销毁时自动取消内部所有协程，避免内存泄漏。
 *     - **CoroutineContext (协程上下文)**: 协程的"配置信息"，最重要的元素是`Job`和`Dispatcher`。
 *       - **Job**: 协程的句柄，可以控制其生命周期（如取消）。
 *       - **Dispatcher (调度器)**: 决定协程在哪个线程或线程池上执行。
 *         - `Dispatchers.Main`: Android主线程，用于UI操作。
 *         - `Dispatchers.IO`: 优化的后台线程池，用于磁盘或网络I/O操作。
 *         - `Dispatchers.Default`: CPU密集型任务的后台线程池，如大型列表排序、JSON解析。
 *     - **CoroutineBuilder (协程构建器)**: 用于启动协程。
 *       - `launch`: "Fire and forget"，启动一个新协程，不返回结果。
 *       - `async`: 启动一个新协程，并允许你使用`.await()`在一个`Deferred`对象上等待其结果。
 *     - **suspend fun (挂起函数)**: 协程的核心。一个`suspend`函数可以在不阻塞线程的情况下暂停其执行，并在稍后恢复。所有耗时操作（如网络请求）都应在挂起函数中完成。
 */
class CoroutinesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutinesBinding
    private var runningJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLaunch.setOnClickListener { handleLaunch() }
        binding.btnAsync.setOnClickListener { handleAsync() }
        binding.btnWithContext.setOnClickListener { handleWithContext() }
        binding.btnCancel.setOnClickListener { handleCancellation() }
        binding.btnException.setOnClickListener { handleException() }
        binding.btnLifecycleScope.setOnClickListener { handleLifecycleScope() }
        binding.btnClearLog.setOnClickListener { 
            logToScreen("Log cleared.", true)
        }
    }

    /**
     * [launch]: 启动一个"即发即忘"的协程。
     * - 不会阻塞当前线程。
     * - 不直接返回结果。
     * - 适用于不需要结果的后台任务，如写入数据库、发送分析事件等。
     */
    private fun handleLaunch() {
        logToScreen("---" + "Launch Demo" + "---", true)
        logToScreen("启动一个后台任务，2秒后完成...")

        lifecycleScope.launch { // 在与Activity生命周期绑定的作用域中启动
            logToScreen("Coroutine started on " + threadName())
            delay(2000) // 模拟耗时操作，非阻塞地暂停协程
            logToScreen("后台任务完成!")
        }
        logToScreen("Launch构建器不会阻塞主线程，此消息会立即显示。")
    }

    /**
     * [async/await]: 并发执行任务并等待结果。
     * - `async` 启动一个协程并返回一个 `Deferred<T>` 对象，它承诺在未来会有一个类型为T的结果。
     * - `await()` 是一个挂起函数，等待`Deferred`对象的结果，而不会阻塞线程。
     * - 适用于需要并发执行多个独立任务，然后合并它们结果的场景。
     */
    private fun handleAsync() {
        logToScreen("---" + "Async/Await Demo" + "---", true)
        logToScreen("并发获取用户数据和好友列表...")

        lifecycleScope.launch {
            val startTime = System.currentTimeMillis()

            // aync允许并发执行，总耗时将约等于最长的那个任务的时间
            val userDeferred = async { fetchUserData() } 
            val friendsDeferred = async { fetchFriendList() }

            // 使用await()等待两个任务的结果
            val user = userDeferred.await() 
            val friends = friendsDeferred.await()

            val duration = System.currentTimeMillis() - startTime
            logToScreen("获取成功: " + user + ", " + friends)
            logToScreen("总耗时: " + duration + "ms (并发执行)")
        }
    }

    private suspend fun fetchUserData(): String {
        delay(1000) // 模拟网络延迟
        return "User(name=Alex)"
    }

    private suspend fun fetchFriendList(): String {
        delay(1500) // 模拟网络延迟
        return "[Friend1, Friend2, Friend3]"
    }

    /**
     * [withContext]: 安全地切换协程上下文（线程）。
     * - 是一个挂起函数，调用它会挂起当前协程，在指定的Dispatcher上执行代码块，然后将结果带回原始Dispatcher。
     * - 是在主线程协程中执行IO或CPU密集型任务的标准做法。
     */
    private fun handleWithContext() {
        logToScreen("---" + "withContext Demo" + "---", true)
        lifecycleScope.launch(Dispatchers.Main) { // 明确从Main线程开始
            logToScreen("Coroutine started on " + threadName())
            logToScreen("准备切换到IO线程执行网络请求...")

            // 切换到IO线程池执行耗时操作
            val result = withContext(Dispatchers.IO) {
                logToScreen("Executing network request on " + threadName() + "...")
                delay(2000)
                "Network data loaded"
            } // 执行完毕后，自动切回Main线程

            logToScreen("Back on " + threadName())
            logToScreen("请求结果: " + result)
        }
    }

    /**
     * [Job] & Cancellation: 控制和取消协程。
     * - `launch`返回一个`Job`对象，代表这个协程。我们可以用它来取消协程。
     * - 协程的取消是协作式的。代码必须通过检查`isActive`状态或调用`yield()`/`delay()`等可取消的挂起函数来响应取消请求。
     */
    private fun handleCancellation() {
        logToScreen("---" + "Cancellation Demo" + "---", true)
        if (runningJob == null || !runningJob!!.isActive) {
            logToScreen("启动一个每秒计数的耗时任务 (5秒后再次点击可取消)")
            runningJob = lifecycleScope.launch(Dispatchers.Default) {
                try {
                    for (i in 1..10) {
                        if (!isActive) break // 检查协程是否已被取消
                        logToScreen("Counting: " + i + " on " + threadName() + "...")
                        delay(1000)
                    }
                    logToScreen("计数任务正常完成。")
                } catch (e: CancellationException) {
                    logToScreen("计数任务被成功取消！")
                }
            }
        } else {
            logToScreen("正在取消计数任务...")
            runningJob?.cancel() // 发出取消请求
            runningJob = null
        }
    }

    /**
     * Exception Handling: 捕获协程中的异常。
     * - `try-catch`块可以像普通代码一样在协程中使用。
     * - `CoroutineExceptionHandler`是一个更高级的机制，可以作为上下文元素安装，用于捕获未被处理的异常，常用于全局日志记录或错误上报。
     */
    private fun handleException() {
        logToScreen("---" + "Exception Handling Demo" + "---", true)
        val handler = CoroutineExceptionHandler { _, exception ->
            // 这个handler在主线程被调用，可以安全更新UI
            logToScreen("CoroutineExceptionHandler 捕获到异常: " + exception.message)
        }

        // 启动一个会抛出异常的协程
        lifecycleScope.launch(handler) {
            logToScreen("启动一个即将失败的任务...")
            delay(1000)
            throw RuntimeException("网络连接超时!")
        }
    }
    
    /**
     * [lifecycleScope]: 生命周期感知的协程作用域。
     * - 这是`androidx.lifecycle:lifecycle-runtime-ktx`库提供的扩展属性。
     * - 在`lifecycleScope`中启动的协程会在Activity/Fragment的`onDestroy`事件发生时自动被取消。
     * - 这是在UI层启动协程的首选方式，能有效防止内存泄漏和不必要的工作。
     */
    private fun handleLifecycleScope() {
        logToScreen("---" + "LifecycleScope Demo" + "---", true)
        logToScreen("一个与Activity生命周期绑定的协程已启动。")
        logToScreen("它将在10秒后完成。如果在完成前退出此页面(Activity销毁)，它将被自动取消。")
        
        this.lifecycleScope.launch {
            Log.d("CoroutinesActivity", "LifecycleScope coroutine started.")
            try {
                delay(10000)
                Log.d("CoroutinesActivity", "LifecycleScope coroutine finished successfully.")
                logToScreen("LifecycleScope协程正常结束。", false)
            } catch (e: CancellationException) {
                // 当Activity销毁时，会抛出此异常
                Log.d("CoroutinesActivity", "LifecycleScope coroutine was cancelled because Activity is being destroyed.")
            }
        }
    }

    // Helper function to log to both Logcat and the on-screen TextView
    private fun logToScreen(message: String, clearLog: Boolean = false) {
        Log.d("CoroutinesActivity", message)
        runOnUiThread {
            val currentTime = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())
            val logMessage = "$currentTime: $message\n"
            if (clearLog) {
                binding.tvLog.text = logMessage
            } else {
                binding.tvLog.append(logMessage)
            }
        }
    }

    private fun threadName(): String = Thread.currentThread().name

    override fun onDestroy() {
        super.onDestroy()
        // lifecycleScope会自动处理取消，但如果是手动创建的Scope，需要在这里取消
        // example: parentJob.cancel()
    }
}
