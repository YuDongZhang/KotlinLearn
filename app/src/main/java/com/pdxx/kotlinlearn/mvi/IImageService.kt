package com.pdxx.kotlinlearn.mvi

import com.pdxx.kotlinlearn.mvi.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Step 3: 定义网络服务接口 (Network Service Interface)
 * 使用Retrofit定义用于获取图片列表的API端点。
 */
interface IImageService {

    /**
     * 获取图片列表
     * @param page 页码
     * @param size 每页数量
     * @return ImageResponse 包含图片列表的响应体
     */
    @GET("api/getImages")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ImageResponse
}
