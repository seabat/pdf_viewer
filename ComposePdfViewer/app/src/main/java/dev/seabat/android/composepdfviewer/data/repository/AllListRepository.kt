package dev.seabat.android.composepdfviewer.data.repository

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.repository.AllListRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class AllListRepository @Inject constructor(): AllListRepositoryContract {
    private val pdfs: PdfListEntity = PdfListEntity(
        mutableListOf(
            PdfEntity("title1", "desc1", 178, Date()),
            PdfEntity("title2", "desc2", 298, Date()),
            PdfEntity("title3", "desc3", 587, Date()),
            PdfEntity("title4", "desc4", 319, Date()),
            PdfEntity("title5", "desc5", 287, Date()),
            PdfEntity("title6", "desc6", 654, Date()),
            PdfEntity("title7", "desc7", 769, Date()),
            PdfEntity("title8", "desc8", 890, Date()),
            PdfEntity("title9", "desc9", 997, Date()),
            PdfEntity("title10", "desc10", 65, Date()),
            PdfEntity("title11", "desc11", 143, Date()),
            PdfEntity("title12", "desc12", 253, Date()),
            PdfEntity("title13", "desc13", 380, Date()),
            PdfEntity("title14", "desc14", 417, Date()),
            PdfEntity("title15", "desc15", 528, Date()),
            PdfEntity("title16", "desc16", 651, Date())
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