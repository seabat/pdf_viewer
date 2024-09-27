package dev.seabat.android.composepdfviewer.domain.usecase

import dev.seabat.android.composepdfviewer.domain.entity.PdfListEntity
import dev.seabat.android.composepdfviewer.domain.entity.PdfResourceEntity
import dev.seabat.android.composepdfviewer.domain.repository.RecentListRepositoryContract
import dev.seabat.android.composepdfviewer.utils.getNowTimeStamp
import javax.inject.Inject

class AddRecentListUseCase @Inject constructor(
    private val recentListRepository: RecentListRepositoryContract
) : AddRecentListUseCaseContract {
    override suspend fun invoke(pdf: PdfResourceEntity): UseCaseResult<PdfListEntity> = try {
        recentListRepository.add(
            pdf.copy(openedDateString = getNowTimeStamp())
        )
        UseCaseResult.Success(recentListRepository.fetch())
    } catch (e: Exception) {
        UseCaseResult.Failure(e)
    }
}