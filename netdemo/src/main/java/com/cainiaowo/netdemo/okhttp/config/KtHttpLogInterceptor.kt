package com.cainiaowo.netdemo.okhttp.config

import android.util.Log
import okhttp3.*
import okio.Buffer
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*

/**
 * 记录OkHttp网络日志的拦截器
 * [block] 这种写法是将函数块作为默认参数传进来,并且默认值为null
 */

/*
(block: (KtHttpLogInterceptor.() -> Unit)? = null)构造函数参数，参数block是可选的，可以传入一个函数或者为null。
() -> Unit 表示这是一个不接收任何参数且无返回值的函数类型。
. 操作符之前的 KtHttpLogInterceptor 表示这个函数类型的接收者是 KtHttpLogInterceptor 类型的对象。
这种函数类型声明告诉编译器，接收一个以 KtHttpLogInterceptor 为接收者类型的函数作为参数。
函数类型 (KtHttpLogInterceptor.() -> Unit) 允许在其内部直接调用 KtHttpLogInterceptor 对象的方法或者访问其属性，而不需要显式地指定对象名。
 */
class KtHttpLogInterceptor(block: (KtHttpLogInterceptor.() -> Unit)? = null) : Interceptor {

    /**
     * 日志范围标记,默认不打印
     */
    private var logLevel: LogLevel = LogLevel.NONE

    /**
     * 日志等级颜色,默认是DEBUG的颜色
     */
    private var colorLevel: ColorLevel = ColorLevel.DEBUG

    private var logTag = TAG

    init {
        block?.invoke(this)
    //invoke 的时候就会运行   OkHttpApi
        //.addNetworkInterceptor(KtHttpLogInterceptor {
        //            //可以看到上面那个类里面的数据 ,这样可以直接传方法 , 显得B格高
        //            logLevel(KtHttpLogInterceptor.LogLevel.BODY)
        //        })
    }

    /**
     * 设置LogLevel
     */
    fun logLevel(level: LogLevel): KtHttpLogInterceptor {
        logLevel = level
        return this
    }

    /**
     * 设置colorLevel
     */
    fun colorLevel(level: ColorLevel): KtHttpLogInterceptor {
        colorLevel = level
        return this
    }

    /**
     * 设置TAG
     */
    fun logTag(tag: String): KtHttpLogInterceptor {
        logTag = tag
        return this
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        // 请求
        val request = chain.request()
        // 响应
        return kotlin.runCatching { chain.proceed(request) }
            .onFailure {
                it.printStackTrace()
                logIt(
                    it.message.toString(),
                    ColorLevel.ERROR
                )
            }.onSuccess { response ->
                if (logLevel == LogLevel.NONE) {
                    return response
                }
                // 记录请求日志
                logRequest(request, chain.connection())
                // 记录响应日志
                logResponse(response)
            }.getOrThrow()
    }

    /**
     * 记录请求日志
     */
    private fun logRequest(request: Request, connection: Connection?) {
        val sb = StringBuffer()
        sb.append(">>>>>>>>>>>>>>>>>>>>\n")
        when (logLevel) {
            LogLevel.NONE -> {
                /*do nothing*/
            }
            LogLevel.BASIC -> {
                logBasicReq(sb, request, connection)
            }
            LogLevel.HEADERS -> {
                logHeadersReq(sb, request, connection)
            }
            LogLevel.BODY -> {
                logBodyReq(sb, request, connection)
            }
        }
        sb.append("\n>>>>>>>>>>>>>>>>>>>>\n")
        logIt(sb)
    }

    // region requestLog
    private fun logBodyReq(sb: StringBuffer, request: Request, connection: Connection?) {
        logHeadersReq(sb, request, connection)
        // 读取RequestBody的内容
        val req = request.newBuilder().build()
        val sink = Buffer()
        req.body?.writeTo(sink)
        sb.append("RequestBody:${sink.readUtf8()}")
    }

    private fun logHeadersReq(sb: StringBuffer, request: Request, connection: Connection?) {
        logBasicReq(sb, request, connection)
        val headersStr = request.headers.joinToString("") { header ->
            "请求Header：{${header.first}=${header.second}}\n"
        }
        sb.append(headersStr)
    }

