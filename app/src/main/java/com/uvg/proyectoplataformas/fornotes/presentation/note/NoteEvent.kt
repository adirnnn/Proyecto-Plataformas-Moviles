package com.uvg.proyectoplataformas.fornotes.presentation.note

import com.uvg.proyectoplataformas.fornotes.domain.model.Note
import com.uvg.proyectoplataformas.fornotes.domain.util.NoteOrder

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder) : NoteEvent()
    data class DeleteNote(val note: Note) : NoteEvent()
    object RestoreNote : NoteEvent()
    object ToggleOrderSection : NoteEvent()
}