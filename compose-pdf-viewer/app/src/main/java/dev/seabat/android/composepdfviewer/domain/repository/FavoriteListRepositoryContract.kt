package dev.seabat.android.composepdfviewer.domain.repository

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity

interface FavoriteListRepositoryContract {
    suspend fun fetch(): PdfListEntity

    suspend fun add(pdf: PdfResourceEntity)

    suspend fun update(pdf: PdfResourceEntity)

    suspend fun remove(pdf: PdfResourceEntity)
}