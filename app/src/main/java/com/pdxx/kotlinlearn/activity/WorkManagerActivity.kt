package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.work.UploadWorker
import java.util.concurrent.TimeUnit

/**
 * WorkManager 使用示例 Activity
 *
 * WorkManager 是 Android Jetpack 的一部分，用于管理可延期的后台任务。
 * 即使应用程序退出或设备重启，这些任务也能保证得到执行。
 *
 * 适用场景：
 * - 上传日志/数据
 * - 同步数据
 * - 处理图片
 */
class WorkManagerActivity : AppCompatActivity() {

    private lateinit var tvLog: TextView
    private val workManager by lazy { WorkManager.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)

        tvLog = findViewById(R.id.tv_log)

        findViewById<Button>(R.id.btn_one_time_work).setOnClickListener {
            startOneTimeWork()
        }

        findViewById<Button>(R.id.btn_periodic_work).setOnClickListener {
            startPeriodicWork()
        }

        findViewById<Button>(R.id.btn_cancel_work).setOnClickListener {
            cancelAllWork()
        }
    }

    /**
     * 启动一次性任务
     */
    private fun startOneTimeWork() {
        appendLog("准备启动一次性任务...")

        // 1. 构建约束条件 (可选)
        // 例如：只有在连接网络且充电时才执行
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // 需要网络连接
            // .setRequiresCharging(true) // 需要充电
            .build()

        // 2. 构建输入数据 (可选)
        val inputData = Data.Builder()
            .putString("key_input_data", "Hello World Image")
            .build()

        // 3. 构建 WorkRequest
        // OneTimeWorkRequest 用于一次性任务
        val uploadWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints) // 设置约束
            .setInputData(inputData) // 设置输入数据
            .addTag("upload_tag") // 设置 Tag，方便后续取消或查询
            // .setInitialDelay(10, TimeUnit.SECONDS) // 延迟 10 秒执行
            .build()

        // 4. 提交任务
        workManager.enqueue(uploadWorkRequest)

        // 5. 观察任务状态
        // getWorkInfoByIdLiveData 返回一个 LiveData，可以感知生命周期
        workManager.getWorkInfoByIdLiveData(uploadWorkRequest.id)
            .observe(this) { workInfo ->
                if (workInfo != null) {
                    val status = workInfo.state
                    val outputData = workInfo.outputData.getString(UploadWorker.KEY_OUTPUT_DATA)

                    appendLog("任务状态: $status")

                    if (status.isFinished) {
                        appendLog("任务结束，结果: $outputData")
                    }
                }
            }
    }

    /**
     * 启动周期性任务
     */
    private fun startPeriodicWork() {
        appendLog("准备启动周期性任务...")

        // PeriodicWorkRequest 用于周期性任务
        // 注意：最小间隔时间为 15 分钟
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            UploadWorker::class.java,
            15, TimeUnit.MINUTES // 重复间隔
        )
            .addTag("periodic_upload")
            .build()

        // 提交任务
        // 使用 enqueueUniquePeriodicWork 可以避免重复添加相同的任务
        // ExistingPeriodicWorkPolicy.KEEP: 如果任务已存在，则保持原样，不替换
        // ExistingPeriodicWorkPolicy.REPLACE: 如果任务已存在，则取消旧的，替换为新的
        workManager.enqueueUniquePeriodicWork(
            "unique_periodic_upload",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )

        appendLog("周期性任务已提交 (最小间隔15分钟)")
        
        // 观察周期性任务状态
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this) { workInfo ->
                 if (workInfo != null) {
                     appendLog("周期任务状态: ${workInfo.state}")
                 }
            }
    }

    /**
     * 取消所有任务
     */
    private fun cancelAllWork() {
        workManager.cancelAllWork()
        appendLog("已请求取消所有任务")
    }

    private fun appendLog(msg: String) {
        val currentText = tvLog.text.toString()
        val newText = "$msg\n$currentText"
        tvLog.text = newText
    }
}
