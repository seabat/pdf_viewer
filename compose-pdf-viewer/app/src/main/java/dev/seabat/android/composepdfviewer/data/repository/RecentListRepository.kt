package dev.seabat.android.composepdfviewer.data.repository

import dev.seabat.android.composepdfviewer.data.datasource.room.RecentPdf
import dev.seabat.android.composepdfviewer.data.datasource.room.RecentPdfDao
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.domain.repository.RecentListRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecentListRepository @Inject constructor(private val recentPdfDao: RecentPdfDao) :
    RecentListRepositoryContract {
    override suspend fun fetch(): PdfListEntity = withContext(Dispatchers.IO) {
        recentPdfDao.getAll().map {
            convertToPdfEntity(it)
        }.let {
            PdfListEntity(it.toMutableList())
        }
    }

    override suspend fun add(pdf: PdfResourceEntity) = withContext(Dispatchers.IO) {
        convertToRecentPdf(pdf).let {
            recentPdfDao.insertPdf(it)
        }
    }

    override suspend fun update(pdf: PdfResourceEntity) = withContext(Dispatchers.IO) {
        convertToRecentPdf(pdf).let {
            recentPdfDao.updatePdf(it)
        }
    }

    override suspend fun remove(pdf: PdfResourceEntity) = withContext(Dispatchers.IO) {
        convertToRecentPdf(pdf).let {
            recentPdfDao.delete(it)
        }
    }

    private fun convertToRecentPdf(pdf: PdfResourceEntity): RecentPdf = RecentPdf(
        path = pdf.pathString,
        title = pdf.title,
        fileName = pdf.fileName,
        size = pdf.size,
        importedDate = pdf.importedDateString,
        openedDate = pdf.openedDateString
    )

    private fun convertToPdfEntity(recentPdf: RecentPdf): PdfResourceEntity = PdfResourceEntity(
        title = recentPdf.title,
        fileName = recentPdf.fileName,
        pathString = recentPdf.path,
        size = recentPdf.size,
        importedDateString = recentPdf.importedDate,
        openedDateString = recentPdf.openedDate
    )
}