package com.pdxx.kotlinlearn.mvi

import com.pdxx.kotlinlearn.mvi.model.ImageResponse

/**
 * Step 4: 创建数据仓库 (Repository)
 * Repository层负责处理数据操作。它将ViewModel与具体的数据来源（网络、数据库等）隔离开来。
 * 这使得我们可以轻松地替换或组合数据源，而不影响ViewModel。
 */
class ImageRepository(private val apiService: IImageService) {

    /**
     * 从API获取图片列表。
     * 这是一个挂起函数，因为它执行的是网络请求。
     *
     * @param page 要获取的页码
     * @param size 每页的数量
     * @return 返回API的响应结果
     */
    suspend fun getImages(page: Int, size: Int): ImageResponse {
        return apiService.getImages(page, size)
    }
}
