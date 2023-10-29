package dev.seabat.android.composepdfviewer

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.utils.getFileInfoFromUri
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.io.File
import java.io.FileOutputStream
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun copyPdfToInternalStorage() {
        viewModelScope.launch {
            val outputFile = File(context.filesDir, "sample.pdf")
            context.assets.open("sample.pdf").use { inputStream ->
                outputFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
    }

    fun importPdf(uri: Uri, onImported: (PdfEntity) -> Unit) {
        val fileInfo = getFileInfoFromUri(context, uri) ?: return
        fileInfo.first ?.let { fileName ->
            copyPdfToInternalStorage(fileName, uri) {
                onImported(PdfEntity(fileName, fileName, fileInfo.second, ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)))
            }
        }
    }

    private fun copyPdfToInternalStorage(fileName: String, uri: Uri, onImported: (String) -> Unit) {
        viewModelScope.launch {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return@launch
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
            onImported(fileName)
        }
    }
}