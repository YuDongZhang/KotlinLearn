package com.pdxx.kotlinlearn.securenote.data

import com.pdxx.kotlinlearn.securenote.domain.SecureNote
import com.pdxx.kotlinlearn.securenote.domain.SecureNoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 仓库实现：协调 KeystoreDataSource 和 LocalDataSource
 */
class SecureNoteRepositoryImpl(
    private val keystoreDataSource: KeystoreDataSource,
    private val localDataSource: LocalDataSource
) : SecureNoteRepository {

    override suspend fun saveNote(note: SecureNote) = withContext(Dispatchers.IO) {
        // 1. 加密内容
        val (iv, encryptedContent) = keystoreDataSource.encryptData(note.content)
        // 2. 存储加密后的数据
        localDataSource.saveEncryptedNote(note.id, iv, encryptedContent)
    }

    override suspend fun getNote(id: String): SecureNote? = withContext(Dispatchers.IO) {
        // 1. 获取加密数据
        val encryptedData = localDataSource.getEncryptedNote(id) ?: return@withContext null
        val (iv, encryptedContent) = encryptedData
        
        // 2. 解密内容
        try {
            val decryptedContent = keystoreDataSource.decryptData(iv, encryptedContent)
            // 简单起见，这里只恢复内容，Title 和 Timestamp 也可以类似处理或存明文
            // 这里假设 Title 是 "Secure Note"
            SecureNote(id, "Secure Note", decryptedContent, System.currentTimeMillis())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
