package com.pdxx.kotlinlearn.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdxx.kotlinlearn.net.KtRetrofit
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * Step 5: 创建ViewModel
 * ViewModel是UI的“状态容器”和“逻辑处理器”。
 * 1. 接收View发来的Intent。
 * 2. 调用Repository处理业务逻辑。
 * 3. 根据结果更新State。
 * 4. 通过Flow将State暴露给View。
 */
class ImageViewModel : ViewModel() {

    // 用户意图的Channel。View通过这个Channel将用户的操作意图发送给ViewModel。
    // Channel可以缓存未被处理的intent，防止背压问题。
    val intentChannel = Channel<ImageIntent>(Channel.UNLIMITED)

    // 可变的、私有的StateFlow，用于内部更新UI状态。
    private val _state = MutableStateFlow<ImageState>(ImageState())
    // 不可变的、公开的StateFlow，用于UI层观察状态变化。
    val state: StateFlow<ImageState> = _state

    // Repository的实例，负责数据获取
    private val repository: ImageRepository

    init {
        // 初始化Retrofit和Repository
        val apiService = KtRetrofit.initConfig("https://api.apiopen.top/").getService(IImageService::class.java)
        repository = ImageRepository(apiService)

        // 启动一个协程来持续处理来自Channel的意图
        processIntents()
    }

    // 处理意图的函数
    private fun processIntents() {
        viewModelScope.launch {
            // 将Channel转换为Flow，并对每个意图进行处理
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is ImageIntent.LoadInitial -> loadImages(isRefresh = false)
                    is ImageIntent.Refresh -> loadImages(isRefresh = true)
                    is ImageIntent.LoadMore -> loadMoreImages()
                }
            }
        }
    }

    // 加载图片（用于首次加载和刷新）
    private fun loadImages(isRefresh: Boolean) {
        viewModelScope.launch {
            // 1. 根据是刷新还是首次加载，更新UI状态为加载中
            _state.value = if (isRefresh) {
                _state.value.copy(isRefreshing = true, error = null)
            } else {
                _state.value.copy(isLoading = true, error = null)
            }

            try {
                // 2. 调用Repository获取数据
                val pageToLoad = 1 // 首次加载和刷新都从第一页开始
                val response = repository.getImages(page = pageToLoad, size = 10)

                // 3. 检查响应是否成功
                if (response.code == 200) {
                    val images = response.result.list
                    // 4. 更新State为成功状态
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        images = images, // 用新数据替换旧数据
                        page = pageToLoad + 1, // 准备加载下一页
                        canLoadMore = images.isNotEmpty() // 如果返回数据不为空，则可以继续加载
                    )
                } else {
                    // 处理API返回的错误
                    throw Exception(response.message)
                }
            } catch (e: Exception) {
                // 5. 如果发生异常，更新State为错误状态
                _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    error = e.message ?: "An unknown error occurred"
                )
            }
        }
    }

    // 加载更多图片
    private fun loadMoreImages() {
        // 如果正在加载或没有更多数据了，则直接返回
        if (_state.value.isLoadingMore || !_state.value.canLoadMore) return

        viewModelScope.launch {
            // 1. 更新UI为“加载更多”状态
            _state.value = _state.value.copy(isLoadingMore = true, error = null)

            try {
                // 2. 调用Repository获取下一页数据
                val pageToLoad = _state.value.page
                val response = repository.getImages(page = pageToLoad, size = 10)

                if (response.code == 200) {
                    val newImages = response.result.list
                    // 3. 将新数据追加到现有列表后面
                    val currentImages = _state.value.images
                    _state.value = _state.value.copy(
                        isLoadingMore = false,
                        images = currentImages + newImages, // 追加新数据
                        page = pageToLoad + 1, // 页码+1
                        canLoadMore = newImages.isNotEmpty() // 检查是否还有更多数据
                    )
                } else {
                    throw Exception(response.message)
                }
            } catch (e: Exception) {
                // 4. 更新State为错误状态
                _state.value = _state.value.copy(
                    isLoadingMore = false,
                    error = e.message ?: "An unknown error occurred"
                )
            }
        }
    }
}
