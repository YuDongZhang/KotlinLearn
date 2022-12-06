package com.cainiaowo.netdemo.okhttp.config

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 重试请求网络拦截器
 */
class RetryInterceptor(private val maxRetry: Int = 0) : Interceptor {
    /**
     * 已经重试的次数
     */
    private var retriedNum: Int = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        Log.d("RetryInterceptor", "intercept 19行:retriedNum=$retriedNum")
        var response = chain.proceed(request)
        while (!response.isSuccessful && retriedNum < maxRetry) {
            retriedNum++
            Log.d("RetryInterceptor", "intercept 23行:retriedNum=$retriedNum")
            response = chain.proceed(request)
        }
        return response
    }
}