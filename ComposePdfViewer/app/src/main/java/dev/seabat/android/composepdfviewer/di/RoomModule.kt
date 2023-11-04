package dev.seabat.android.composepdfviewer.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.seabat.android.composepdfviewer.data.datasource.room.PdfDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PdfDatabase::class.java, "pdf_database").build()

    @Singleton
    @Provides
    fun provideFavoritePdfDao(db: PdfDatabase) = db.favoritePdfDao()

    @Singleton
    @Provides
    fun provideRecentPdfDao(db: PdfDatabase) = db.recentPdfDao()
}