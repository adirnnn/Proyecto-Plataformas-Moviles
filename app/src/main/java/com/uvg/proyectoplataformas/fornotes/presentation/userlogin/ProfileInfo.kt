package com.uvg.proyectoplataformas.fornotes.presentation.userlogin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.uvg.proyectoplataformas.navigation.Screen

@Composable
fun ProfileScreen(
    navController: NavController
) {

    var name by remember { mutableStateOf("Name") }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedImageUrl by remember {
        mutableStateOf("https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png")
    }
    var isImageSelectorVisible by remember { mutableStateOf(false) } //  selector

    // url imagenes
    val profileImageOptions = listOf(
        //"https://cdn-icons-png.flaticon.com/512/5094/5094452.png", // dog pink
        "https://cdn-icons-png.flaticon.com/512/1005/1005364.png", // panda pink
        //"https://cdn-icons-png.flaticon.com/512/4775/4775505.png",
        "https://cdn-icons-png.flaticon.com/512/4775/4775521.png", // foca green
        // "https://cdn-icons-png.flaticon.com/512/4775/4775537.png", // fox green
        //"https://cdn-icons-png.flaticon.com/512/8409/8409712.png",
        //https://cdn-icons-png.flaticon.com/256/414/414681.png
        //https://cdn-icons-png.flaticon.com/512/2403/2403430.png
        "https://cdn-icons-png.flaticon.com/512/7408/7408099.png", // cat blue
        //"https://cdn-icons-png.flaticon.com/512/6525/6525892.png" // conejo yellow
        "https://cdn-icons-png.flaticon.com/512/4775/4775486.png" // dog yellow



    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate(Screen.NoteScreen.route) }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Profile Settings",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        //
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color.Gray, CircleShape)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isImageSelectorVisible = !isImageSelectorVisible }
                .padding(horizontal = 40.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Change Profile Picture",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (isImageSelectorVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle Image Selector",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        //
        if (isImageSelectorVisible) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                profileImageOptions.forEach { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "Selectable Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(
                                width = if (selectedImageUrl == imageUrl) 4.dp else 2.dp,
                                color = if (selectedImageUrl == imageUrl) Color.Cyan else Color.Gray,
                                shape = CircleShape
                            )
                            .clickable {
                                selectedImageUrl = imageUrl
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(1.dp))

        // p options
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ProfileOption(
                icon = Icons.Default.Person,
                title = "Profile Name",
                onClick = { showEditDialog = true }
            )
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

            ProfileOption(icon = Icons.Default.Favorite, title = "Favorites")
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

            ProfileOptionWithSwitch(icon = Icons.Default.Notifications, title = "Notifications")
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

            Spacer(modifier = Modifier.height(16.dp))

            // Logout
            ProfileOption(
                icon = Icons.Default.ExitToApp,
                title = "Logout",
                onClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(0) // Clear all backstack to prevent going back
                    }
                },
                isLogout = true
            )
        }
    }

    //
    if (showEditDialog) {
        EditNameDialog(
            currentName = name,
            onDismiss = { showEditDialog = false },
            onNameChange = { newName ->
                name = newName
                showEditDialog = false
            }
        )
    }
}


@Composable
fun EditNameDialog(
    currentName: String,
    onDismiss: () -> Unit,
    onNameChange: (String) -> Unit
) {
    var newName by remember { mutableStateOf(currentName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onNameChange(newName) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Edit Name") },
        text = {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Name") }
            )
        },
        properties = DialogProperties(dismissOnClickOutside = true)
    )
}



@Composable
fun ProfileOptionWithSwitch(icon: ImageVector, title: String) {
    val isPushEnabled = remember { mutableStateOf(false) } // Se switch status

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD8E6D8) //
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isPushEnabled.value,
                onCheckedChange = { isPushEnabled.value = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
fun ProfileOption(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit = {}, // Action to perform when the option is clicked
    isLogout: Boolean = false // Flag for special styling on the logout option
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick), // Make the entire card clickable
        colors = CardDefaults.cardColors(
            containerColor = if (isLogout) Color(0xFFFFD8D8) else Color(0xFFD8E6D8) // Different color for logout
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (isLogout) Color.Red else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isLogout) Color.Red else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            if (!isLogout) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}