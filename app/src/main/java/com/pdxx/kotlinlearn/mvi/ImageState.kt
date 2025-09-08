package com.pdxx.kotlinlearn.mvi

import com.pdxx.kotlinlearn.mvi.model.ImageItem

/**
 * Step 2.3: 定义状态 (State)
 * State是UI的唯一数据源。它是一个不可变的数据类（data class），包含了渲染UI所需的所有信息。
 * 当State发生变化时，View会观察到这个变化并更新自己。
 *
 * @param isLoading 是否正在进行首次加载
 * @param isRefreshing 是否正在下拉刷新
 * @param isLoadingMore 是否正在上拉加载更多
 * @param images 当前已加载的图片列表
 * @param error 加载过程中发生的错误信息，如果没有错误则为null
 * @param canLoadMore 是否还可以加载更多数据
 * @param page 当前的页码，用于加载更多
 */
data class ImageState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val images: List<ImageItem> = emptyList(),
    val error: String? = null,
    val canLoadMore: Boolean = true,
    val page: Int = 1
)
