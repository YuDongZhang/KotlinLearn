package com.cainiaowo.netdemo.okhttp.config

import com.blankj.utilcode.util.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.CacheControl
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

/**
 * 项目相关，添加公共Header的拦截器
 *
 * 这个拦截器负责添加公共头、签名计算等。需要注意的地方包括：
 *
 * 是否正确处理了GET和POST请求的参数拼接。
 * 签名生成逻辑是否符合服务器要求，尤其是参数排序和加密方式。
 * 是否有潜在的空指针异常，例如当某些头部不存在时。
 */
class CaiNiaoInterceptor : Interceptor {

    companion object {
        private val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .create()
        private val mapType = object : TypeToken<Map<String, Any>>() {}.type
//        private val mapdtd = object : TypeToken<Map<String,Any>>() {}.type
    }

    /*
    静态成员：在 companion object 中定义的成员可以通过类名直接访问，类似于 Java 中的静态成员。
    工厂方法：可以定义工厂方法用于创建类的实例。
    常量：可以在其中定义常量，避免在多个实例中重复存储相同的数据。

    object : TypeToken<Map<String, Any>>() {}：这是创建一个继承自 TypeToken<Map<String, Any>> 的匿名内部类。
    由于是匿名内部类，Kotlin 允许我们直接创建一个该类型的实例。

     */

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
        val tokenstr = "ss"
        val localToken = SPStaticUtils.getString(SP_KEY_USER_TOKEN, tokenstr)
        if (localToken.isNotEmpty()) {
            attachHeaders.add("token" to localToken)
        }
        val signHeaders = mutableListOf<Pair<String, String>>()
        signHeaders.addAll(attachHeaders)
        // get的请求，参数 , 参数拼装 , get请求参数 都是在url中 key value
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
                    //这个是一个流的形式
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
            .joinToString("&") { "${it.first}=${it.second}" }//就是一个语法糖 , key和value 用&就行拼接 , 最后一个不会有
            .plus("&appkey=$NET_CONFIG_APPKEY")//最后加上这个参数

        //拦截器 拦截的是 originRequest 修改后拼装成为新的
        val newBuilder = originRequest.newBuilder()
            .cacheControl(CacheControl.FORCE_NETWORK)
        //把值添加到header里面
        attachHeaders.forEach { newBuilder.header(it.first, it.second) }
        //把sign 值加入
        newBuilder.header("sign", EncryptUtils.encryptMD5ToString(signValue))//最后sign加入到请求头里面,md5加密

        if (originRequest.method == "POST" && requestBody != null) {
            newBuilder.post(requestBody) //要加上去, 构建新的请求体的时候
        } else if (originRequest.method == "GET") {
            newBuilder.get()
        }
        return chain.proceed(newBuilder.build())
    }
}