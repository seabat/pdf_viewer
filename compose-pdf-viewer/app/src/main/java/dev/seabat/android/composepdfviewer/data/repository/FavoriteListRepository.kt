package dev.seabat.android.composepdfviewer.data.repository

import dev.seabat.android.composepdfviewer.data.datasource.room.FavoritePdf
import dev.seabat.android.composepdfviewer.data.datasource.room.FavoritePdfDao
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.domain.repository.FavoriteListRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteListRepository @Inject constructor(private val favoritePdfDao: FavoritePdfDao) :
    FavoriteListRepositoryContract {
    override suspend fun fetch(): PdfListEntity = withContext(Dispatchers.IO) {
        favoritePdfDao.getAll().map {
            convertToPdfEntity(it)
        }.let {
            PdfListEntity(it.toMutableList())
        }
    }

    override suspend fun add(pdf: PdfResourceEntity) = withContext(Dispatchers.IO) {
        convertToFavoritePdf(pdf).let {
            favoritePdfDao.insertPdf(it)
        }
    }

    override suspend fun update(pdf: PdfResourceEntity) = withContext(Dispatchers.IO) {
        convertToFavoritePdf(pdf).let {
            favoritePdfDao.updatePdf(it)
        }
    }

    override suspend fun remove(pdf: PdfResourceEntity) = withContext(Dispatchers.IO) {
        convertToFavoritePdf(pdf).let {
            favoritePdfDao.delete(it)
        }
    }

    private fun convertToFavoritePdf(pdf: PdfResourceEntity): FavoritePdf = FavoritePdf(
        path = pdf.pathString,
        title = pdf.title,
        fileName = pdf.fileName,
        size = pdf.size,
        importedDate = pdf.importedDateString,
        openedDate = pdf.openedDateString
    )

    private fun convertToPdfEntity(favoritePdf: FavoritePdf): PdfResourceEntity = PdfResourceEntity(
        title = favoritePdf.title,
        fileName = favoritePdf.fileName,
        pathString = favoritePdf.path,
        size = favoritePdf.size,
        importedDateString = favoritePdf.importedDate,
        openedDateString = favoritePdf.openedDate
    )
}