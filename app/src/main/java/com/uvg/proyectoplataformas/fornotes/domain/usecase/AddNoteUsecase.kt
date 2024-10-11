package com.uvg.proyectoplataformas.fornotes.domain.usecase

import com.uvg.proyectoplataformas.fornotes.domain.model.Note
import com.uvg.proyectoplataformas.fornotes.domain.repository.Repository
import com.uvg.proyectoplataformas.fornotes.domain.util.InvalidNoteException

class AddNoteUsecase(private val repository: Repository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title can't be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content can't be empty.")
        }
        repository.insertNote(note)
    }
}