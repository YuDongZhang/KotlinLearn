package com.cainiaowo.netdemo.okhttp.support

/**
 * 网络请求的接口回调
 */
interface IHttpCallback {

    /**
     * 网络请求成功的回调
     * [data] 返回回调的数据结果
     */
    fun onSuccess(data: Any?) //加?  可能为null

    /**
     * 接口回调失败
     * [error] 错误信息
     */
    fun onFailed(error: Any?)
}