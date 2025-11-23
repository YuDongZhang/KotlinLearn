package com.pdxx.kotlinlearn.moduleFunny

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pdxx.kotlinlearn.moduleFunny.model.Joke

/**
 * PagingSource: Paging 3的核心，定义了如何从数据源加载分页数据。
 *
 * - 第一个泛型参数 `Int`: Key的类型。由于我们的API使用页码(page number)来分页，所以这里使用Int。
 * - 第二个泛型参数 `Joke`: Value的类型。即列表中每个item的数据类型。
 */
class JokePagingSource(private val jokeService: IJokeService) : PagingSource<Int, Joke>() {

    /**
     * 这是PagingSource的核心函数，用于执行实际的数据加载。
     * 它是一个挂起函数，因此可以在这里安全地执行异步操作（如网络请求）。
     *
     * @param params `LoadParams<Key>` 对象，包含了加载所需的信息：
     *      - `params.key`: 当前需要加载的页的Key。如果是首次加载，key为null。
     *      - `params.loadSize`: 需要加载的数据量。
     *
     * @return `LoadResult<Key, Value>` 加载结果。
     *      - `LoadResult.Page`: 加载成功时返回。包含了数据列表、上一页的Key和下一页的Key。
     *      - `LoadResult.Error`: 加d载失败时返回。包含了异常信息。
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Joke> {
        return try {
            // 1. 获取当前页码。如果是初次加载，params.key为null，我们手动指定第一页为 1。
            val currentPage = params.key ?: 1
            // 定义每页加载的数量
            val pageSize = 20

            // 2. 调用Retrofit Service中的方法，执行网络请求
            val response = jokeService.getJokes(page = currentPage, count = pageSize)
            val jokes = response.result.list

            // 3. 计算上一页和下一页的Key
            // 如果当前页是第一页，那么就没有上一页，prevKey为null
            val prevKey = if (currentPage > 1) currentPage - 1 else null
            // 如果API返回的数据不为空，则下一页是当前页+1；如果为空，说明没有更多数据了，nextKey为null
            val nextKey = if (jokes.isNotEmpty()) currentPage + 1 else null

            // 4. 构建LoadResult.Page对象并返回
            LoadResult.Page(
                data = jokes,       // 当前页的数据
                prevKey = prevKey,  // 上一页的Key
                nextKey = nextKey   // 下一页的Key
            )

        } catch (e: Exception) {
            // 5. 如果发生异常（如网络错误），则返回LoadResult.Error
            LoadResult.Error(e)
        }
    }

    /**
     * 当数据需要刷新时（例如，下拉刷新），此方法用于确定从哪里开始加载。
     * Paging库会调用这个方法来获取初始加载的Key。
     *
     * @param state `PagingState` 包含了已加载分页数据的信息，例如最近访问的位置。
     *
     * @return 返回用于`load()`方法的新key。返回null会重新从初始页开始加载。
     */
    override fun getRefreshKey(state: PagingState<Int, Joke>): Int? {
        // 在这个例子中，我们希望刷新时总是从第一页重新开始。
        // 一个更复杂的实现可以是：
        // return state.anchorPosition?.let { anchorPosition ->
        //     state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) 
        //         ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        // }
        return null
    }
}
