package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.repository.FavoriteListRepositoryContract
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoriteListRepository: FavoriteListRepositoryContract
) : DeleteFavoriteUseCaseContract {
    override suspend fun invoke(pdf: PdfEntity) {
        favoriteListRepository.remove(pdf)
    }
}