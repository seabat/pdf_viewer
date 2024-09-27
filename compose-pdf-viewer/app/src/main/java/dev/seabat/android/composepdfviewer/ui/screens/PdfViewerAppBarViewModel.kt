package dev.seabat.android.composepdfviewer.ui.screens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.usecase.ImportFileUseCaseContract
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PdfViewerAppBarViewModel @Inject constructor(
    private val importFileUseCase: ImportFileUseCaseContract
) : ViewModel() {

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