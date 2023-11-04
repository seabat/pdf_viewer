package dev.seabat.android.composepdfviewer.data.repository

import dev.seabat.android.composepdfviewer.data.datasource.room.RecentPdf
import dev.seabat.android.composepdfviewer.data.datasource.room.RecentPdfDao
import dev.seabat.android.composepdfviewer.domain.repository.RecentListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecentListRepository @Inject constructor(
    private val recentPdfDao: RecentPdfDao
): RecentListRepositoryContract {
    override suspend fun fetch(): PdfListEntity {
        return withContext(Dispatchers.IO) {
            recentPdfDao.getAll().map {
                convertToPdfEntity(it)
            }.let {
                PdfListEntity(it.toMutableList())
            }
        }
    }

    override suspend fun add(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            convertToRecentPdf(pdf).let {
                recentPdfDao.insertPdf(it)
            }
        }
    }

    override suspend fun update(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            convertToRecentPdf(pdf).let {
                recentPdfDao.updatePdf(it)
            }
        }
    }

    override suspend fun remove(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            convertToRecentPdf(pdf).let {
                recentPdfDao.delete(it)
            }
        }
    }

    private fun convertToRecentPdf(pdf: PdfEntity): RecentPdf {
        return RecentPdf(
            path = pdf.pathString,
            title = pdf.title,
            fileName = pdf.fileName,
            size = pdf.size,
            importedDate = pdf.importedDateString,
            openedDate = pdf.openedDateString
        )
    }

    private fun convertToPdfEntity(recentPdf: RecentPdf): PdfEntity {
        return PdfEntity(
            title = recentPdf.title,
            fileName = recentPdf.fileName,
            pathString = recentPdf.path,
            size = recentPdf.size,
            importedDateString = recentPdf.importedDate,
            openedDateString = recentPdf.openedDate
        )
    }
}