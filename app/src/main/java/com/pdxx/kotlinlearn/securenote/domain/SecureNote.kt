package com.pdxx.kotlinlearn.securenote.domain

/**
 * 领域模型：安全笔记
 */
data class SecureNote(
    val id: String,
    val title: String,
    val content: String,
    val timestamp: Long
)
