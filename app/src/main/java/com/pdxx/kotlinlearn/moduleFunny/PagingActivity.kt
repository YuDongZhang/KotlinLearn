package com.pdxx.kotlinlearn.moduleFunny

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.databinding.ActivityPagingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * PagingActivity: Paging 3架构的UI展示层。
 * 它的职责是：
 * 1. 观察ViewModel中的PagingData数据流。
 * 2. 将数据提交给PagingDataAdapter。
 * 3. 监听加载状态，并更新UI（如显示/隐藏加载动画）。
 */
class PagingActivity : AppCompatActivity() {

    // 使用ViewBinding来访问视图
    private lateinit var binding: ActivityPagingBinding

    // 通过ViewModelProvider获取ViewModel实例
    private val viewModel: JokeViewModel by lazy {
        ViewModelProvider(this)[JokeViewModel::class.java]
    }

    // 创建Adapter的实例
    private val jokeAdapter = JokePagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observePagingData()
        observeLoadState()
    }

    /**
     * 设置RecyclerView
     */
    private fun setupRecyclerView() {
        binding.rvJokes.apply {
            layoutManager = LinearLayoutManager(this@PagingActivity)
            adapter = jokeAdapter
            // 也可以添加LoadStateAdapter来在列表底部显示加载动画或重试按钮
//             adapter = jokeAdapter.withLoadStateFooter(
//                 footer = YourLoadStateAdapter { jokeAdapter.retry() }
//             )
        }
    }

    /**
     * 观察并提交PagingData
     */
    private fun observePagingData() {
        // lifecycleScope.launch 会创建一个与Activity生命周期绑定的协程
        lifecycleScope.launch {
            // collectLatest 会观察Flow。当有新的PagingData发射时，它会取消之前的块并执行新的块。
            // 这是官方推荐的用法，可以防止在数据快速更新时出现问题。
            viewModel.jokesFlow.collectLatest {
                // submitData是一个挂起函数，它将新的PagingData提交给Adapter进行差异计算和UI更新。
                jokeAdapter.submitData(it)
            }
        }
    }

    /**
     * 观察加载状态以更新UI
     */
    private fun observeLoadState() {
        lifecycleScope.launch {
            // adapter.loadStateFlow 是一个暴露加载状态的Flow。
            jokeAdapter.loadStateFlow.collectLatest { loadStates ->
                // `loadStates.refresh` 代表整个列表的加载状态（初次加载、下拉刷新）
                val refreshState = loadStates.refresh

                // 根据初次加载的状态来控制UI
                binding.progressBar.isVisible = refreshState is LoadState.Loading
                binding.btnRetry.isVisible = refreshState is LoadState.Error

                // 如果加载失败，可以获取错误信息并进行提示
                if (refreshState is LoadState.Error) {
                    // 可选：显示Toast或Snackbar提示用户
                    // Toast.makeText(this@PagingActivity, "Load Error: ${refreshState.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 设置重试按钮的点击事件
        binding.btnRetry.setOnClickListener {
            jokeAdapter.retry() // retry()会触发PagingSource重新加载失败的页面
        }
    }
}
