package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.repository.FavoriteListRepositoryContract
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoriteListRepository: FavoriteListRepositoryContract
) : AddFavoriteUseCaseContract {
    override suspend fun invoke(pdf: PdfEntity) {
        favoriteListRepository.add(pdf)
    }
}