    private fun logBasicReq(sb: StringBuffer, request: Request, connection: Connection?) {
        sb.append(
            """
            请求method:${request.method}    url:${decodeUrlStr(request.url.toString())}
            tag:${request.tag()}        protocol:${connection?.protocol() ?: Protocol.HTTP_1_1}
            """.trimIndent() + "\n"
        )
    }
    // endregion

    /**
     * 记录响应日志
     */
    private fun logResponse(response: Response) {
        val sb = StringBuffer()
        sb.append("<<<<<<<<<<<<<<<<<<<<\n")
        when (logLevel) {
            LogLevel.NONE -> {
                /*do nothing*/
            }
            LogLevel.BASIC -> {
                logBasicResp(sb, response)
            }
            LogLevel.HEADERS -> {
                logHeadersResp(sb, response)
            }
            LogLevel.BODY -> {
                logBodyResp(sb, response)
            }
        }
        sb.append("<<<<<<<<<<<<<<<<<<<")
        logIt(sb, ColorLevel.INFO)
    }

    // region responseLog
    private fun logBodyResp(sb: StringBuffer, response: Response) {
        logHeadersResp(sb, response)
        // body.string会抛IO异常
        kotlin.runCatching {
            // peek类似于clone数据流,不能直接用原来的body的string流数据作为日志,因为会消费掉IO,
            // 所以使用peek
            val peekBody = response.peekBody(1024 * 1024)
            sb.append("ResponseBody:${peekBody.string()}\n")
        }.getOrNull()
    }

    private fun logHeadersResp(sb: StringBuffer, response: Response) {
        logBasicResp(sb, response)
        val headersStr = response.headers.joinToString("") { header ->
            "响应Header：{${header.first}=${header.second}}\n"
        }
        sb.append(headersStr)
    }

    private fun logBasicResp(sb: StringBuffer, response: Response) {
        sb.append("响应protocol:${response.protocol}\r code：${response.code}\r message：${response.message}\n")
            .append("响应request url：${decodeUrlStr(response.request.url.toString())}\n")
            .append(
                "响应sentRequestTime：${
                    toDateTimeStr(
                        response.sentRequestAtMillis,
                        MILLIS_PATTERN
                    )
                }\r\r\r 响应receivedResponseTime：${
                    toDateTimeStr(
                        response.receivedResponseAtMillis,
                        MILLIS_PATTERN
                    )
                }\n"
            )
    }
    // endregion

    /**
     * 对于url编码的String解码
     */
    private fun decodeUrlStr(url: String): String? {
        return kotlin.runCatching {
            URLDecoder.decode(url, "utf-8")
        }.onFailure { it.printStackTrace() }.getOrNull()
    }

    /**
     * 打印日志
     * [data] 需要打印的数据独享
     * [tempLevel] 便于临时调整打印color等级
     */
    private fun logIt(data: Any, tempLevel: ColorLevel? = null) {
        when (tempLevel ?: colorLevel) {
            ColorLevel.VERBOSE -> Log.v(logTag, data.toString())
            ColorLevel.DEBUG -> Log.d(logTag, data.toString())
            ColorLevel.INFO -> Log.i(logTag, data.toString())
            ColorLevel.WARN -> Log.w(logTag, data.toString())
            ColorLevel.ERROR -> Log.e(logTag, data.toString())
        }
    }


    companion object {
        /**
         * 默认TAG
         */
        private const val TAG = "<KtHttp>"

        /**
         * 时间格式化
         */
        const val MILLIS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSXXX"

        /**
         * 转化为格式化的时间字符串
         */
        fun toDateTimeStr(millis: Long, pattern: String): String {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(millis)
        }
    }

    /**
     * 打印日志的范围
     */
    enum class LogLevel {
        NONE,       // 不打印
        BASIC,      // 只打印行首,请求/响应
        HEADERS,    // 打印 请求/响应 的所有Header
        BODY,       // 打印 所有
    }

    /**
     * 不同Log等级对应的颜色,对应于Android Studio的v,d,i,w,e
     */
    enum class ColorLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
    }
}