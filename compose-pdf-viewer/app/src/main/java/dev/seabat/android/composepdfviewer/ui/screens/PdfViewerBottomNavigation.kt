package dev.seabat.android.composepdfviewer.ui.screens

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun PdfViewerBottomNavigation(
    navController: NavHostController
) {
    val screenItems = listOf(
        Screen.Recent,
        Screen.Favorite,
        Screen.AllList
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screenItems.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.image, contentDescription = null) },
                label = {
                    Text(
                        stringResource(
                            screen.bottomLabelResId
                                ?: throw IllegalStateException(
                                    "BottomNavigationのラベルが設定されていません"
                                )
                        )
                    )
                },
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
                        restoreState = false // NOTE: true にすると画面が再生成されず、ViewModel の初期化も実行されない
                    }
                }
            )
        }
    }
}
