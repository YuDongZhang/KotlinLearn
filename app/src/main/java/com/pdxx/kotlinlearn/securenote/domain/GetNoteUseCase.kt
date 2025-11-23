package com.pdxx.kotlinlearn.securenote.domain

/**
 * UseCase: 获取笔记
 */
class GetNoteUseCase(private val repository: SecureNoteRepository) {
    suspend operator fun invoke(id: String): SecureNote? {
        return repository.getNote(id)
    }
}
