package org.example.project.core

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.example.project.ui.FloatingBottomBar
import org.example.project.ui.GroupScreen
import org.example.project.ui.HomeScreen
import org.example.project.ui.ProfileScreen
import kotlin.math.absoluteValue

private val TopTabs = listOf(Home, Group, Profile)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationWrapper() {
    val pager = rememberPagerState(initialPage = 0) { TopTabs.size }
    var selectedIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    // Un NavController por tab, recordado (para no perder el back stack de cada pestaña)
    val controllers = remember { mutableStateMapOf<Screen, NavHostController>() }

    @Composable
    fun controllerFor(tab: Screen): NavHostController {
        return controllers.getOrPut(tab) { rememberNavController() }
    }

    Scaffold(
        bottomBar = {
            FloatingBottomBar(
                selectedIndex = selectedIndex,
                onSelectIndex = { idx ->
                    selectedIndex = idx
                    scope.launch { pager.animateScrollToPage(idx)
                    }
                }
            )
        }
    ) { inner ->
        HorizontalPager(
            state = pager,
            modifier = Modifier.fillMaxSize().padding(inner),
            beyondViewportPageCount = 1 // precarga sutil para suavidad
        ) { page ->
            when (TopTabs[page]) {
                Home -> TabNavHost(
                    nav = controllerFor(Home),
                    start = HomeRoot
                ) {
                    composable<HomeRoot> { HomeScreen(
                    ) }
                }

                Group -> TabNavHost(
                    nav = controllerFor(Group),
                    start = GroupRoot
                ) {
                    composable<GroupRoot> { GroupScreen() }
                    // … agregá más destinos internos de Group si los necesitás
                }

                Profile -> TabNavHost(
                    nav = controllerFor(Profile),
                    start = ProfileRoot
                ) {
                    composable<ProfileRoot> { ProfileScreen() }
                    // … destinos internos de Profile si querés
                }
            }
        }

        // Pager → BottomBar (cuando el usuario suelta el swipe)
        LaunchedEffect(pager.settledPage) {
            if (selectedIndex != pager.settledPage) selectedIndex = pager.settledPage
        }
    }
}

/* ---------- NavHost “de tab” reutilizable ---------- */

@Composable
private fun TabNavHost(
    nav: NavHostController,
    start: Any,
    graph: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = nav,
        startDestination = start,
        // Entre tabs ya tenemos el swipe del Pager; las transiciones acá son para push/pop internos
        enterTransition = { EnterTransition.None },
        exitTransition  = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition  = { ExitTransition.None },
        builder = graph
    )
}

