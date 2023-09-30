package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.repository.RecentnessListRepositoryContract
import javax.inject.Inject

class AddRecentnessListUseCase @Inject constructor(
    private val recentnessListRepository: RecentnessListRepositoryContract
) : AddRecentnessListUseCaseContract {
    override suspend fun invoke(pdf: PdfEntity): UseCaseResult<PdfListEntity> {
        return try {
            recentnessListRepository.add(pdf)
            UseCaseResult.Success(recentnessListRepository.fetch())
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }
}