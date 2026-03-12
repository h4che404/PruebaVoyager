package org.example.project.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.example.project.ui.FloatingBottomBar
import org.example.project.ui.GroupScreen
import org.example.project.ui.HomeScreen
import org.example.project.ui.ProfileScreen


private val TopTabs = listOf("home", "group", "profile")

@Composable
fun NavigationWrapper() {
    val pager = rememberPagerState(initialPage = 0) { TopTabs.size }
    var selectedIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    val homeNav = rememberNavController()
    val groupNav = rememberNavController()
    val profileNav = rememberNavController()
    val controllers = remember {
        mapOf("home" to homeNav, "group" to groupNav, "profile" to profileNav)
    }

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0) // ← sin insets automáticos
    ) {
        // Fondo único a toda pantalla
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Contenido SIN padding(inner)
            HorizontalPager(
                state = pager,
                modifier = Modifier.fillMaxSize(),
                beyondViewportPageCount = 1
            ) { page ->
                when (TopTabs[page]) {
                    "home" -> TabGraph(controllers.getValue("home"), "home_root") {
                        composable("home_root") { HomeScreen() }
                    }
                    "group" -> TabGraph(controllers.getValue("group"), "group_root") {
                        composable("group_root") { GroupScreen() }
                    }
                    "profile" -> TabGraph(controllers.getValue("profile"), "profile_root") {
                        composable("profile_root") { ProfileScreen() }
                    }
                }
            }

            // Overlay flotante
            FloatingBottomBar(
                selectedIndex = selectedIndex,
                onSelectIndex = { idx ->
                    selectedIndex = idx
                    scope.launch { pager.animateScrollToPage(idx) }
                }
            )
        }
    }

    // Sincroniza barra cuando el swipe “asienta”
    LaunchedEffect(pager.settledPage) {
        if (selectedIndex != pager.settledPage) selectedIndex = pager.settledPage
    }
}

@Composable
private fun TabGraph(
    nav: NavHostController,
    start: String,
    graph: androidx.navigation.NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = nav,
        startDestination = start,
        builder = graph
    )
}