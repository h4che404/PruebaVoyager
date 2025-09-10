package org.example.project.core

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.example.project.ui.FloatingBottomBar
import org.example.project.ui.GroupScreen
import org.example.project.ui.HomeScreen
import org.example.project.ui.ProfileScreen


private val TopTabs = listOf(Home, Group, Profile)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationWrapper() {
    val pager = rememberPagerState(initialPage = 0) { TopTabs.size }
    var selectedIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    // Un controller por tab → preserva el back stack de cada pestaña
    val homeNav: NavHostController = rememberNavController()
    val groupNav: NavHostController = rememberNavController()
    val profileNav: NavHostController = rememberNavController()

    val controllers = remember {
        mapOf(Home to homeNav, Group to groupNav, Profile to profileNav)
    }

    Scaffold { inner ->
        Box(Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pager,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inner),
                beyondViewportPageCount = 1
            ) { page ->
                when (TopTabs[page]) {
                    Home -> TabGraph(controllers.getValue(Home), start = HomeRoot) {
                        // Rutas tipadas (si tu versión lo soporta en iOS)
                        composable<HomeRoot> { HomeScreen() }
                        // Si te falla en iOS, usa String:
                        // composable("home_root") { HomeScreen() }
                    }
                    Group -> TabGraph(controllers.getValue(Group), start = GroupRoot) {
                        composable<GroupRoot> { GroupScreen() }
                        // composable("group_root") { GroupScreen() }
                    }
                    Profile -> TabGraph(controllers.getValue(Profile), start = ProfileRoot) {
                        composable<ProfileRoot> { ProfileScreen() }
                        // composable("profile_root") { ProfileScreen() }
                    }
                }
            }

            // Barra flotante por encima (overlay real)
            FloatingBottomBar(
                selectedIndex = selectedIndex,
                onSelectIndex = { idx ->
                    selectedIndex = idx
                    scope.launch { pager.animateScrollToPage(idx) }
                }
            )
        }

        // Sincroniza el índice cuando el usuario suelta el swipe
        LaunchedEffect(pager.settledPage) {
            if (selectedIndex != pager.settledPage) selectedIndex = pager.settledPage
        }
    }
}

@Composable
private fun TabGraph(
    nav: NavHostController,
    start: Any,
    graph: androidx.navigation.NavGraphBuilder.() -> Unit
) {
    // Si usaras String:
    // NavHost(navController = nav, startDestination = "xxx", builder = graph)
    NavHost(
        navController = nav,
        startDestination = start,
        builder = graph
    )
}
/*
// Si querés usar Navigation Compose, mové esta implementación a androidMain y usá esa versión sólo en Android.
@Composable
private fun TabNavHost(/* ... */) { }
*/
