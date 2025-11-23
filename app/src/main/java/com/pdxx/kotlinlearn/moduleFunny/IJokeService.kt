package com.pdxx.kotlinlearn.moduleFunny

import com.pdxx.kotlinlearn.moduleFunny.model.JokeResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Service 接口，用于定义网络请求
 */
interface IJokeService {

    /**
     * 获取笑话列表 (视频类型)
     * @param page 请求的页码，从1开始
     * @param count 每页请求的数量
     * @return JokeResponse 包含笑话列表的响应体
     *
     * 注意：为了能被协程调用，这个函数必须被声明为 `suspend`
     */
    @GET("api/getHaoKanVideo")
    suspend fun getJokes(
        @Query("page") page: Int,
        @Query("size") count: Int
    ): JokeResponse
}
