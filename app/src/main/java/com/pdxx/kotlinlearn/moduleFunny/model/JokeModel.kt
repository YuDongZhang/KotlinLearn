package com.pdxx.kotlinlearn.moduleFunny.model

// 完整的API响应结构
data class JokeResponse(
    val code: Int,
    val message: String,
    val result: JokeResult
)

// “result”字段对应的结构
data class JokeResult(
    val total: Int,
    val list: List<Joke>
)

// 视频列表中的单个视频对象
data class Joke(
    val id: String,
    val title: String,
    val userName: String,
    val userPic: String,
    val coverUrl: String,
    val playUrl: String,
    val duration: String
)