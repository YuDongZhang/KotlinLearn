package com.pdxx.kotlinlearn.moduleFunny.model

// API返回的完整数据结构
data class JokeResponse(
    val code: Int,
    val message: String,
    val result: List<Joke>
)

// 单个笑话的数据模型
data class Joke(
    val sid: String,
    val text: String,
    val type: String,
    val thumbnail: String,
    val video: String,
    val author: String
)
