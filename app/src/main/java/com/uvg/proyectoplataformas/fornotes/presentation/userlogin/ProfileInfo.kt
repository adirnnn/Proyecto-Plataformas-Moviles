    package com.uvg.proyectoplataformas.fornotes.presentation.userlogin
    
    import android.content.Context
    import android.content.Intent
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
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.window.DialogProperties
    import androidx.navigation.NavController
    import coil.compose.rememberAsyncImagePainter
    import com.uvg.proyectoplataformas.R
    import com.uvg.proyectoplataformas.fornotes.presentation.MainActivity
    import com.uvg.proyectoplataformas.navigation.Screen
    import java.util.Locale
    import androidx.compose.ui.unit.dp

    @Composable
    fun ProfileScreen(
        navController: NavController
    ) {
        val context = LocalContext.current
        val sharedPreferences = remember {
            context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
        }

        // Retrieve and set the preferred language
        val savedLanguage = sharedPreferences.getString("app_language", "en") ?: "en"
        val (currentLanguage, setCurrentLanguage) = remember { mutableStateOf(savedLanguage) }

        // Apply language changes
        fun applyLanguage(languageCode: String) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val config = context.resources.configuration
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)

            // Save the language preference
            sharedPreferences.edit().putString("app_language", languageCode).apply()

            // Refresh the ProfileScreen by updating the current language state
            setCurrentLanguage(languageCode)
        }


        // Localized strings
        val profileTitle = stringResource(R.string.profile_title)
        val changeProfilePicture = stringResource(R.string.change_profile_picture)
        val defaultName = stringResource(R.string.default_name)
        val profileName = stringResource(R.string.profile_name)
        val logout = stringResource(R.string.logout)



        var name by remember {
            mutableStateOf(
                sharedPreferences.getString("profile_name", defaultName) ?: defaultName
            )
        }
        var selectedImageUrl by remember {
            mutableStateOf(
                sharedPreferences.getString(
                    "profile_image_url",
                    "https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png"
                ) ?: "https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png"
            )
        }
        var showEditDialog by remember { mutableStateOf(false) }
        var isImageSelectorVisible by remember { mutableStateOf(false) }
    
        // url imagenes
        val profileImageOptions = listOf(
    
            //"https://cdn-icons-png.flaticon.com/512/5094/5094452.png", // dog pink
            "https://cdn-icons-png.flaticon.com/512/1005/1005364.png", // panda pink
            //"https://cdn-icons-png.flaticon.com/512/4775/4775505.png",
            "https://cdn-icons-png.flaticon.com/512/4775/4775521.png", // foca green
            // "https://cdn-icons-png.flaticon.com/512/4775/4775537.png", // fox green
            //"https://cdn-icons-png.flaticon.com/512/8409/8409712.png",
            "https://cdn-icons-png.flaticon.com/512/7408/7408099.png", // cat blue
                //"https://cdn-icons-png.flaticon.com/512/6525/6525892.png" // conejo yellow
            "https://cdn-icons-png.flaticon.com/512/4775/4775486.png" // dog yellow
        )
    
        //save preferences
        fun savePreference(key: String, value: String) {
            sharedPreferences.edit().putString(key, value).apply()
        }


    
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
                    text = profileTitle,
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
    
            // cahnge profile pic option
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isImageSelectorVisible = !isImageSelectorVisible }
                    .padding(horizontal = 40.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = changeProfilePicture,
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


            // select image
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
                                    savePreference("profile_image_url", imageUrl)
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
                    title = profileName,
                    onClick = { showEditDialog = true }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
    

                //ProfileOptionWithSwitch(icon = Icons.Default.Notifications, title = "Notifications")
               // Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
    
                Spacer(modifier = Modifier.height(16.dp))
    
                // logout
                ProfileOption(
                    icon = Icons.Default.ExitToApp,
                    title = logout,
                    onClick = {
                        navController.navigate(Screen.LoginScreen.route) {
                            popUpTo(0) // clear
                        }
                    },
                    isLogout = true
                )

            }

            //
            Spacer(modifier = Modifier.height(1.dp))

            // buttons for language
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { applyLanguage("en") },
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 12.dp)
                ) {
                    Text("English")
                }
                Button(
                    onClick = { applyLanguage("es") },
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 12.dp)
                ) {
                    Text("Español")
                }
            }
        }


    
        // edit name
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
    
        if (showEditDialog) {
            EditNameDialog(
                currentName = name,
                onDismiss = { showEditDialog = false },
                onNameChange = { newName ->
                    name = newName
                    savePreference("profile_name", newName)
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

        val editName = stringResource(R.string.edit_name)
        val cancelName = stringResource(R.string.cancel_name)
        val saveName = stringResource(R.string.save_name)
        val boxName = stringResource(R.string.box_name)
    
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = { onNameChange(newName) }) {
                    Text(saveName, color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(cancelName, color = MaterialTheme.colorScheme.primary)
                }
            },
            title = {
                Text(editName, color = MaterialTheme.colorScheme.onBackground)
            },

            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = {
                        Text(
                            boxName,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.onBackground // set text color
                    ),
                    singleLine = true,
                    maxLines = 1
                )
            },


            properties = DialogProperties(dismissOnClickOutside = true)
        )
    
    }
    
    
    
    @Composable
    fun ProfileOptionWithSwitch(icon: ImageVector, title: String) {
        val isPushEnabled = remember { mutableStateOf(false) } // for status
    
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
        onClick: () -> Unit = {}, // do when click
        isLogout: Boolean = false // on the logout option
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clickable(onClick = onClick), //
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