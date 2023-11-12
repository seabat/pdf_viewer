package dev.seabat.android.composepdfviewer.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritePdf::class, RecentPdf::class], version = 1, exportSchema = false)
abstract class PdfDatabase : RoomDatabase() {
    abstract fun favoritePdfDao(): FavoritePdfDao
    abstract fun recentPdfDao(): RecentPdfDao
}