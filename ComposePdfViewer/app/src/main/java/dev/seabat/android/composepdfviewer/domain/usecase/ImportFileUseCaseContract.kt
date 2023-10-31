package dev.seabat.android.composepdfviewer.domain.usecase

import android.net.Uri
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity

interface ImportFileUseCaseContract {

    /**
     * Uri が示す場所に格納されているファイルをアプリのプライベート領域に import する
     *
     * @return
     */
    suspend operator fun invoke(uri: Uri): PdfEntity
}