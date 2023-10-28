package dev.seabat.android.composepdfviewer.domain.entity

import java.time.ZonedDateTime

data class PdfEntity(
    val title: String,
    val description: String? = null,
    val size: Long,
    val date: ZonedDateTime
)