package com.pdxx.kotlinlearn.securenote.data

import android.content.Context
import android.content.SharedPreferences

/**
 * 本地数据源：使用 SharedPreferences 存储加密后的数据
 */
class LocalDataSource(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("secure_notes", Context.MODE_PRIVATE)

    fun saveEncryptedNote(id: String, iv: String, encryptedContent: String) {
        prefs.edit()
            .putString("${id}_iv", iv)
            .putString("${id}_content", encryptedContent)
            .apply()
    }

    fun getEncryptedNote(id: String): Pair<String, String>? {
        val iv = prefs.getString("${id}_iv", null)
        val content = prefs.getString("${id}_content", null)
        
        if (iv != null && content != null) {
            return Pair(iv, content)
        }
        return null
    }
}
