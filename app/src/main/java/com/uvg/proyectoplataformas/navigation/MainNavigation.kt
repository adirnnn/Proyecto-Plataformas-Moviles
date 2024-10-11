package com.uvg.proyectoplataformas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.uvg.proyectoplataformas.fornotes.presentation.addandedit.AddEditNoteScreen
import com.uvg.proyectoplataformas.fornotes.presentation.note.NoteScreen
import com.uvg.proyectoplataformas.fornotes.presentation.userlogin.ProfileScreen

@Composable
fun MainNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.NoteScreen.route,
    ) {
        composable(route = Screen.NoteScreen.route) { NoteScreen(navController) }
        composable(Screen.ProfileScreen.route) { ProfileScreen(navController) }
        composable(
            route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(name = "noteColor") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        )
        { entry ->
            val color = entry.arguments?.getInt("noteColor") ?: -1
            AddEditNoteScreen(navController, color)
        }
    }
}