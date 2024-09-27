package dev.seabat.android.composepdfviewer.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.ui.screens.Screen.AllList
import dev.seabat.android.composepdfviewer.ui.screens.Screen.Favorite
import dev.seabat.android.composepdfviewer.ui.screens.Screen.PdfViewer
import dev.seabat.android.composepdfviewer.ui.screens.Screen.Recent
import dev.seabat.android.composepdfviewer.ui.screens.all.AllListScreen
import dev.seabat.android.composepdfviewer.ui.screens.favorite.FavoriteScreen
import dev.seabat.android.composepdfviewer.ui.screens.pdfviewer.PdfViewerScreen
import dev.seabat.android.composepdfviewer.ui.screens.recent.RecentScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PdfViewerApp() {
    val navController = rememberNavController()

    PdfViewerNavHost(
        navController = navController
    )
}

@Composable
fun PdfViewerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Recent.route
    ) {
        composable(Recent.route) {
            RecentScreen(navController = navController)
        }
        composable(Favorite.route) {
            FavoriteScreen(navController = navController)
        }
        composable(AllList.route) {
            AllListScreen(navController = navController)
        }
        composable(
            route = "${PdfViewer.route}/?pdf={pdf}",
            arguments = listOf(navArgument("pdf") { type = NavType.StringType })
        ) { backStackEntry ->
            val jsonString = backStackEntry.arguments?.getString("pdf")
            jsonString?.let { json ->
                PdfResourceEntity.convertJsonToObject(json)?.let { pdfEntity ->
                    PdfViewerScreen(navController = navController, pdf = pdfEntity)
                }
            }
        }
    }
}
