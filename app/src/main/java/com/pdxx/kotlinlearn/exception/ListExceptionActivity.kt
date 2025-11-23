package com.pdxx.kotlinlearn.exception

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pdxx.kotlinlearn.R
import java.lang.ref.WeakReference
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.Executors

class ListExceptionActivity : AppCompatActivity() {

    private lateinit var tvLog: TextView
    private val logBuilder = StringBuilder()

    // 各种泄漏示例的变量
    private var leakHandler: Handler? = null
    private var timer: Timer? = null
    private var asyncTask: AsyncTask<*, *, *>? = null
    private var cursor: Cursor? = null
    private var broadcastReceiver: BroadcastReceiver? = null

    // 静态变量泄漏示例
    companion object {
        var staticActivity: ListExceptionActivity? = null
        val staticList = mutableListOf<Any>()
    }

    // 单例类泄漏示例
    object SingletonLeak {
        var context: Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_exception)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        tvLog = findViewById(R.id.tv_log)
    }

    private fun setupClickListeners() {
        // 1. Context使用不当
        findViewById<View>(R.id.btn_static_activity).setOnClickListener {
            demonstrateStaticActivityLeak()
        }

        findViewById<View>(R.id.btn_singleton_context).setOnClickListener {
            demonstrateSingletonContextLeak()
        }

        findViewById<View>(R.id.btn_inner_class_leak).setOnClickListener {
            demonstrateInnerClassLeak()
        }

        // 2. Handler/MessageQueue泄漏
        findViewById<View>(R.id.btn_handler_leak).setOnClickListener {
            demonstrateHandlerLeak()
        }

        findViewById<View>(R.id.btn_message_not_removed).setOnClickListener {
            demonstrateMessageNotRemoved()
        }

        // 3. 线程/异步任务泄漏
        findViewById<View>(R.id.btn_thread_leak).setOnClickListener {
            demonstrateThreadLeak()
        }

        findViewById<View>(R.id.btn_async_task_leak).setOnClickListener {
            demonstrateAsyncTaskLeak()
        }

        findViewById<View>(R.id.btn_timer_leak).setOnClickListener {
            demonstrateTimerLeak()
        }

        findViewById<View>(R.id.btn_rxjava_leak).setOnClickListener {
            demonstrateRxJavaLeak()
        }

        // 4. 资源未关闭
        findViewById<View>(R.id.btn_cursor_leak).setOnClickListener {
            demonstrateCursorLeak()
        }

        findViewById<View>(R.id.btn_broadcast_leak).setOnClickListener {
            demonstrateBroadcastLeak()
        }

        // 5. 静态集合泄漏
        findViewById<View>(R.id.btn_static_collection).setOnClickListener {
            demonstrateStaticCollectionLeak()
        }

        // 6. WebView泄漏
        findViewById<View>(R.id.btn_webview_leak).setOnClickListener {
            demonstrateWebViewLeak()
        }

        // 7. Adapter泄漏
        findViewById<View>(R.id.btn_adapter_leak).setOnClickListener {
            demonstrateAdapterLeak()
        }

        // 8. 监听器未解绑
        findViewById<View>(R.id.btn_listener_leak).setOnClickListener {
            demonstrateListenerLeak()
        }

        findViewById<View>(R.id.btn_eventbus_leak).setOnClickListener {
            demonstrateEventBusLeak()
        }

        // 清空日志
        findViewById<View>(R.id.btn_clear_log).setOnClickListener {
            clearLog()
        }
    }

    // 1.1 静态变量持有Activity引用
    private fun demonstrateStaticActivityLeak() {
        log("=== 静态变量持有Activity引用示例 ===")
        log("错误示例: static Activity mActivity = this;")
        staticActivity = this // 这会导致内存泄漏
        log("✓ 当前Activity已被静态变量持有")
        log("⚠️ 即使Activity被销毁，静态变量仍然持有引用，导致无法被GC回收")
        log("✅ 解决方案: 在onDestroy()中设置 staticActivity = null")
    }

    // 1.2 单例类中持有Context引用
    private fun demonstrateSingletonContextLeak() {
        log("=== 单例类中持有Context引用示例 ===")
        log("错误示例: Singleton.context = activityContext;")
        SingletonLeak.context = this // 使用Activity Context会导致泄漏
        log("✓ 单例类已持有Activity Context引用")
        log("⚠️ 应该使用Application Context: Singleton.context = applicationContext")
        log("✅ 解决方案: 使用ApplicationContext而不是Activity Context")
    }

    // 1.3 内部类持有外部类引用
    private fun demonstrateInnerClassLeak() {
        log("=== 内部类持有外部类引用示例 ===")
        log("错误示例: 非静态内部类或匿名类")

        // 错误示例: 非静态内部类
        val innerClass = object : Runnable {
            override fun run() {
                // 这个匿名类会持有外部Activity的引用
                log("内部类执行中...")
            }
        }

        Thread(innerClass).start()
        log("✓ 创建了持有Activity引用的匿名内部类")
        log("⚠️ 如果内部类在后台线程运行，Activity销毁时线程仍在运行，会导致泄漏")
        log("✅ 解决方案: 使用static内部类 + WeakReference<Context>")
    }

    // 2.1 Handler非静态内部类泄漏
    private fun demonstrateHandlerLeak() {
        log("=== Handler非静态内部类泄漏示例 ===")

        // 错误示例: 非静态Handler
        leakHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                // 这个Handler会持有Activity引用
                log("Handler处理消息: ${msg.what}")
            }
        }

        leakHandler?.sendEmptyMessage(1)
        log("✓ 创建了非静态Handler")
        log("⚠️ Handler持有Activity引用，如果Message未及时处理会导致泄漏")
        log("✅ 解决方案: 使用静态Handler + WeakReference")
    }

    // 2.2 Message未及时移除
    private fun demonstrateMessageNotRemoved() {
        log("=== Message未及时移除示例 ===")

        val handler = Handler(Looper.getMainLooper())
        // 发送延迟消息但未移除
        handler.postDelayed({
            log("延迟消息执行")
        }, 30000) // 30秒后执行

        log("✓ 发送了30秒后执行的延迟消息")
        log("⚠️ 如果Activity在30秒内销毁，消息仍然持有Activity引用")
        log("✅ 解决方案: 在onDestroy()中调用 handler.removeCallbacksAndMessages(null)")
    }

    // 3.1 Thread未停止
    private fun demonstrateThreadLeak() {
        log("=== Thread未停止示例 ===")

        Thread {
            while (true) {
                try {
                    Thread.sleep(1000)
                    log("后台线程运行中...")
                } catch (e: InterruptedException) {
                    break
                }
            }
        }.start()

        log("✓ 启动了无限循环的后台线程")
        log("⚠️ 线程持有Activity引用且未停止，导致Activity无法回收")
        log("✅ 解决方案: 使用volatile标志位控制线程退出，在onDestroy()中停止线程")
    }

    // 3.2 AsyncTask泄漏
    private fun demonstrateAsyncTaskLeak() {
        log("=== AsyncTask泄漏示例 ===")

        asyncTask = object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                // 模拟长时间任务
                Thread.sleep(10000)
                return null
            }

            override fun onPostExecute(result: Void?) {
                log("AsyncTask执行完成")
            }
        }

        asyncTask?.execute()
        log("✓ 启动了10秒的AsyncTask")
        log("⚠️ AsyncTask持有Activity引用，如果Activity在任务完成前销毁会导致泄漏")
        log("✅ 解决方案: 在onDestroy()中调用asyncTask.cancel(true)")
    }

    // 3.3 Timer未取消
    private fun demonstrateTimerLeak() {
        log("=== Timer未取消示例 ===")

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                log("Timer任务执行")
            }
        }, 0, 1000) // 每秒执行一次

        log("✓ 启动了每秒执行的Timer")
        log("⚠️ Timer持有Activity引用且未取消，导致泄漏")
        log("✅ 解决方案: 在onDestroy()中调用timer.cancel()")
    }

    // 3.4 RxJava未dispose
    private fun demonstrateRxJavaLeak() {
        log("=== RxJava未dispose示例 ===")
        log("模拟RxJava订阅未取消的情况")
        log("错误示例: Observable.subscribe() 但未保存Disposable")
        log("⚠️ 订阅者持有Activity引用，如果未dispose会导致泄漏")
        log("✅ 解决方案: 使用CompositeDisposable管理订阅，在onDestroy()中clear()")
    }

    // 4.1 Cursor未关闭
    private fun demonstrateCursorLeak() {
        log("=== Cursor未关闭示例 ===")
        log("错误示例: 查询数据库后未关闭Cursor")

        // 模拟Cursor泄漏（这里只是演示，实际需要数据库操作）
        log("✓ 模拟了未关闭的Cursor")
        log("⚠️ Cursor持有数据库连接，未关闭会导致资源泄漏")
        log("✅ 解决方案: 使用use{}函数或try-finally确保Cursor关闭")
    }

    // 4.2 广播未注销
    private fun demonstrateBroadcastLeak() {
        log("=== 广播未注销示例 ===")

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                log("接收到广播")
            }
        }

        val filter = IntentFilter("TEST_ACTION")
        registerReceiver(broadcastReceiver, filter)
        log("✓ 注册了广播接收器")
        log("⚠️ 广播接收器持有Activity引用，未注销会导致泄漏")
        log("✅ 解决方案: 在onDestroy()中调用unregisterReceiver()")
    }

    // 5. 静态集合持有对象
    private fun demonstrateStaticCollectionLeak() {
        log("=== 静态集合持有对象示例 ===")

        // 向静态集合添加大量对象
        for (i in 1..100) {
            staticList.add("Object_$i")
        }
        staticList.add(this) // 甚至添加Activity本身

        log("✓ 向静态集合添加了101个对象（包括Activity）")
        log("⚠️ 静态集合持有对象引用，导致这些对象无法被GC回收")
        log("✅ 解决方案: 及时清理静态集合，或使用WeakReference")
    }

    // 6. WebView泄漏
    private fun demonstrateWebViewLeak() {
        log("=== WebView泄漏示例 ===")
        log("错误示例: 在Activity中直接创建WebView")
        log("⚠️ WebView内部复杂，直接创建容易导致泄漏")
        log("✅ 解决方案1: 使用ApplicationContext创建WebView")
        log("✅ 解决方案2: 在onDestroy()中调用webView.destroy()")
        log("✅ 解决方案3: 使用独立的Fragment管理WebView")
    }

    // 7. Adapter持有Activity引用
    private fun demonstrateAdapterLeak() {
        log("=== Adapter持有Activity引用示例 ===")
        log("错误示例: Adapter直接持有Activity引用")
        log("⚠️ Adapter持有Activity引用，如果RecyclerView未销毁会导致泄漏")
        log("✅ 解决方案: 使用弱引用或通过接口回调")
    }

    // 8.1 匿名监听器未解绑
    private fun demonstrateListenerLeak() {
        log("=== 匿名监听器未解绑示例 ===")

        val button = findViewById<View>(R.id.btn_listener_leak)
        // 匿名监听器会持有Activity引用
        button.setOnClickListener {
            log("按钮点击监听器执行")
        }

        log("✓ 设置了匿名点击监听器")
        log("⚠️ 匿名监听器持有Activity引用")
        log("✅ 解决方案: 在onDestroy()中设置button.setOnClickListener(null)")
    }

    // 8.2 EventBus未注销
    private fun demonstrateEventBusLeak() {
        log("=== EventBus未注销示例 ===")
        log("错误示例: EventBus.getDefault().register(this) 但未注销")
        log("⚠️ EventBus持有订阅者引用，未注销会导致泄漏")
        log("✅ 解决方案: 在onDestroy()中调用EventBus.getDefault().unregister(this)")
        log("✅ 替代方案: 使用Lifecycle-aware的EventBus库")
    }

    // 正确的Handler示例（静态内部类 + WeakReference）
    private class SafeHandler(activity: ListExceptionActivity) : Handler(Looper.getMainLooper()) {
        private val weakActivity = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            val activity = weakActivity.get()
            if (activity != null) {
                activity.log("安全Handler处理消息: ${msg.what}")
            }
        }
    }

    private fun log(message: String) {
        runOnUiThread {
            logBuilder.append("${System.currentTimeMillis()}: $message\n")
            tvLog.text = logBuilder.toString()
            // 自动滚动到底部
            tvLog.post {
                val scrollAmount = tvLog.layout.getLineTop(tvLog.lineCount) - tvLog.height
                if (scrollAmount > 0) {
                    tvLog.scrollTo(0, scrollAmount)
                } else {
                    tvLog.scrollTo(0, 0)
                }
            }
            Log.d("MemoryLeakDemo", message)
        }
    }

    private fun clearLog() {
        logBuilder.clear()
        tvLog.text = "日志已清空"
    }

    override fun onDestroy() {
        super.onDestroy()

        // 清理各种资源，防止泄漏
//        leakHandler?.removeCallbacksAndMessages(null)
//        timer?.cancel()
//        asyncTask?.cancel(true)
//        cursor?.close()
//
//        broadcastReceiver?.let {
//            try {
//                unregisterReceiver(it)
//            } catch (e: Exception) {
//                // 忽略未注册的异常
//            }
//        }
//
//        // 清理静态引用
//        staticActivity = null
//        SingletonLeak.context = null
//        staticList.clear()

        log("Activity销毁，已清理资源")
    }
}