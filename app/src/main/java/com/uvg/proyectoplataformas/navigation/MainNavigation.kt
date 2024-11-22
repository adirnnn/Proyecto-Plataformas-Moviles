package com.uvg.proyectoplataformas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.uvg.proyectoplataformas.fornotes.presentation.LoginScreen
import com.uvg.proyectoplataformas.fornotes.presentation.addandedit.AddEditNoteScreen
import com.uvg.proyectoplataformas.fornotes.presentation.note.NoteScreen
import com.uvg.proyectoplataformas.fornotes.presentation.userlogin.ProfileScreen
import com.uvg.proyectoplataformas.fornotes.presentation.RegisterScreen

@Composable
fun MainNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route // iniciar
    ) {

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }


        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController)
        }


        composable(route = Screen.NoteScreen.route) {
            NoteScreen(navController)
        }


        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController)
        }

        // add/edit note
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
        ) { entry ->
            val color = entry.arguments?.getInt("noteColor") ?: -1
            AddEditNoteScreen(navController, color)
        }
    }
}