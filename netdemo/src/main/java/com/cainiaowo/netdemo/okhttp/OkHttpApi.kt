package com.cainiaowo.netdemo.okhttp

import androidx.collection.SimpleArrayMap
import com.cainiaowo.netdemo.okhttp.config.CaiNiaoInterceptor
import com.cainiaowo.netdemo.okhttp.config.KtHttpLogInterceptor
import com.cainiaowo.netdemo.okhttp.config.LocalCookieJar
import com.cainiaowo.netdemo.okhttp.config.RetryInterceptor
import com.cainiaowo.netdemo.okhttp.support.IHttpCallback
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * IHttpApi的实现类：使用OkHttp
 * 单例模式
 */
class OkHttpApi private constructor() : IHttpApi {

    /**
     * 最大重试次数
     */
    var maxRetry = 0

    /**
     * 存储请求,控制请求可取消
     */
    private val callMap = SimpleArrayMap<Any, Call>()

    // OkHttpClient
    private val defaultClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)       // 完整请求超时时长,从发起到接收返回数据,默认值0,不限定
        .connectTimeout(10, TimeUnit.SECONDS)   // 与服务器建立连接的时长,默认10s
        .readTimeout(10, TimeUnit.SECONDS)      // 读取服务器返回数据的时长
        .writeTimeout(10, TimeUnit.SECONDS)     // 向服务器写入数据的时长,默认10s
        .retryOnConnectionFailure(true) // 重连
        .followRedirects(false)     // 重定向
        .cache(Cache(File("sdcard/cache", "okhttp"), 1024L))    // 网络请求的缓存数据
//        .cookieJar(CookieJar.NO_COOKIES)      // 实现 接口CookieJar的实现类,以此来设置cookie相关的信息,NO_COOKIES只适合无COOKIE使用
        .cookieJar(LocalCookieJar())
        .addNetworkInterceptor(CaiNiaoInterceptor())    // 公共Header拦截器,放在打印日志拦截器前可以观察是否添加成功
        .addNetworkInterceptor(KtHttpLogInterceptor {
            logLevel(KtHttpLogInterceptor.LogLevel.BODY)
        })
        .addNetworkInterceptor(RetryInterceptor(maxRetry))
        .build()

    /**
     * mClient可以根据自己配置修改,defaultClient是OkHttpApi中默认配置的client
     */
    private var mClient = defaultClient

    fun getClient() = mClient

    /**
     * 配置自定义client
     */
    fun initClientConfig(client: OkHttpClient) {
        this.mClient = client
    }

    /**
     * 单例实现
     */
    companion object {
        @Volatile
        private var api: OkHttpApi? = null

        @Synchronized
        fun getInstance(): OkHttpApi {
            return api ?: OkHttpApi().also { api = it }
        }
    }

    override fun get(params: Map<String, Any>, urlStr: String, callback: IHttpCallback) {
        val urlBuilder = urlStr.toHttpUrl().newBuilder()
        params.forEach { entry ->
            urlBuilder.addEncodedQueryParameter(entry.key, entry.value.toString())
        }

        val request = Request.Builder()
            .get()
            .tag(params)//用于tag标记方便取消
            .url(urlBuilder.build())
            .cacheControl(CacheControl.FORCE_NETWORK) // 强制使用网络更新
            .build()
        val newCall = mClient.newCall(request)
        // 存储请求,用于取消
        callMap.put(request.tag(), newCall)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string())
            }
        })
    }

    override fun post(body: Any, urlStr: String, callback: IHttpCallback) {
        val reqBody = Gson().toJson(body).toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .post(reqBody)
            .tag(body)
            .url(urlStr)
            .build()
        val newCall = mClient.newCall(request)
        // 存储请求,用于取消
        callMap.put(request.tag(), newCall)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string())
            }
        })
    }

    /**
     * 取消网络请求,tag就是每次请求的id标记,也就是请求的传参
     */
    override fun cancelRequest(tag: Any) {
        callMap[tag]?.cancel()
    }

    /**
     * 取消所有网络请求
     */
    override fun cancelAllRequest() {
        for (i in 0 until callMap.size()) {
            callMap.get(callMap.keyAt(i))?.cancel()
        }
    }


    /**
     * 使用协程形式的get请求，使用runblocking，也可以使用suspend修饰
     */
//    fun get(params: Map<String, Any>, urlStr: String) = runBlocking {
//        val urlBuilder = urlStr.toHttpUrl().newBuilder()
//        params.forEach { entry ->
//            urlBuilder.addEncodedQueryParameter(entry.key, entry.value.toString())
//        }
//
//        val request = Request.Builder()
//            .get()
//            .tag(params)
//            .url(urlBuilder.build())
//            .cacheControl(CacheControl.FORCE_NETWORK)
//            .build()
//        val newCall = mClient.newCall(request)
//
//        //存储请求，用户取消
//        callMap.put(request.tag(), newCall)
//        newCall.call()
    //  得到请求的结果会返回给runbolocking  get()得到的就是 response
//    }
//
//    /**
//     * 自定义扩展函数，扩展okhttp的call的异步执行方式，结合协程，返回dataresult的数据响应
//     */
//    private suspend fun Call.call(async: Boolean = true): Response {
//        return suspendCancellableCoroutine { continuation ->
//            if (async) {
//                enqueue(object : Callback {
//                    override fun onFailure(call: Call, e: IOException) {
//                        //避免不必要的冗余调用
//                        if (continuation.isCancelled) return
//                        //continuation.resumeWithException(e)
//                    }
//
//                    override fun onResponse(call: Call, response: Response) {
//                        continuation.resume(response)
//                    }
//                })
//            } else {
//                continuation.resume(execute())
//            }
//            //协程取消的时候网络请求取消
//            continuation.invokeOnCancellation {
//                try {
//                    cancel()
//                } catch (ex: Exception) {
//                    ex.printStackTrace()
//                }
//            }
//        }
//    }
}