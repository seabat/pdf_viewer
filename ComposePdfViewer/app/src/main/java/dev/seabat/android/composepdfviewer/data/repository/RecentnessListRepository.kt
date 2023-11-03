package dev.seabat.android.composepdfviewer.data.repository

import dev.seabat.android.composepdfviewer.data.datasource.room.RecentnessPdf
import dev.seabat.android.composepdfviewer.data.datasource.room.RecentnessPdfDao
import dev.seabat.android.composepdfviewer.domain.repository.RecentnessListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecentnessListRepository @Inject constructor(
    private val recentnessPdfDao: RecentnessPdfDao
): RecentnessListRepositoryContract {
    override suspend fun fetch(): PdfListEntity {
        return withContext(Dispatchers.IO) {
            recentnessPdfDao.getAll().map {
                convertToPdfEntity(it)
            }.let {
                PdfListEntity(it.toMutableList())
            }
        }
    }

    override suspend fun add(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            convertToRecentnessPdf(pdf).let {
                recentnessPdfDao.insertPdf(it)
            }
        }
    }

    override suspend fun update(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            convertToRecentnessPdf(pdf).let {
                recentnessPdfDao.updatePdf(it)
            }
        }
    }

    override suspend fun remove(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            convertToRecentnessPdf(pdf).let {
                recentnessPdfDao.delete(it)
            }
        }
    }

    private fun convertToRecentnessPdf(pdf: PdfEntity): RecentnessPdf {
        return RecentnessPdf(
            path = pdf.pathString,
            title = pdf.title,
            fileName = pdf.fileName,
            size = pdf.size,
            importedDate = pdf.importedDateString,
            openedDate = pdf.openedDateString
        )
    }

    private fun convertToPdfEntity(recentnessPdf: RecentnessPdf): PdfEntity {
        return PdfEntity(
            title = recentnessPdf.title,
            fileName = recentnessPdf.fileName,
            pathString = recentnessPdf.path,
            size = recentnessPdf.size,
            importedDateString = recentnessPdf.importedDate,
            openedDateString = recentnessPdf.openedDate
        )
    }
}