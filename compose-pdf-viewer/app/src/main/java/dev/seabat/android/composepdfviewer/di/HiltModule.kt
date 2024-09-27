package dev.seabat.android.composepdfviewer.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.composepdfviewer.data.repository.FavoriteListRepository
import dev.seabat.android.composepdfviewer.data.repository.LocalFileRepository
import dev.seabat.android.composepdfviewer.data.repository.PdfMetadataRepository
import dev.seabat.android.composepdfviewer.data.repository.RecentListRepository
import dev.seabat.android.composepdfviewer.domain.repository.FavoriteListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.repository.LocalFileRepositoryContract
import dev.seabat.android.composepdfviewer.domain.repository.PdfMetadataRepositoryContract
import dev.seabat.android.composepdfviewer.domain.repository.RecentListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.usecase.AddFavoriteUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.AddFavoriteUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.AddRecentListUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.AddRecentListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.CreatePdfRendererUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.CreatePdfRendererUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.DeleteFavoriteUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.DeleteFavoriteUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.DeleteFileUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.DeleteFileUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.ExtractPageCountUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.ExtractPageCountUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.ExtractPdfTitleUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.ExtractPdfTitleUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.FetchFavoriteListUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.FetchFavoriteListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.FetchFileListUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.FetchFileListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.FetchRecentListUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.FetchRecentListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.ImportFileUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.ImportFileUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.ImportSampleUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.ImportSampleUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.RendererPdfUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.RendererPdfUseCaseContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFavoriteListRepository(
        repository: FavoriteListRepository
    ): FavoriteListRepositoryContract

    @Singleton
    @Binds
    abstract fun bindLocalFileRepository(
        repository: LocalFileRepository
    ): LocalFileRepositoryContract

    @Singleton
    @Binds
    abstract fun bindPdfMetadataRepository(
        repository: PdfMetadataRepository
    ): PdfMetadataRepositoryContract

    @Singleton
    @Binds
    abstract fun bindRecentListRepository(
        repository: RecentListRepository
    ): RecentListRepositoryContract
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindAddFavoriteUseCase(useCase: AddFavoriteUseCase): AddFavoriteUseCaseContract

    @Singleton
    @Binds
    abstract fun bindAddRecentListUseCase(
        useCase: AddRecentListUseCase
    ): AddRecentListUseCaseContract

    @Singleton
    @Binds
    abstract fun bindAddCreatePdfRendererUseCase(
        useCase: CreatePdfRendererUseCase
    ): CreatePdfRendererUseCaseContract

    @Singleton
    @Binds
    abstract fun bindDeleteFavoriteUseCase(
        useCase: DeleteFavoriteUseCase
    ): DeleteFavoriteUseCaseContract

    @Singleton
    @Binds
    abstract fun bindDeleteFiletUseCase(useCase: DeleteFileUseCase): DeleteFileUseCaseContract

    @Singleton
    @Binds
    abstract fun bindExtractPageCountUseCase(
        useCase: ExtractPageCountUseCase
    ): ExtractPageCountUseCaseContract

    @Singleton
    @Binds
    abstract fun bindExtractPdfTitleUseCase(
        useCase: ExtractPdfTitleUseCase
    ): ExtractPdfTitleUseCaseContract

    @Singleton
    @Binds
    abstract fun bindFetchFavoriteListUseCase(
        useCase: FetchFavoriteListUseCase
    ): FetchFavoriteListUseCaseContract

    @Singleton
    @Binds
    abstract fun bindFetchFileListUseCase(
        useCase: FetchFileListUseCase
    ): FetchFileListUseCaseContract

    @Singleton
    @Binds
    abstract fun bindFetchRecentListUseCase(
        useCase: FetchRecentListUseCase
    ): FetchRecentListUseCaseContract

    @Singleton
    @Binds
    abstract fun bindImportFileUseCase(useCase: ImportFileUseCase): ImportFileUseCaseContract

    @Singleton
    @Binds
    abstract fun bindImportSampleUseCase(useCase: ImportSampleUseCase): ImportSampleUseCaseContract

    @Singleton
    @Binds
    abstract fun bindRendererPdfUseCase(useCase: RendererPdfUseCase): RendererPdfUseCaseContract
}

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    @ActivityScoped
    fun provideContext(@ActivityContext context: Context): Context = context
}
