package com.pdxx.kotlinlearn.securenote.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdxx.kotlinlearn.securenote.domain.GetNoteUseCase
import com.pdxx.kotlinlearn.securenote.domain.SaveNoteUseCase
import com.pdxx.kotlinlearn.securenote.domain.SecureNote
import kotlinx.coroutines.launch

class SecureNoteViewModel(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase
) : ViewModel() {

    private val _noteContent = MutableLiveData<String>()
    val noteContent: LiveData<String> = _noteContent

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    fun saveNote(content: String) {
        viewModelScope.launch {
            try {
                val note = SecureNote("default_note", "My Secret", content, System.currentTimeMillis())
                saveNoteUseCase(note)
                _statusMessage.value = "笔记已加密保存"
            } catch (e: Exception) {
                _statusMessage.value = "保存失败: ${e.message}"
            }
        }
    }

    fun loadNote() {
        viewModelScope.launch {
            try {
                val note = getNoteUseCase("default_note")
                if (note != null) {
                    _noteContent.value = note.content
                    _statusMessage.value = "笔记已解密加载"
                } else {
                    _statusMessage.value = "未找到笔记"
                }
            } catch (e: Exception) {
                _statusMessage.value = "加载失败: ${e.message}"
            }
        }
    }
}
