package dev.seabat.android.composepdfviewer.data.repository

import dev.seabat.android.composepdfviewer.domain.repository.RecentnessListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RecentnessListRepository @Inject constructor(): RecentnessListRepositoryContract {
    private val pdfs: PdfListEntity = PdfListEntity(
        mutableListOf(
            PdfEntity("title1", "desc1", 178, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title2", "desc2", 298, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title3", "desc3", 587, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title4", "desc4", 319, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title5", "desc5", 287, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
        )
    )

    override suspend fun fetch(): PdfListEntity {
        return withContext(Dispatchers.IO) {
            pdfs
        }
    }

    override suspend fun add(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            // 重複データを削除する
            pdfs.firstOrNull { it.title == pdf.title } ?.let {
                pdfs.remove(it)
            }

            pdfs.add(pdf)
        }
    }

    override suspend fun remove(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            pdfs.remove(pdf)
        }
    }
}