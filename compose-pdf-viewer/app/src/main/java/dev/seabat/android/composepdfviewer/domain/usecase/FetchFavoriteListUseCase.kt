package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.repository.FavoriteListRepositoryContract
import javax.inject.Inject

class FetchFavoriteListUseCase @Inject constructor(
    private val favoriteListRepository: FavoriteListRepositoryContract
) : FetchFavoriteListUseCaseContract {
    override suspend operator fun invoke(): UseCaseResult<PdfListEntity> {
        return try {
            val favoriteList = favoriteListRepository.fetch()
            UseCaseResult.Success(favoriteList)
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }
}