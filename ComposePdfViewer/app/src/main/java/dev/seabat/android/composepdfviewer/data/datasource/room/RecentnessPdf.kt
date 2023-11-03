package dev.seabat.android.composepdfviewer.data.datasource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentnessPdf(
    @PrimaryKey val path: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "file_name") val fileName: String,
    @ColumnInfo(name = "size") val size: Long,
    @ColumnInfo(name = "date") val date: String
)