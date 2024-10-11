package com.uvg.proyectoplataformas.fornotes.domain.usecase

data class NoteUsecases(
    val getNotesUsecase: GetNotesUsecase,
    val deleteNoteUsecase: DeleteNoteUsecase,
    val addNoteUsecase: AddNoteUsecase,
    val getNoteByIdUsecase: GetNoteByIdUsecase
)