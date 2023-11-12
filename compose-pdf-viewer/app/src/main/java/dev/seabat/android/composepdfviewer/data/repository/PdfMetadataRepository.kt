package dev.seabat.android.composepdfviewer.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.seabat.android.composepdfviewer.domain.exception.PdfViewerException
import dev.seabat.android.composepdfviewer.domain.repository.PdfMetadataRepositoryContract
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDDocumentInformation
import java.io.File
import javax.inject.Inject

class PdfMetadataRepository @Inject constructor(
    @ApplicationContext private val context: Context
): PdfMetadataRepositoryContract {

   override fun extractPdfTitle(pdfFilePath: String): String? {
        val titleResult = runCatching {
            PDFBoxResourceLoader.init(context)
            val title = PDDocument.load(File(pdfFilePath)).use { document ->
                val info: PDDocumentInformation = document.documentInformation
                info.author
                info.subject
                info.creationDate
                info.title
            }
            title
        }.onFailure {
            throw PdfViewerException("PDFファイルからメタデータ取得する際にエラーが発生しました")
        }
        return titleResult.getOrNull()
    }
}