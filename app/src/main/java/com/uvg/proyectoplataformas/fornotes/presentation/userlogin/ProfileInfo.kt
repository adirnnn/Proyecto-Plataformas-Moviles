package com.uvg.proyectoplataformas.fornotes.presentation.userlogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.uvg.proyectoplataformas.fornotes.presentation.note.NoteScreen
import com.uvg.proyectoplataformas.ui.theme.NotesTheme

@Composable
fun ProfileScreen(
    navigator: NavController
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://png.pngtree.com/background/20230616/original/pngtree-faceted-abstract-background-in-3d-with-shimmering-iridescent-metallic-texture-of-picture-image_3653595.jpg"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://paciershk.com/cdn/shop/files/PastedGraphic25.png?v=1713190363"),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Lando Norris",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            ProfileOption(icon = Icons.Default.Person, title = "Edit Profile")
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

            ProfileOption(icon = Icons.Default.Lock, title = "Reset Password")
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

            ProfileOptionWithSwitch(icon = Icons.Default.Notifications, title = "Notifications")
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
        }
    }
}

@Composable
fun ProfileOption(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
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
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ProfileOptionWithSwitch(icon: ImageVector, title: String) {
    val isPushEnable = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
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
            checked = isPushEnable.value,
            onCheckedChange = { isPushEnable.value = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navigator = rememberNavController())
}
