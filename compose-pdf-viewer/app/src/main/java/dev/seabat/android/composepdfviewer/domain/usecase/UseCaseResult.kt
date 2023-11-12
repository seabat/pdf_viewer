package dev.seabat.android.composepdfviewer.domain.usecase

import java.lang.Exception

sealed class UseCaseResult<out T> {
    data class Success<out T>(val data: T) : UseCaseResult<T>()
    data class Failure<out T>(val e: Exception) : UseCaseResult<T>()
}