package dev.seabat.android.composepdfviewer.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.ui.appbar.PdfViewerAppBar
import dev.seabat.android.composepdfviewer.ui.screens.all.AllListScreen
import dev.seabat.android.composepdfviewer.ui.screens.all.AllListViewModel
import dev.seabat.android.composepdfviewer.ui.screens.favorite.FavoriteScreen
import dev.seabat.android.composepdfviewer.ui.screens.pdfviewer.PdfViewerScreen
import dev.seabat.android.composepdfviewer.ui.screens.pdfviewer.PdfViewerViewModel
import dev.seabat.android.composepdfviewer.ui.screens.recentness.RecentnessScreen
import dev.seabat.android.composepdfviewer.ui.screens.recentness.RecentnessViewModel
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PdfViewerApp() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = getScreen(
        backStackEntry?.destination?.route ?: Screen.Favorite.route
    )

    val scaffoldState = remember {
        mutableStateOf(
            ScaffoldState(
                shouldShowTop = true,
                shouldShowTopClose = false,
                shouldShowBottom = true
            )
        )
    }

    Scaffold(
        topBar = {
            if (scaffoldState.value.shouldShowTop) {
                PdfViewerAppBar(
                    navController = navController,
                    currentScreen = currentScreen,
                    scaffoldState = scaffoldState
                )
            }
        },
        bottomBar = {
            if (scaffoldState.value.shouldShowBottom) {
                PdfViewerBottomNavigation(
                    navController = navController,
                )
            }
        }
    ) { paddingValues ->
        PdfViewerNavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            onChangeScaffoldState = { state -> scaffoldState.value = state },
        )
    }
}

@Composable
fun PdfViewerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onChangeScaffoldState: (ScaffoldState) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "favorite",
    ) {
        composable("recentness") {
            val viewModel = hiltViewModel<RecentnessViewModel>()
            RecentnessScreen(
                viewModel = viewModel,
                onClick = {
                    navController.navigate("all")
                }
            )
            onChangeScaffoldState(
                ScaffoldState(
                    shouldShowTop = true,
                    shouldShowTopClose = false,
                    shouldShowBottom = true
                )
            )
        }
        composable("favorite") {
            FavoriteScreen(
                onClick = {
                    navController.navigate("recentness")
                }
            )
            onChangeScaffoldState(
                ScaffoldState(
                    shouldShowTop = false,
                    shouldShowTopClose = false,
                    shouldShowBottom = true
                )
            )
        }
        composable("all") {
            val viewModel = hiltViewModel<AllListViewModel>()
            AllListScreen(
                viewModel = viewModel,
                navigateToPdfViewer = { pdf ->
                    //TODO: pdf を JSON データに変換する
                    navController.navigate("pdf_viewer" + "/?pdf=" + "pdfのJSONデータ")
                }
            )
            onChangeScaffoldState(
                ScaffoldState(
                    shouldShowTop = true,
                    shouldShowTopClose = false,
                    shouldShowBottom = true
                )
            )
        }
        composable(
            route = "pdf_viewer/?pdf={pdf}",
            arguments = listOf(navArgument("pdf") { type = NavType.StringType }),
        ) {backStackEntry ->
            val pdfJson = backStackEntry.arguments?.getString("pdf")
            pdfJson?.let {
                //TODO: pdfJson を PDF オブジェクトに変換する
                val viewModel = hiltViewModel<PdfViewerViewModel>()
                PdfViewerScreen(
                    viewModel = viewModel,
                    pdf = PdfEntity("title1", "desc1", 178, Date()),
                )
            }
            onChangeScaffoldState(
                ScaffoldState(
                    shouldShowTop = true,
                    shouldShowTopClose = true,
                    shouldShowBottom = false
                )
            )
        }
    }
}


@Composable
fun PdfViewerBottomNavigation(
    navController: NavHostController,
) {
    val screenItems = listOf(
        Screen.Recentness,
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
                        stringResource(screen.bottomLabelResId ?: throw IllegalStateException("BottomNavigationのラベルが設定されていません"))
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

data class ScaffoldState(
    val shouldShowTop: Boolean,
    val shouldShowTopClose: Boolean,
    val shouldShowBottom: Boolean
)
