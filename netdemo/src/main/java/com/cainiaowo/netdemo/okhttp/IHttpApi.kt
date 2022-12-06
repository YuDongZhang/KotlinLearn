package com.cainiaowo.netdemo.okhttp

import com.cainiaowo.netdemo.okhttp.support.IHttpCallback

/**
 * 网络请求统一接口
 */
interface IHttpApi {
    /**
     * 抽象的Http的get请求封装,异步请求
     */
    fun get(params: Map<String, Any>, path: String, callback: IHttpCallback)

    /**
     * 抽象的Http的get请求封装,同步请求
     */
    fun syncGet(params: Map<String, Any>, path: String): Any? = null

    /**
     * 抽象的Http的post请求封装,异步请求
     */
    fun post(body: Any, path: String, callback: IHttpCallback)

    /**
     * 抽象的Http的post请求封装,同步请求 , = null 也是一种 return
     */
    fun syncPost(body: Any, path: String): Any? = null

    /**
     * 取消请求
     */
    fun cancelRequest(tag: Any)

    /**
     * 取消所有请求
     */
    fun cancelAllRequest()
}