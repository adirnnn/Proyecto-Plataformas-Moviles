package com.uvg.proyectoplataformas.fornotes.presentation.note

import com.uvg.proyectoplataformas.fornotes.domain.model.Note
import com.uvg.proyectoplataformas.fornotes.domain.util.NoteOrder
import com.uvg.proyectoplataformas.fornotes.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)