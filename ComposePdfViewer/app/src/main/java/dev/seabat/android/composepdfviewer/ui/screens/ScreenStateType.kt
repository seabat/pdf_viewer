package dev.seabat.android.composepdfviewer.ui.screens

sealed class ScreenStateType(val value: Int) {
    object Loading : ScreenStateType(0)
    object Loaded : ScreenStateType(1)
    data class Error(val e: Exception) : ScreenStateType(2)
}
