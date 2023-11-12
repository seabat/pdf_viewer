package dev.seabat.android.composepdfviewer.data.datasource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritePdf(
    @PrimaryKey val path: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "file_name") val fileName: String,
    @ColumnInfo(name = "size") val size: Long,
    @ColumnInfo(name = "imported_date") val importedDate: String,
    @ColumnInfo(name = "opened_date") val openedDate: String
)