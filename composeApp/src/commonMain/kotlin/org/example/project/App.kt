package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import pruebavoyager.composeapp.generated.resources.Res
import pruebavoyager.composeapp.generated.resources.compose_multiplatform

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var selected by remember { mutableStateOf("home") }
        var showContent by remember { mutableStateOf(false) }

        TopAppBar(
            title = { Text(text = "home") } // muestra el nombre de la pantalla
        )
        Box(
            Modifier.fillMaxSize()
        ) {
            when (selected) {
                "home" -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 96.dp) // ⚡ espacio extra para que el último ítem no quede tapado
                    ) {
                        items((1..30).toList()) { index ->
                            ListItem(
                                headlineContent = { Text("Elemento $index") },
                                supportingContent = { Text("Detalle del elemento $index") }
                            )
                            Divider()
                        }
                    }
                }
                "grupo" -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .safeContentPadding()
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("Grupo")
                    }
                }
                "perfil" -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Pantalla de Configuración")
                }
            }

            // 3) Barra flotante conectada al estado
            FloatingBottomBar(
                selected = selected,
                onSelect = { selected = it },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .widthIn(max = 350.dp)   // 🔹 ancho máximo (compacta)
                    .height(76.dp)
                    .padding(bottom = 15.dp)
            )
        }
    }
}