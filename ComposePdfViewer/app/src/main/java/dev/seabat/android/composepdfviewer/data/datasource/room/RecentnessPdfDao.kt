package dev.seabat.android.composepdfviewer.data.datasource.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecentnessPdfDao {
    @Query("SELECT * FROM RecentnessPdf")
    fun getAll(): List<RecentnessPdf>

    @Query("SELECT * FROM RecentnessPdf WHERE path = :path")
    fun loadAllByIds(path: IntArray): RecentnessPdf

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPdf(vararg pdf: RecentnessPdf)

    @Update
    fun updatePdf(vararg pdf: RecentnessPdf)

    @Delete
    fun delete(user: RecentnessPdf)
}