package dev.seabat.android.composepdfviewer.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.composepdfviewer.data.datasource.room.PdfDatabase
import dev.seabat.android.composepdfviewer.data.repository.LocalFileRepository
import dev.seabat.android.composepdfviewer.data.repository.PdfMetadataRepository
import dev.seabat.android.composepdfviewer.data.repository.RecentnessListRepository
import dev.seabat.android.composepdfviewer.domain.repository.LocalFileRepositoryContract
import dev.seabat.android.composepdfviewer.domain.repository.PdfMetadataRepositoryContract
import dev.seabat.android.composepdfviewer.domain.repository.RecentnessListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.usecase.AddRecentnessListUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.AddRecentnessListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.ExtractPdfTitleUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.ExtractPdfTitleUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.FetchFileListUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.FetchFileListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.FetchRecentnessListUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.FetchRecentnessListUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.ImportFileUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.ImportFileUseCaseContract
import dev.seabat.android.composepdfviewer.domain.usecase.ImportSampleUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.ImportSampleUseCaseContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PdfDatabase::class.java, "recentness_database").build()

    @Singleton
    @Provides
    fun provideRecentnessPdfDao(db: PdfDatabase) = db.recentnessPdfDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindPdfMetaDataRepository(repository: LocalFileRepository): LocalFileRepositoryContract

    @Singleton
    @Binds
    abstract fun bindPdfMetadataRepository(repository: PdfMetadataRepository): PdfMetadataRepositoryContract

    @Singleton
    @Binds
    abstract fun bindRecentnessListRepository(repository: RecentnessListRepository): RecentnessListRepositoryContract
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Singleton
    @Binds
    abstract fun bindAddRecentnessListUseCase(useCase: AddRecentnessListUseCase): AddRecentnessListUseCaseContract

    @Singleton
    @Binds
    abstract fun bindExtractPdfTitleUseCase(useCase: ExtractPdfTitleUseCase): ExtractPdfTitleUseCaseContract

    @Singleton
    @Binds
    abstract fun bindFetchFileListUseCase(useCase: FetchFileListUseCase): FetchFileListUseCaseContract

    @Singleton
    @Binds
    abstract fun bindFetchRecentnessListUseCase(useCase: FetchRecentnessListUseCase): FetchRecentnessListUseCaseContract

    @Singleton
    @Binds
    abstract fun bindImportFileUseCase(useCase: ImportFileUseCase): ImportFileUseCaseContract

    @Singleton
    @Binds
    abstract fun bindImportSampleUseCase(useCase: ImportSampleUseCase): ImportSampleUseCaseContract
}

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    @ActivityScoped
    fun provideContext(@ActivityContext context: Context): Context {
        return context
    }
}
