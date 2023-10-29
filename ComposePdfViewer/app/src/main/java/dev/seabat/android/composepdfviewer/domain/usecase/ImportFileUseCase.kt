package dev.seabat.android.composepdfviewer.domain.usecase

import android.net.Uri
import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.repository.LocalFileRepositoryContract
import javax.inject.Inject

class ImportFileUseCase @Inject constructor(
    private val localFileRepository: LocalFileRepositoryContract
) : ImportFileUseCaseContract {

    /**
     * Uri が示す場所に格納されているファイルをアプリのプライベート領域に import する
     *
     * @return
     */
    override suspend operator fun invoke(uri: Uri): PdfEntity {
        return localFileRepository.add(uri)
    }
}