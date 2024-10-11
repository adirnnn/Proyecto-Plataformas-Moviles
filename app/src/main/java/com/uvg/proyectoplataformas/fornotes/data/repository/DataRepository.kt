package com.uvg.proyectoplataformas.fornotes.data.repository

import com.uvg.proyectoplataformas.fornotes.data.local.NoteDao
import com.uvg.proyectoplataformas.fornotes.domain.model.Note
import com.uvg.proyectoplataformas.fornotes.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class DataRepository(
    private val dao: NoteDao
) : Repository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }
}