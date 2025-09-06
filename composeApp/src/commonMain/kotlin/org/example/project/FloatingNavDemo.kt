package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun FloatingBottomBarDemo() {
    var selected by remember { mutableStateOf("home") }

    Box(Modifier.fillMaxSize()) {
        // Contenido de tu pantalla
        Text(
            text = when (selected) {
                "home" -> "Inicio"
                "team" -> "Equipo"
                else   -> "Torneos"
            },
            modifier = Modifier.align(Alignment.Center)
        )

        // Barra flotante
        FloatingBottomBar(
            selected = selected,
            onSelect = { selected = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp) // separación del borde inferior
        )
    }
}

@Composable
fun FloatingBottomBar(
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Usamos Surface para darle forma, sombra y color
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        tonalElevation = 8.dp,              // sombra Material 3
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        // “Mini” NavigationBar con ancho envuelto
        NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
            NavigationBarItem(
                selected = selected == "home",
                onClick = { onSelect("home") },
                icon = { Icon(Icons.Filled.Home, null) },
                label = { Text("Inicio") },
                alwaysShowLabel = false
            )
            NavigationBarItem(
                selected = selected == "grupo",
                onClick = { onSelect("grupo") },
                icon = { Icon(Icons.Filled.Groups, null) },
                label = { Text("Grupo") },
                alwaysShowLabel = false
            )
            NavigationBarItem(
                selected = selected == "perfil",
                onClick = { onSelect("perfil") },
                icon = { Icon(Icons.Filled.Group, null) },
                label = { Text("Perfil") },
                alwaysShowLabel = false
            )
        }
    }
}