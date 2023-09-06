package dev.seabat.android.composepdfviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    PdfViewerNavHost(
        navController = navController
    )
}

@Composable
fun PdfViewerNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "recentness") {
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
