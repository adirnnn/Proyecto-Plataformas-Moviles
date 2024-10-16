package com.uvg.proyectoplataformas.fornotes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uvg.proyectoplataformas.fornotes.domain.model.Note
import com.uvg.proyectoplataformas.fornotes.domain.model.Note.Companion.NOTE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM $NOTE_TABLE")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}