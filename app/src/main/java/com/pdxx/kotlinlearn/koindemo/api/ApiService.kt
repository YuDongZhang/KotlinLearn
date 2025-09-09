package com.pdxx.kotlinlearn.koindemo.api

import com.pdxx.kotlinlearn.koindemo.model.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/getHaoKanVideo")
    suspend fun getHaoKanVideo(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): VideoResponse
}
