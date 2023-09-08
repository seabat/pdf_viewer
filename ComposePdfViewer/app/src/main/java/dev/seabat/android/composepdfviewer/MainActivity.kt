package dev.seabat.android.composepdfviewer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PdfViewerApp() {
    val navController = rememberNavController()
    val shouldShowTop = remember { mutableStateOf(true) }
    val shouldShowBottom = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            if (shouldShowTop.value) {
                PdfViewerAppBar()
            }
        },
        bottomBar = {
            if (shouldShowBottom.value) {
                PdfViewerBottomNavigation(
                    navController = navController
                )
            }
        }
    ) {
        PdfViewerNavHost(
            navController = navController,
            shouldShowTop,
            shouldShowBottom
        )
    }
}

@Composable
fun PdfViewerNavHost(
    navController: NavHostController,
    shouldShowTop: MutableState<Boolean>,
    shouldShowBottom: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = "favorite",
    ) {
        composable("recentness") {
            RecentnessScreen(
                onClick = {
                    navController.navigate("all")
                }
            )
            shouldShowTop.value = true
            shouldShowBottom.value = true
        }
        composable("favorite") {
            FavoriteScreen(
                onClick = {
                    navController.navigate("recentness")
                }
            )
            shouldShowTop.value = false
            shouldShowBottom.value = true
        }
        composable("all") {
            AllListScreen(
                onClick = {
                    navController.navigate("favorite")
                }
            )
            shouldShowTop.value = true
            shouldShowBottom.value = true
        }
    }
}

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val image: ImageVector
) {
    object Recentness : Screen("recentness", R.string.recentness, Icons.Filled.DateRange)
    object Favorite : Screen("favorite", R.string.favorite, Icons.Filled.Favorite)
    object AllList : Screen("all", R.string.all, Icons.Filled.List)
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
                icon = { Icon(screen.image, contentDescription = null) },
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

@Composable
fun PdfViewerAppBar() {
    androidx.compose.material.TopAppBar(
        title = { androidx.compose.material3.Text("最近見たファイル") },
    )
}
