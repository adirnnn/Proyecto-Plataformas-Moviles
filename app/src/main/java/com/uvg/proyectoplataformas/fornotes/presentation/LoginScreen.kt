package com.uvg.proyectoplataformas.fornotes.presentation

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uvg.proyectoplataformas.MyApp
import com.uvg.proyectoplataformas.fornotes.presentation.MainActivity

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Login", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (showError) {
            Text(text = "Invalid credentials, please try again", color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    // On successful login, navigate to the main screen
                    navController.navigate("note_screen") {
                        popUpTo("login_screen") { inclusive = true }
                    }
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Option to navigate to the registration screen
        Text(
            text = "Don't have an account? Register here",
            modifier = Modifier
                .clickable {
                    navController.navigate("register_screen")
                }
                .padding(top = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
