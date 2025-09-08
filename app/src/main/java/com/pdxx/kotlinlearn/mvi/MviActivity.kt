package com.pdxx.kotlinlearn.mvi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdxx.kotlinlearn.databinding.ActivityMviBinding
import kotlinx.coroutines.launch

/**
 * Step 6.4: 创建Activity (View层)
 * Activity/Fragment是View层，它的职责是：
 * 1. 观察ViewModel的State变化，并根据State更新UI。
 * 2. 捕获用户的交互事件（如点击、滑动），并将其转换为Intent发送给ViewModel。
 * View层本身不包含任何业务逻辑，它只是一个状态的展示器和事件的传递者。
 */
class MviActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMviBinding
    private val viewModel: ImageViewModel by viewModels()
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        observeViewModel()

        // Activity创建时，发送首次加载的意图
        if (savedInstanceState == null) {
            lifecycleScope.launch {
                viewModel.intentChannel.send(ImageIntent.LoadInitial)
            }
        }
    }

    // 初始化RecyclerView
    private fun setupRecyclerView() {
        imageAdapter = ImageAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MviActivity)
            adapter = imageAdapter
        }
    }

    // 设置各种监听器
    private fun setupListeners() {
        // 设置下拉刷新监听
        binding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.intentChannel.send(ImageIntent.Refresh)
            }
        }

        // 设置RecyclerView的滚动监听，用于实现上拉加载更多
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                // 当滚动到列表末尾时，发送加载更多的意图
                if (lastVisibleItemPosition == totalItemCount - 1) {
                    lifecycleScope.launch {
                        viewModel.intentChannel.send(ImageIntent.LoadMore)
                    }
                }
            }
        })
    }

    // 观察ViewModel的State变化
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                // 根据State更新UI
                render(state)
            }
        }
    }

    // 根据State渲染UI
    private fun render(state: ImageState) {
        // 更新首次加载的ProgressBar
        binding.progressBar.isVisible = state.isLoading

        // 更新下拉刷新的ProgressBar
        binding.swipeRefreshLayout.isRefreshing = state.isRefreshing

        // 更新加载更多的ProgressBar
        binding.progressBarLoadMore.isVisible = state.isLoadingMore

        // 更新Adapter的数据
        imageAdapter.updateData(state.images)

        // 显示错误信息
        state.error?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}
