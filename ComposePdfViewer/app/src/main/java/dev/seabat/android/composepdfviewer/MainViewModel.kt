package dev.seabat.android.composepdfviewer

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.usecase.ImportFileUseCaseContract
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.io.File

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val importFileUseCase: ImportFileUseCaseContract,
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

    /**
     * Uri に配置された PDF ファイルをアプリのプライベート領域に import する
     *
     * @param uri
     * @param onImported import が完了した際に呼ばれるコールバック
     */
    fun importPdfAsync(uri: Uri, onImported: (PdfEntity) -> Unit) {
        viewModelScope.launch {
            val pdfEntity = importFileUseCase(uri)
            onImported(pdfEntity)
        }
    }
}