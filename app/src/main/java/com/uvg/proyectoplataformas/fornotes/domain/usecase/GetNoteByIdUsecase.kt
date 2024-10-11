package com.uvg.proyectoplataformas.fornotes.domain.usecase

import com.uvg.proyectoplataformas.fornotes.domain.model.Note
import com.uvg.proyectoplataformas.fornotes.domain.repository.Repository

class GetNoteByIdUsecase(private val repository: Repository) {
    suspend operator fun invoke(noteId: Int): Note? {
        return repository.getNoteById(noteId)
    }
}