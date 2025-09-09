package com.pdxx.kotlinlearn.koindemo.repository

import com.pdxx.kotlinlearn.koindemo.api.ApiService

class VideoRepository(private val apiService: ApiService) {
    suspend fun getVideos(page: Int, size: Int) = apiService.getHaoKanVideo(page, size)
}
