package dev.seabat.android.composepdfviewer.entities

import java.util.Date

data class Pdf(
    val title: String,
    val description: String,
    val size: Int,
    val date: Date
)