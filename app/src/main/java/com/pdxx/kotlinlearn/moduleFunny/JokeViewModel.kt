package com.pdxx.kotlinlearn.moduleFunny

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pdxx.kotlinlearn.moduleFunny.model.Joke
import com.pdxx.kotlinlearn.net.KtRetrofit
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel: 在UI和数据源之间充当桥梁。
 * 它的核心职责是创建、管理并向UI层暴露PagingData的数据流。
 */
class JokeViewModel : ViewModel() {

    // 在实际项目中，Service实例通常通过依赖注入（如Koin, Hilt）传入ViewModel的构造函数中。
    // private val jokeService: IJokeService
    // 这里为了简单起见，我们直接创建它。
    private val jokeService: IJokeService = KtRetrofit.initConfig("https://api.apiopen.top/")
        .getService(IJokeService::class.java)

    /**
     * PagingData的数据流。UI层（Activity/Fragment）将会观察这个Flow。
     * `PagingData`是分页数据的容器，它本身也是一个数据流，可以被RecyclerView的Adapter消费。
     */
    val jokesFlow: Flow<PagingData<Joke>> = createJokesFlow()

    private fun createJokesFlow(): Flow<PagingData<Joke>> {
        // 1. 创建一个Pager对象，它是构建PagingData流的入口。
        return Pager(
            // 2. 配置PagingConfig
            // 这是分页加载的核心配置。
            config = PagingConfig(
                pageSize = 20, // 每页加载的item数量
                prefetchDistance = 3, // 距离列表底部还有多少个item时开始预加载下一页
                initialLoadSize = 20, // 首次加载时加载的item数量，通常是pageSize的倍数
                enablePlaceholders = false // 是否启用占位符。如果为true，RecyclerView会显示未加载项的null视图
            ),

            // 3. 指定PagingSourceFactory
            // 这是一个lambda表达式，Paging库每次需要新的数据页时都会调用它来创建一个新的PagingSource实例。
            // 必须每次都创建新实例，以保证数据的刷新和一致性。
            pagingSourceFactory = { JokePagingSource(jokeService) }

        ).flow // 4. 从Pager获取Flow<PagingData<Joke>>
        .cachedIn(viewModelScope) // 5. 缓存数据流
        // `cachedIn`操作符至关重要。它将PagingData流缓存在指定的CoroutineScope（这里是viewModelScope）中。
        // 这使得数据在屏幕旋转等配置变更后得以幸存，避免了重复加载。
    }
}
