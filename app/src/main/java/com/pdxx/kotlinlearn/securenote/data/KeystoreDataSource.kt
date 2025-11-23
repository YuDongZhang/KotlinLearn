package com.pdxx.kotlinlearn.securenote.data

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Keystore 数据源：负责加密和解密
 * 使用 Android Keystore 系统生成和存储密钥，确保密钥安全
 */
class KeystoreDataSource {

    companion object {
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val KEY_ALIAS = "SecureNoteKey"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
    }

    private val keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply {
        load(null)
    }

    init {
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            generateKey()
        }
    }

    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    private fun getKey(): SecretKey {
        return keyStore.getKey(KEY_ALIAS, null) as SecretKey
    }

    fun encryptData(plainText: String): Pair<String, String> {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        
        val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
        val encryptedString = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        
        return Pair(ivString, encryptedString)
    }

    fun decryptData(ivString: String, encryptedString: String): String {
        val iv = Base64.decode(ivString, Base64.DEFAULT)
        val encryptedBytes = Base64.decode(encryptedString, Base64.DEFAULT)
        
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)
        
        val decodedBytes = cipher.doFinal(encryptedBytes)
        return String(decodedBytes, Charsets.UTF_8)
    }
}
