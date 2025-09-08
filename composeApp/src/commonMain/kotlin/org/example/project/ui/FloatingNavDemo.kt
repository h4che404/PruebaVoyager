package org.example.project.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface

@Composable
fun FloatingBottomBar(
    selectedIndex: Int,
    onSelectIndex: (Int) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(30.dp),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {

            NavigationBarItem(
                selected = selectedIndex == 0,
                onClick = { onSelectIndex(0) },
                icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                label = { Text("Inicio") },
                alwaysShowLabel = false
            )

            NavigationBarItem(
                selected = selectedIndex == 1,
                onClick = { onSelectIndex(1) },
                icon = { Icon(Icons.Filled.Groups, contentDescription = "Grupo") },
                label = { Text("Grupo") },
                alwaysShowLabel = false
            )

            NavigationBarItem(
                selected = selectedIndex == 2,
                onClick = { onSelectIndex(2) },
                icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
                label = { Text("Perfil") },
                alwaysShowLabel = false
            )
        }
    }
}