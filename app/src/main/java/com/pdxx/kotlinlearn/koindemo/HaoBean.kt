package com.pdxx.kotlinlearn.koindemo
import androidx.annotation.Keep


@Keep
data class HaoBean(
    val code: Int?,
    val message: String?,
    val result: Result?
) {
    @Keep
    data class Result(
        val list: List<Desc>?,
        val total: Int?
    ) {
        @Keep
        data class Desc (
            val coverUrl: String?,
            val duration: String?,
            val id: Int?,
            val playUrl: String?,
            val title: String?,
            val userName: String?,
            val userPic: String?
        )
    }
}