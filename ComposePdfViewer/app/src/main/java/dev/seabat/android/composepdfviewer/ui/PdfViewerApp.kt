package dev.seabat.android.composepdfviewer.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.ui.screens.all.AllListScreen
import dev.seabat.android.composepdfviewer.ui.screens.all.AllListViewModel
import dev.seabat.android.composepdfviewer.ui.screens.favorite.FavoriteScreen
import dev.seabat.android.composepdfviewer.ui.screens.favorite.FavoriteViewModel
import dev.seabat.android.composepdfviewer.ui.screens.pdfviewer.PdfViewerScreen
import dev.seabat.android.composepdfviewer.ui.screens.pdfviewer.PdfViewerViewModel
import dev.seabat.android.composepdfviewer.ui.screens.recentness.RecentnessScreen
import dev.seabat.android.composepdfviewer.ui.screens.recentness.RecentnessViewModel
import dev.seabat.android.composepdfviewer.ui.screens.Screen.AllList
import dev.seabat.android.composepdfviewer.ui.screens.Screen.Favorite
import dev.seabat.android.composepdfviewer.ui.screens.Screen.PdfViewer
import dev.seabat.android.composepdfviewer.ui.screens.Screen.Recentness
import java.time.ZonedDateTime

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PdfViewerApp() {
    val navController = rememberNavController()

    PdfViewerNavHost(
        navController = navController,
    )
}

@Composable
fun PdfViewerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Recentness.route,
    ) {
        composable(Recentness.route) {
            val viewModel = hiltViewModel<RecentnessViewModel>()
            RecentnessScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Favorite.route) {
            val viewModel = hiltViewModel<FavoriteViewModel>()
            FavoriteScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(AllList.route) {
            val viewModel = hiltViewModel<AllListViewModel>()
            AllListScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = "${PdfViewer.route}/?pdf={pdf}",
            arguments = listOf(navArgument("pdf") { type = NavType.StringType }),
        ) {backStackEntry ->
            val pdfJson = backStackEntry.arguments?.getString("pdf")
            pdfJson?.let {
                //TODO: pdfJson を PDF オブジェクトに変換する
                val viewModel = hiltViewModel<PdfViewerViewModel>()
                PdfViewerScreen(
                    viewModel = viewModel,
                    navController = navController,
                    pdf = PdfEntity("title1", "desc1", 178, ZonedDateTime.now()),
                )
            }
        }
    }
}