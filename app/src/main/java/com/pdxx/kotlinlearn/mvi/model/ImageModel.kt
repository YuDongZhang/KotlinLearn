package com.pdxx.kotlinlearn.mvi.model

/**
 * Step 2.1: 定义数据模型 (Data Models)
 * 这些是Kotlin数据类，用于让GSON库将API返回的JSON字符串自动解析成我们可用的对象。
 * 它们的结构必须严格匹配JSON的结构。
 */

/**
 * 顶级响应对象，对应整个JSON
 */
data class ImageResponse(
    val code: Int,
    val message: String,
    val result: ImageResult
)

/**
 * "result" 字段对应的对象
 */
data class ImageResult(
    val total: Int,
    val list: List<ImageItem>
)

/**
 * "list" 数组中的单个图片对象
 */
data class ImageItem(
    val id: Int,
    val title: String,
    val url: String,
    val type: String
)
