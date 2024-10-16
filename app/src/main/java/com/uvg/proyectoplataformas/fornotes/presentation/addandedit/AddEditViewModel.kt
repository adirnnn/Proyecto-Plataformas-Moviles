package com.uvg.proyectoplataformas.fornotes.presentation.addandedit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.proyectoplataformas.fornotes.domain.model.Note
import com.uvg.proyectoplataformas.fornotes.domain.usecase.NoteUsecases
import com.uvg.proyectoplataformas.fornotes.domain.util.InvalidNoteException
import com.uvg.proyectoplataformas.fornotes.presentation.util.TextFieldState
import com.uvg.proyectoplataformas.fornotes.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val usecases: NoteUsecases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                getCurrentNote(noteId)
            }
        }
    }

    private var currentNoteId: Int? = null

    private val _noteContent = mutableStateOf(TextFieldState(hint = "Enter some content..."))
    val noteContent: State<TextFieldState> = _noteContent

    private val _noteTitle = mutableStateOf(TextFieldState(hint = "Enter title..."))
    val noteTitle: State<TextFieldState> = _noteTitle

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = _noteTitle.value.copy(
                    text = event.text
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && _noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.text
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused
                            && _noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.SaveNote -> {
                saveNote()
            }
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                usecases.addNoteUsecase(
                    note = Note(
                        title = noteTitle.value.text,
                        content = noteContent.value.text,
                        timeStamp = System.currentTimeMillis(),
                        color = noteColor.value,
                        id = currentNoteId
                    )
                )
                _uiEventFlow.emit(UiEvent.NavigateOnSaveNote)
            } catch (e: InvalidNoteException) {
                _uiEventFlow.emit(
                    UiEvent.ShowSnackbar(
                        message = e.message ?: "Couldn't save note"
                    )
                )
            }
        }
    }

    private fun getCurrentNote(noteId: Int) {
        viewModelScope.launch {
            usecases.getNoteByIdUsecase(noteId)?.also { note ->
                currentNoteId = note.id
                _noteTitle.value = noteTitle.value.copy(
                    text = note.title,
                    isHintVisible = false
                )
                _noteContent.value = _noteContent.value.copy(
                    text = note.content,
                    isHintVisible = false
                )
                _noteColor.value = note.color
            }
        }
    }


}