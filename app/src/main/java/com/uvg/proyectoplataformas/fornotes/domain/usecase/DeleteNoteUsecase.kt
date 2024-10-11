package com.uvg.proyectoplataformas.fornotes.domain.usecase

import com.uvg.proyectoplataformas.fornotes.domain.model.Note
import com.uvg.proyectoplataformas.fornotes.domain.repository.Repository

class DeleteNoteUsecase(private val repository: Repository) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }

}