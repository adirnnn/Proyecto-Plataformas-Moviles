package com.uvg.proyectoplataformas.fornotes.presentation

import android.content.Context
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
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import com.uvg.proyectoplataformas.R
import java.util.Locale

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val sharedPreferences = remember {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    // extract string for messages
    val errorFillFields = stringResource(R.string.error_fill_fields)
    val errorAuthenticationFailed = stringResource(R.string.error_authentication_failed)
    //
    val loginTitle = stringResource(R.string.login_title)
    val emailLabel = stringResource(R.string.email_label)
    val passwordLabel = stringResource(R.string.password_label)
    val loginButtonText = stringResource(R.string.login_button)
    val noAccountText = stringResource(R.string.no_account)

    //  change language
    fun changeLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // Save the sel ected language in SharedPreferences
        sharedPreferences.edit().putString("app_language", languageCode).apply()

        // Restart the activity to apply the language change
        val restartIntent = Intent(context, MainActivity::class.java)
        context.startActivity(restartIntent)
        (context as? MainActivity)?.finish()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.login_title), style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email_label)) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password_label)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (showError) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate("note_screen") {
                                    popUpTo("login_screen") { inclusive = true }
                                }
                            } else {
                                showError = true
                                errorMessage = "$errorAuthenticationFailed: ${task.exception?.localizedMessage}"
                            }
                        }
                } else {
                    showError = true
                    errorMessage = errorFillFields
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(loginButtonText)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = noAccountText,
            modifier = Modifier
                .clickable { navController.navigate("register_screen") }
                .padding(top = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.weight(1f))

        // language selection buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { changeLanguage("en") },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("English")
            }

            Button(
                onClick = { changeLanguage("es") },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text("Español")
            }
        }
    }
}
