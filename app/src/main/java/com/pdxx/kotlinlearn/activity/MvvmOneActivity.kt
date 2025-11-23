package com.pdxx.kotlinlearn.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.pdxx.kotlinlearn.databinding.ActivityMvvmOneBinding
import com.pdxx.kotlinlearn.vm.UserViewModel

/**
 * MVVM中的 View层
 * Activity/Fragment作为View层，职责是:
 * 1. 显示数据：观察ViewModel中的LiveData，并将数据更新到UI上。
 * 2. 传递事件：捕获用户的交互（如点击），并通知ViewModel。
 * 3. View层非常“薄”，不包含任何业务逻辑或数据处理代码。
 */
class MvvmOneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmOneBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvvmOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 获取ViewModel实例
        // 通过ViewModelProvider，我们可以获取一个与此Activity生命周期绑定的ViewModel。
        // 当Activity因为配置变更（如旋转屏幕）重建时，我们能获取到同一个ViewModel实例，数据不会丢失。
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // 2. 设置UI事件监听
        // 当用户点击按钮时，我们只通知ViewModel“用户想要获取数据”，而不关心具体如何获取。
        binding.btnFetchUser.setOnClickListener {
            viewModel.fetchUserData()
        }

        // 3. 观察ViewModel中的数据变化
        setupObservers()

        // 4. 在界面上显示MVVM讲解文本
        setupExplanationText()

        // 5. 首次进入时自动加载一次数据
        if (savedInstanceState == null) {
            viewModel.fetchUserData()
        }
    }

    private fun setupObservers() {
        // 观察用户数据 LiveData
        // 当viewModel.user的数据发生变化时，这个lambda表达式会被执行。
        viewModel.user.observe(this) { user ->
            // 将数据显示在TextView上
            binding.tvUserData.text = "用户名: ${user.name}, 年龄: ${user.age}"
        }

        // 观察加载状态 LiveData
        // 当viewModel.isLoading的数据发生变化时，这个lambda表达式会被执行。
        viewModel.isLoading.observe(this) { isLoading ->
            // 根据isLoading的值，显示或隐藏ProgressBar
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun setupExplanationText() {
        val mvvmExplanation = ""
            .plus("MVVM (Model-View-ViewModel) 核心思想:\n")
            .plus("1. View: 只负责UI展示和用户事件转发。 (本Activity就是View)\n")
            .plus("2. ViewModel: 连接View和Model，持有UI状态(LiveData)，处理View的事件，请求Model层获取数据。 (UserViewModel.kt)\n")
            .plus("3. Model: 负责业务逻辑和数据处理。 (UserRepository.kt)\n\n")
            .plus("数据流: Model -> ViewModel -> View (单向数据流)\n")
            .plus("事件流: View -> ViewModel -> Model\n\n")
            .plus("点击下面的按钮，体验这个流程：\n")
            .plus("View(点击) -> ViewModel(调用fetchUserData) -> Model(模拟获取数据) -> ViewModel(更新LiveData) -> View(观察到变化并更新UI)")

        binding.tvExplanation.text = mvvmExplanation
    }
}
