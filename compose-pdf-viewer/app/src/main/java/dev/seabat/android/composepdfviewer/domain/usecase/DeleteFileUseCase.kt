package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.domain.repository.FavoriteListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.repository.LocalFileRepositoryContract
import dev.seabat.android.composepdfviewer.domain.repository.RecentListRepositoryContract
import javax.inject.Inject

class DeleteFileUseCase @Inject constructor(
    private val localFileRepository: LocalFileRepositoryContract,
    private val favoriteListRepository: FavoriteListRepositoryContract,
    private val recentListRepository: RecentListRepositoryContract
) : DeleteFileUseCaseContract {
    override suspend fun invoke(pdf: PdfResourceEntity) {
        localFileRepository.remove(pdf.fileName)
        favoriteListRepository.remove(pdf)
        recentListRepository.remove(pdf)
    }
}