package dev.seabat.android.composepdfviewer.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import dev.seabat.android.composepdfviewer.R

sealed class Screen(
    val route: String,
    @StringRes val appBarTitleResId: Int,
    @StringRes val bottomLabelResId: Int?,
    val image: ImageVector
) {
    object Recentness : Screen(
        "recentness",
        R.string.recentness,
        R.string.recentness,
        Icons.Filled.DateRange
    )
    object Favorite : Screen(
        "favorite",
        R.string.favorite,
        R.string.favorite,
        Icons.Filled.Favorite
    )
    object AllList : Screen(
        "all",
        R.string.all,
        R.string.all,
        Icons.Filled.List
    )
    object PdfViewer : Screen(
        "pdf_viewer",
        R.string.pdf_viewer,
        null,
        Icons.Filled.List
    )
}

@StringRes
fun getAppBarTitle(route: String): Int {
    return when(route) {
        Screen.Recentness.route -> Screen.Recentness.appBarTitleResId
        Screen.Favorite.route -> Screen.Favorite.appBarTitleResId
        Screen.AllList.route -> Screen.AllList.appBarTitleResId
        Screen.PdfViewer.route -> Screen.PdfViewer.appBarTitleResId
        else -> {
            throw IllegalStateException()
        }
    }
}

/**
 *  @param route ex. "favorite", "pdf_viewer/?pdf={pdf}"
 */
fun getScreen(route: String): Screen {
    val screenName = route.split("/")[0]
    return when(screenName) {
        Screen.Recentness.route -> Screen.Recentness
        Screen.Favorite.route -> Screen.Favorite
        Screen.AllList.route -> Screen.AllList
        Screen.PdfViewer.route -> Screen.PdfViewer
        else -> {
            throw IllegalStateException()
        }
    }
}
