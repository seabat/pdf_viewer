package dev.seabat.android.composepdfviewer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.composepdfviewer.domain.repository.RecentnessListRepositoryContract
import dev.seabat.android.composepdfviewer.domain.usecase.FetchRecentnessListUseCase
import dev.seabat.android.composepdfviewer.domain.usecase.FetchRecentnessListUseCaseContract
import dev.seabat.android.composepdfviewer.repository.RecentnessListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRecentnessListRepository(repository: RecentnessListRepository): RecentnessListRepositoryContract
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Singleton
    @Binds
    abstract fun bindFetchRecentnessListUseCase(useCase: FetchRecentnessListUseCase): FetchRecentnessListUseCaseContract
}
