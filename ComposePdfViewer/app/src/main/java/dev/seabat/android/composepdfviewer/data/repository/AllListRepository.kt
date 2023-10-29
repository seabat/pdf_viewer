package dev.seabat.android.composepdfviewer.data.repository

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.repository.AllListRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AllListRepository @Inject constructor(): AllListRepositoryContract {
    private val pdfs: PdfListEntity = PdfListEntity(
        mutableListOf(
            PdfEntity("title1", "desc1", 178, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title2", "desc2", 298, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title3", "desc3", 587, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title4", "desc4", 319, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title5", "desc5", 287, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title6", "desc6", 654, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title7", "desc7", 769, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title8", "desc8", 890, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title9", "desc9", 997, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title10", "desc10", 65, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title11", "desc11", 143, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title12", "desc12", 253, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title13", "desc13", 380, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title14", "desc14", 417, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title15", "desc15", 528, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)),
            PdfEntity("title16", "desc16", 651, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
        )
    )

    override suspend fun fetch(): PdfListEntity {
        return withContext(Dispatchers.IO) {
            pdfs
        }
    }

    override suspend fun add(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            pdfs.add(pdf)
        }
    }

    override suspend fun remove(pdf: PdfEntity) {
        return withContext(Dispatchers.IO) {
            pdfs.remove(pdf)
        }
    }
}