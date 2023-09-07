package dev.seabat.android.composepdfviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.seabat.android.composepdfviewer.screen.all.AllListScreen
import dev.seabat.android.composepdfviewer.screen.favorite.FavoriteScreen
import dev.seabat.android.composepdfviewer.screen.recentness.RecentnessScreen
import dev.seabat.android.composepdfviewer.ui.theme.ComposePdfViewerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePdfViewerTheme {
                PdfViewerApp()
            }
        }
    }
}

@Composable
fun PdfViewerApp() {
    val navController = rememberNavController()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        PdfViewerNavHost(
            navController = navController
        )
        PdfViewerBottomNavigation(
            navController = navController
        )
    }
}

@Composable
fun PdfViewerNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "recentness",
    ) {
        composable("recentness") {
            RecentnessScreen(
                onClick = {
                    navController.navigate("all")
                }
            )
        }
        composable("favorite") {
            FavoriteScreen(
                onClick = {
                    navController.navigate("recentness")
                }
            )
        }
        composable("all") {
            AllListScreen(
                onClick = {
                    navController.navigate("favorite")
                }
            )
        }
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Recentness : Screen("recentness", R.string.recentness)
    object Favorite : Screen("favorite", R.string.favorite)
    object AllList : Screen("all", R.string.all)
}

@Composable
fun PdfViewerBottomNavigation(
    navController: NavHostController
) {
    val screenItems = listOf(
        Screen.Favorite,
        Screen.Recentness,
        Screen.AllList
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screenItems.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
