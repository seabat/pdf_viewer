package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.repository.RecentnessListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import javax.inject.Inject

class FetchRecentnessListUseCase @Inject constructor(
    private val recentnessListRepository: RecentnessListRepositoryContract
) : FetchRecentnessListUseCaseContract {
    override suspend operator fun invoke(): UseCaseResult<PdfListEntity> {
        return try {
            val recentnessList = recentnessListRepository.fetch()
            UseCaseResult.Success(recentnessList)
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }
}