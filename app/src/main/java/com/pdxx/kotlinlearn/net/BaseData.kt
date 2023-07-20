package com.pdxx.kotlinlearn.net

import androidx.annotation.Keep

@Keep
data class BaseData(
    val code: Int,  // 响应码
    val data: String?,  // 响应数据内容,返回的数据都是经过加密的String
    val result: String?    // 响应数据的结果描述
) {
    companion object {
        const val SERVER_CODE_FAILED = 404    // 接口请求响应业务处理 失败
        const val SERVER_CODE_SUCCESS = 200   // 接口请求响应业务处理 成功
    }

}