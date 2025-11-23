package com.pdxx.kotlinlearn.securenote.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.securenote.data.KeystoreDataSource
import com.pdxx.kotlinlearn.securenote.data.LocalDataSource
import com.pdxx.kotlinlearn.securenote.data.SecureNoteRepositoryImpl
import com.pdxx.kotlinlearn.securenote.domain.GetNoteUseCase
import com.pdxx.kotlinlearn.securenote.domain.SaveNoteUseCase
import java.util.concurrent.Executor

/**
 * 安全笔记 Activity
 * 演示：
 * 1. Clean Architecture 分层 (UI -> ViewModel -> UseCase -> Repository -> DataSource)
 * 2. Android Keystore 系统进行数据加密
 * 3. BiometricPrompt 进行生物识别认证
 */
class SecureNoteActivity : AppCompatActivity() {

    private lateinit var viewModel: SecureNoteViewModel
    private lateinit var etContent: EditText
    private lateinit var tvStatus: TextView
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secure_note)

        // 手动注入依赖 (实际项目中推荐使用 Koin/Hilt)
        val keystoreDataSource = KeystoreDataSource()
        val localDataSource = LocalDataSource(this)
        val repository = SecureNoteRepositoryImpl(keystoreDataSource, localDataSource)
        val saveNoteUseCase = SaveNoteUseCase(repository)
        val getNoteUseCase = GetNoteUseCase(repository)
        
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SecureNoteViewModel(saveNoteUseCase, getNoteUseCase) as T
            }
        }).get(SecureNoteViewModel::class.java)

        initViews()
        setupBiometric()
        observeViewModel()
    }

    private fun initViews() {
        etContent = findViewById(R.id.et_note_content)
        tvStatus = findViewById(R.id.tv_status)

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val content = etContent.text.toString()
            if (content.isNotEmpty()) {
                viewModel.saveNote(content)
                etContent.text.clear()
            } else {
                Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btn_load).setOnClickListener {
            // 点击加载时，先进行生物识别认证
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun setupBiometric() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    tvStatus.text = "认证错误: $errString"
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    tvStatus.text = "认证成功，正在解密..."
                    // 认证成功后，执行加载逻辑
                    viewModel.loadNote()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    tvStatus.text = "认证失败"
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("验证身份以查看笔记")
            .setSubtitle("使用指纹或面部识别")
            .setNegativeButtonText("取消")
            .build()
    }

    private fun observeViewModel() {
        viewModel.noteContent.observe(this) { content ->
            etContent.setText(content)
        }

        viewModel.statusMessage.observe(this) { msg ->
            tvStatus.text = msg
        }
    }
}
