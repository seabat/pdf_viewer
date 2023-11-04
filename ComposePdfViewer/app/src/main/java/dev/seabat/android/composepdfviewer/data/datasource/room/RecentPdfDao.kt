package dev.seabat.android.composepdfviewer.data.datasource.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecentPdfDao {
    @Query("SELECT * FROM RecentPdf ORDER BY opened_date DESC")
    fun getAll(): List<RecentPdf>

    @Query("SELECT * FROM RecentPdf WHERE path = :path")
    fun loadAllByIds(path: IntArray): RecentPdf

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPdf(vararg pdf: RecentPdf)

    @Update
    fun updatePdf(vararg pdf: RecentPdf)

    @Delete
    fun delete(user: RecentPdf)
}