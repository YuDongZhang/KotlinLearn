package com.pdxx.kotlinlearn.ktxdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdxx.kotlinlearn.ktxdemo.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KtxDemoViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        loadUser()
    }

    private fun loadUser() {
        // Simulate a network request or database query
        viewModelScope.launch {
            delay(2000) // Simulate a 2-second delay
            _user.value = User("John Doe", 30)
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            delay(1000) // Simulate a 1-second delay
            val currentAge = _user.value?.age ?: 0
            _user.value = User("Jane Doe", currentAge + 1)
        }
    }
}
