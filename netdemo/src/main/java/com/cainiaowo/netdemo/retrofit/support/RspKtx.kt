package com.hym.netdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cainiaowo.netdemo.retrofit.model.ApiResponse
import com.cainiaowo.netdemo.retrofit.model.DataResult
import com.cainiaowo.netdemo.retrofit.model.UNKNOWN_ERROR_CODE
import com.google.gson.Gson

import okhttp3.Response
import retrofit2.Call
import retrofit2.await
import retrofit2.awaitResponse
import java.io.IOException

/**
 * author: huangyaomian
 * created on: 2021/6/13 3:24 下午
 * description:okhttp3 的 response的扩展函数，属性
 */

//region okhttp3 相关扩展

/**
 * okHttp的Call执行异步，并转化为liveData可观察结果
 * 看最后的返回的值 , 就是一个 live
 */
inline fun <reified T> okhttp3.Call.toLiveData(): LiveData<T?> {
    val live = MutableLiveData<T?>()
    this.enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            live.postValue(null)
        }

        override fun onResponse(call: okhttp3.Call, response: Response) {
            if (response.isSuccessful) {
                //这个扩展函数在下面
                response.toEntity<T>()
            }
        }
    })
    return live
}

/**
 * 将Response的对象，转化为需要的对象类型，也就是将body.string转为entity
 * @return 返回需要的类型对象，可能为null，如果json解析失败的话
 *
 * reified 用了泛型又想知道具体的类型 ,( T::class.java) 就应该用这个来修饰
 * 上面用了这个 下面的才可以这样使用
 *
 * inline内联函数 , 会作为一个代码块嵌入到调用地方 , 而不是作为函数
 */
inline fun <reified T> Response.toEntity(): T? {
    if (!isSuccessful) return null
    //gson不允许我们将json对象采用String,所以单独处理   //类型判断
    if (T::class.java.isAssignableFrom(String::class.java)) {
        return kotlin.runCatching {
            this.body?.string() //如果 实体是个 string 直接返回 , 因为不支持解析
        }.getOrNull() as? T
    }
    return kotlin.runCatching {
        //这个地方才是真正的返回实体的类 解析后的
        Gson().fromJson(this.body?.string(), T::class.java)
    }.onFailure { e ->
        e.printStackTrace()
    }.getOrNull()
}
//endregion

//region retrofit 相关扩展
/**
 * retrofit 的 call 执行异步，并转化为livedata可观察结果
 */
fun <T: Any> Call<T>.toLivedata(): LiveData<T?> {
    val live = androidx.lifecycle.MutableLiveData<T>()
    this.enqueue(object : retrofit2.Callback<T>{
        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
           val value = if (response.isSuccessful){
               response.body()
           }else{
               null
           }
            live.postValue(value)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            live.postValue(null)
        }
    })
    return live
}

/**
 * 扩展retrofit的返回数据，调用await，并catch超时等异常
 * @return dataResult 返回格式为ApiResponse封装
 * 这个和下面的返回的数据的结构类型不一样
 */
suspend fun <T : Any> Call<T>.serverData(): DataResult<T> {
    var result: DataResult<T> = DataResult.Loading
    kotlin.runCatching {
        this.await()
    }.onFailure {
        result = DataResult.Error(RuntimeException(it))
    }.onSuccess {
        result = DataResult.Success(it)
    }
    return result
}

/**
 * 扩展retrofit的返回数据，调用await，并catch超时异常
 * @return ApiResponse 返回格式为ApiResponse 封装
 */
suspend fun <T: Any> Call<T>.serverRsp(): ApiResponse<T> {
    var result: ApiResponse<T>
    val response = kotlin.runCatching {
        this.awaitResponse()//内部的扩展函数
    }.onFailure {
        result = ApiResponse.Companion.create(UNKNOWN_ERROR_CODE,it)
    }.getOrThrow()
    result = ApiResponse.Companion.create(response)
    return result
}

//endregion