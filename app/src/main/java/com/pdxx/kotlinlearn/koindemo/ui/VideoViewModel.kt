package com.pdxx.kotlinlearn.koindemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdxx.kotlinlearn.koindemo.model.VideoItem
import com.pdxx.kotlinlearn.koindemo.repository.VideoRepository
import kotlinx.coroutines.launch

class VideoViewModel(private val repository: VideoRepository) : ViewModel() {

    private val _videos = MutableLiveData<List<VideoItem>>()
    val videos: LiveData<List<VideoItem>> = _videos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var currentPage = 1
    private val pageSize = 20
    private var total = 0
    private var videoList = mutableListOf<VideoItem>()

    init {
        loadVideos()
    }

    fun loadVideos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getVideos(currentPage, pageSize)
                if (response.code == 200) {
                    total = response.result.total
                    if (currentPage == 1) {
                        videoList.clear()
                    }
                    videoList.addAll(response.result.list)
                    _videos.value = videoList
                    currentPage++
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        currentPage = 1
        loadVideos()
    }

    fun loadMore() {
        if ((currentPage - 1) * pageSize < total) {
            loadVideos()
        }
    }
}
