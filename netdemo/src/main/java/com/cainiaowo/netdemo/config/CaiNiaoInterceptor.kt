package com.cainiaowo.netdemo.okhttp.config

import com.blankj.utilcode.util.*
import com.cainiaowo.netdemo.config.NET_CONFIG_APPID
import com.cainiaowo.netdemo.config.NET_CONFIG_APPKEY
import com.cainiaowo.netdemo.config.SP_KEY_USER_TOKEN
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.CacheControl
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

/**
 * 项目相关，添加公共Header的拦截器
 */
class CaiNiaoInterceptor : Interceptor {

    companion object {
        private val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .create()
        private val mapType = object : TypeToken<Map<String, Any>>() {}.type
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()

        // 附加的公共headers，封装clientInfo,deviceInfo等。也可以在post请求中，自定义封装headers的字段体内容
        // 注意这里，服务器用于校验的字段，只能是以下的字段内容，可以缺失，但不能额外添加，因为服务端未做处理
        val attachHeaders = mutableListOf<Pair<String, String>>(
            "appid" to NET_CONFIG_APPID,
            "platform" to "android", // 如果重复请求，可能会报重复签名错误，yapi 平台标记则不会
            "timestamp" to System.currentTimeMillis().toString(),

            "brand" to DeviceUtils.getManufacturer(),
            "model" to DeviceUtils.getModel(),
            "uuid" to DeviceUtils.getUniqueDeviceId(),
            "network" to NetworkUtils.getNetworkType().name,
            "system" to DeviceUtils.getSDKVersionName(),

            "version" to AppUtils.getAppVersionName()
        )
        // token仅在有值的时候才传递，
        val tokenstr = ""
        val localToken = SPStaticUtils.getString(SP_KEY_USER_TOKEN, tokenstr)
        if (localToken.isNotEmpty()) {
            attachHeaders.add("token" to localToken)
        }
        val signHeaders = mutableListOf<Pair<String, String>>()
        signHeaders.addAll(attachHeaders)
        // get的请求，参数 , 参数拼装
        if (originRequest.method == "GET") {
            originRequest.url.queryParameterNames.forEach { key ->
                signHeaders.add(key to (originRequest.url.queryParameter(key) ?: ""))
            }
        }
        // post的请求 formBody形式，或json形式的，都需要将内部的字段，遍历出来，参与sign的计算
        val requestBody = originRequest.body
        if (originRequest.method == "POST") {
            // formBody
            if (requestBody is FormBody) {
                for (i in 0 until requestBody.size) {
                    signHeaders.add(requestBody.name(i) to requestBody.value(i))
                }
            }
            // json的body 需要将requestBody反序列化为json转为map application/json
            if (requestBody?.contentType()?.type == "application" && requestBody.contentType()?.subtype == "json") {
                kotlin.runCatching {
                    val buffer = Buffer()
                    requestBody.writeTo(buffer)
                    buffer.readByteString().utf8()
                }.onSuccess {
                    val map = gson.fromJson<Map<String, Any>>(it, mapType)
                    map.forEach { entry ->
                        // FIXME: value 目前json单层级
                        signHeaders.add(entry.key to entry.value.toString())
                    }
                }
            }
        }

        // TODO 算法：都必须是非空参数  sign = MD5（ascii排序后的 headers及params的key=value拼接&后，
        //  最后拼接appkey和value）//32位的大写,
        val signValue = signHeaders
            .sortedBy { it.first }//前面的it.first就是前面的key , 前面的key用 ask码排序
            .joinToString("&") { "${it.first}=${it.second}" }//就是一个语法糖 , key和value 用&就行拼接
            .plus("&appkey=$NET_CONFIG_APPKEY")//最后加上这个参数

        val newBuilder = originRequest.newBuilder()
            .cacheControl(CacheControl.FORCE_NETWORK)
        attachHeaders.forEach { newBuilder.header(it.first, it.second) }
        newBuilder.header("sign", EncryptUtils.encryptMD5ToString(signValue))//最后sign加入到请求头里面,md5加密

        if (originRequest.method == "POST" && requestBody != null) {
            newBuilder.post(requestBody)
        } else if (originRequest.method == "GET") {
            newBuilder.get()
        }
        return chain.proceed(newBuilder.build())
    }
}