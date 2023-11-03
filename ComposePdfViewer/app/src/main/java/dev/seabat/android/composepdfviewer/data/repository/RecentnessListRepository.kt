package dev.seabat.android.composepdfviewer.data.repository

import dev.seabat.android.composepdfviewer.data.datasource.room.RecentnessPdf
import dev.seabat.android.composepdfviewer.data.datasource.room.RecentnessPdfDao
import dev.seabat.android.composepdfviewer.domain.repository.RecentnessListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RecentnessListRepository @Inject constructor(
    private val recentnessPdfDao: RecentnessPdfDao
): RecentnessListRepositoryContract {
    override suspend fun fetch(): PdfListEntity {
        return withContext(Dispatchers.IO) {
            recentnessPdfDao.getAll().map {
                PdfEntity(
                    title = it.title,
                    fileName = it.fileName,
                    pathString = it.path,
                    size = it.size,
                    dateString = it.date
                )
            }.let {
                PdfListEntity(it.toMutableList())
            }
        }
    }

    override suspend fun add(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            RecentnessPdf(
                path = pdf.pathString,
                title = pdf.title,
                fileName = pdf.fileName,
                size = pdf.size,
                date = pdf.dateString
            ).let {
                recentnessPdfDao.insertPdf(it)
            }
        }
    }

    override suspend fun update(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            RecentnessPdf(
                path = pdf.pathString,
                title = pdf.title,
                fileName = pdf.fileName,
                size = pdf.size,
                date = pdf.dateString
            ).let {
                recentnessPdfDao.updatePdf(it)
            }
        }
    }

    override suspend fun remove(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            RecentnessPdf(
                path = pdf.pathString,
                title = pdf.title,
                fileName = pdf.fileName,
                size = pdf.size,
                date = pdf.dateString
            ).let {
                recentnessPdfDao.delete(it)
            }
        }
    }
}