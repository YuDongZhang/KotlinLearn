package com.pdxx.kotlinlearn.moduleFunny

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class BaseJokeRsp<T> (
    val code: Int?,
    val message: String?,
    val result:T?
){
    companion object {
        const val SERVER_CODE_FAILED = 0    // 接口请求响应业务处理 失败
        const val SERVER_CODE_SUCCESS = 1   // 接口请求响应业务处理 成功
    }
}

/**
 * 接口请求成功,但是业务返回code不是1的情况,crossinline使用后不能跳出整个函数块
 */
//@OptIn(ExperimentalContracts::class)
//inline fun BaseJokeRsp.onBizError(
//    crossinline block: (code: Int, message: String) -> Unit
//): BaseJokeRsp {
//    contract {
//        callsInPlace(block, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
//    }
//    if (code != BaseCaiNiaoRsp.SERVER_CODE_SUCCESS) {
//        block.invoke(code, message ?: "Error Message Null")
//    }
//    return this
//}

/**
 * 接口请求成功,且业务成功code == 1的情况
 * crossinline 加上之后 在 方法中return 不能返出代码块之外
 * reified T 这个地方就是具体的类型 , 看调用的地方
 */
//@OptIn(ExperimentalContracts::class)
//inline fun <reified T> BaseJokeRsp<R>.onBizOK(
//    crossinline action: (code: Int, data: T?, message: String?) -> Unit
//): BaseJokeRsp<R>{
//    contract {
//        callsInPlace(action, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
//    }
//
//    //这个是业务的处理的成功 , 处理成功之后的data ,toEntity<T>()是什么?
//    //com/cainiaowo/login/repo/LoginRepo.kt: onBizOK 传入泛型对应到这里 ,
//    //进入到 toEntity 进行解密
//    if (code == BaseJokeRsp.SERVER_CODE_SUCCESS) {
//        action.invoke(code, this.toEntity<T>(), message)
//    }
//    return this
//}
