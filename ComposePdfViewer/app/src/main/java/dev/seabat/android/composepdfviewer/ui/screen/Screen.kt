package dev.seabat.android.composepdfviewer.ui.screen

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
    @StringRes val bottomLabelResId: Int,
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
}

@StringRes
fun getAppBarTitle(route: String): Int {
    return when(route) {
        Screen.Recentness.route -> Screen.Recentness.appBarTitleResId
        Screen.Favorite.route -> Screen.Favorite.appBarTitleResId
        Screen.AllList.route -> Screen.AllList.appBarTitleResId
        else -> {
            throw IllegalStateException()
        }
    }
}

fun getScreen(route: String): Screen {
    return when(route) {
        Screen.Recentness.route -> Screen.Recentness
        Screen.Favorite.route -> Screen.Favorite
        Screen.AllList.route -> Screen.AllList
        else -> {
            throw IllegalStateException()
        }
    }
}
