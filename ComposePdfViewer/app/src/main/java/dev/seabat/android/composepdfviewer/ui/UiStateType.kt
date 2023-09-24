package dev.seabat.android.composepdfviewer.ui

sealed class UiStateType(val value: Int) {
    object Loading : UiStateType(0)
    object Loaded : UiStateType(1)
    data class Error(val e: Exception) : UiStateType(2)
}
