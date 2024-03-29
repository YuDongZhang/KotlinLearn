package com.cainiaowo.netdemo.retrofit.model

import retrofit2.Response

// region 数据响应封装方式一  //密封类  类似枚举
sealed class DataResult<out R> {
    /**
     * 成功状态的时候 , 类型的判断 比如 Success  Error 都是属于 DataResult
     * 因为都继承于 DataResult ,  : DataResult 就是继承的方式
     */
    data class Success<out T>(val data: T) : DataResult<T>()

    /**
     * 错误、失败状态的时候
     */
    data class Error(val exception: Exception) : DataResult<Nothing>()

    /**
     * 加载数据中
     */
    object Loading : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * 返回结果是Success且data非null
 */
val DataResult<*>.succeed
    get() = this is DataResult.Success && data != null
// endregion




// region 数据响应封装方式二 , 这个是用数据类来做的 , 其实和上面差不多 ,
//看一下 :  success  error  loading 都是继承于 success
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, "Resource Success")
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS, ERROR, LOADING
}
// endregion


// region 数据响应封装方式三
//这里接受的是实际的网络请求数据类型 泛型的模式
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                ApiErrorResponse(
                    response.code(),
                    response.errorBody()?.string() ?: response.message()
                )
            }
        }

        fun <T> create(errorCode: Int, error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(errorCode, error.message ?: "Unknown Error!")
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorCode: Int, val errorMessage: String) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

internal const val UNKNOWN_ERROR_CODE = -1  // 未知错误码
// endregion