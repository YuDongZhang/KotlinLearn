package com.pdxx.kotlinlearn.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * 自定义 Worker 类
 *
 * WorkManager 的核心是 Worker 类。我们需要继承 Worker 类并重写 doWork() 方法。
 * doWork() 方法在后台线程上异步运行。
 *
 * 注意：
 * 1. 如果需要协程支持，可以继承 CoroutineWorker。
 * 2. 这里为了演示基础用法，使用了普通的 Worker。
 */
class UploadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val TAG = "UploadWorker"
        const val KEY_OUTPUT_DATA = "key_output_data"
    }

    /**
     * 执行后台任务
     *
     * @return Result 执行结果，可以是：
     * - Result.success()：任务成功完成
     * - Result.failure()：任务失败
     * - Result.retry()：任务失败，需要稍后重试
     */
    override fun doWork(): Result {
        // 获取输入数据
        val inputData = inputData.getString("key_input_data") ?: "No Input"
        Log.d(TAG, "开始执行任务: $inputData, 线程: ${Thread.currentThread().name}")

        return try {
            // 模拟耗时操作，例如上传文件
            // 注意：Worker 的 doWork 默认是在后台线程池中执行的，所以这里可以直接同步执行耗时操作
            // 如果是 CoroutineWorker，则可以使用 suspend 函数
            simulateUpload()

            Log.d(TAG, "任务执行成功")

            // 创建输出数据，传递给观察者
            val outputData = androidx.work.Data.Builder()
                .putString(KEY_OUTPUT_DATA, "上传完成: $inputData")
                .build()

            // 返回成功，并携带输出数据
            Result.success(outputData)
        } catch (e: Exception) {
            Log.e(TAG, "任务执行失败", e)
            // 返回失败
            Result.failure()
        }
    }

    private fun simulateUpload() {
        // 模拟进度
        for (i in 0..100 step 20) {
            Thread.sleep(500) // 模拟耗时 0.5秒
            Log.d(TAG, "上传进度: $i%")
            
            // 如果需要更新进度（WorkManager 2.3.0+），可以使用 setProgressAsync
            // setProgressAsync(workDataOf("progress" to i))
        }
    }
}
