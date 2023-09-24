package dev.seabat.android.composepdfviewer.domain.entity

import java.util.Date

data class PdfEntity(
    val title: String,
    val description: String,
    val size: Int,
    val date: Date
)