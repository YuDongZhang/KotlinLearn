package com.pdxx.kotlinlearn.securenote.domain

/**
 * 仓库接口：定义数据操作契约
 * 位于 Domain 层，不依赖具体实现
 */
interface SecureNoteRepository {
    suspend fun saveNote(note: SecureNote)
    suspend fun getNote(id: String): SecureNote?
}
