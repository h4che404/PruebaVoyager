package org.example.project.ui


import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun FloatingBottomBar(
    selectedIndex: Int,
    onSelectIndex: (Int) -> Unit
) {
    // Se dibuja por ENCIMA del contenido
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Padding multiplataforma: respeta home-indicator (iOS) y barras (Android)
        val bottomInsets = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
            .windowInsetsPadding(WindowInsets.ime.only(WindowInsetsSides.Bottom))
            .padding(bottom = 6.dp)

        Box(modifier = bottomInsets) {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .widthIn(max = 520.dp)
                    .clip(RoundedCornerShape(28.dp)),
                color = Color(0xFF0F0F12),      // negro/gráfico
                tonalElevation = 12.dp,
                shadowElevation = 12.dp
            ) {
                NavigationBar(
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp
                ) {
                    NavigationBarItem(
                        selected = selectedIndex == 0,
                        onClick = { onSelectIndex(0) },
                        icon  = { Icon(Icons.Filled.Home, null) },
                        label = { Text("Home") },
                        alwaysShowLabel = false
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 1,
                        onClick = { onSelectIndex(1) },
                        icon  = { Icon(Icons.Filled.Groups, null) },
                        label = { Text("Grupo") },
                        alwaysShowLabel = false
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 2,
                        onClick = { onSelectIndex(2) },
                        icon  = { Icon(Icons.Filled.Person, null) },
                        label = { Text("Perfil") },
                        alwaysShowLabel = false
                    )
                }
            }
        }
    }
}