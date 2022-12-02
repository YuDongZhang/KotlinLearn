package com.cainiaowo.netdemo.config

import android.util.Log
import okhttp3.*
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*

/**
 * 记录OkHttp网络日志的拦截器
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
        sb.append("RequestBody:${request.body.toString()}")
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