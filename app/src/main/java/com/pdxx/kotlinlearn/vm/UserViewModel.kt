package com.pdxx.kotlinlearn.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdxx.kotlinlearn.model.UserData
import com.pdxx.kotlinlearn.model.UserRepository
import kotlinx.coroutines.launch

/**
 * MVVM中的 ViewModel层
 * ViewModel的职责:
 * 1. 持有并管理UI相关的数据。（使用LiveData）
 * 2. 暴露公共方法给View，用于处理用户交互。
 * 3. 与Model层（Repository）交互，获取数据和执行业务逻辑。
 * 4. ViewModel不应持有任何View（Activity/Fragment）的引用。
 */
class UserViewModel : ViewModel() {

    // Model层的实例
    private val repository = UserRepository()

    // --- LiveData --- //

    // 用户数据: 私有的可变LiveData，用于在ViewModel内部更新数据
    private val _user = MutableLiveData<UserData>()
    // 公开的不可变LiveData，用于给View层观察数据变化
    val user: LiveData<UserData> = _user

    // 加载状态
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // --- Public Methods for View --- //

    /**
     * 当View需要获取或刷新用户数据时调用此方法。
     */
    fun fetchUserData() {
        // 使用viewModelScope启动一个协程。这个协程与ViewModel的生命周期绑定，
        // 当ViewModel被销毁时，协程会自动取消，避免内存泄漏。
        viewModelScope.launch {
            // 1. 开始加载，通知UI显示进度条
            _isLoading.value = true

            try {
                // 2. 调用Repository获取数据
                val fetchedUser = repository.getUser("123") // userId是示例

                // 3. 获取成功，更新user LiveData
                _user.value = fetchedUser

            } catch (e: Exception) {
                // 可以在这里处理异常，例如更新一个error LiveData
                // _error.value = e.message
            } finally {
                // 4. 加载结束，通知UI隐藏进度条
                _isLoading.value = false
            }
        }
    }
}
