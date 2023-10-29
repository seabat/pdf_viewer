package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.repository.LocalFileRepositoryContract
import javax.inject.Inject

class ImportSampleUseCase @Inject constructor(
    private val localFileRepository: LocalFileRepositoryContract
) : ImportSampleUseCaseContract {

    /**
     * サンプル PDF ファイルをアプリのプライベート領域に import する
     *
     * @return
     */
    override suspend operator fun invoke(): PdfEntity {
        return localFileRepository.importAssetsFile()
    }
}