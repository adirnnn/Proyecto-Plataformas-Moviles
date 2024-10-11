package com.uvg.proyectoplataformas.fornotes.domain.repository

import com.uvg.proyectoplataformas.fornotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

//definition
interface Repository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun deleteNote(note: Note)

    suspend fun insertNote(note: Note)
}