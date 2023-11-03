package dev.seabat.android.composepdfviewer.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecentnessPdf::class], version = 1, exportSchema = false)
abstract class PdfDatabase : RoomDatabase() {
    abstract fun recentnessPdfDao(): RecentnessPdfDao
}