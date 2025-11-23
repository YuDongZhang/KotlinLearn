package com.pdxx.kotlinlearn.koindemo.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: VideoResult
)

data class VideoResult(
    @SerializedName("total") val total: Int,
    @SerializedName("list") val list: List<VideoItem>
)

data class VideoItem(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userPic") val userPic: String,
    @SerializedName("coverUrl") val coverUrl: String,
    @SerializedName("playUrl") val playUrl: String,
    @SerializedName("duration") val duration: String
)
