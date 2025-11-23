package com.pdxx.kotlinlearn.securenote.domain

/**
 * UseCase: 保存笔记
 * 封装具体的业务逻辑
 */
class SaveNoteUseCase(private val repository: SecureNoteRepository) {
    suspend operator fun invoke(note: SecureNote) {
        // 这里可以添加业务校验，例如标题不能为空等
        if (note.title.isBlank()) {
            throw IllegalArgumentException("Title cannot be empty")
        }
        repository.saveNote(note)
    }
}
