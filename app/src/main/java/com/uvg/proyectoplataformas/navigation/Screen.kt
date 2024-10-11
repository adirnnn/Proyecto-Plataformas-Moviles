package com.uvg.proyectoplataformas.navigation

sealed class Screen(val route: String) {
    object NoteScreen : Screen("note_screen")
    object AddEditNoteScreen : Screen("add_edit_note_screen")
    object ProfileScreen : Screen("profile_screen")
}