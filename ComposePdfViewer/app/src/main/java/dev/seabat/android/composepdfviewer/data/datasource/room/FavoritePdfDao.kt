package dev.seabat.android.composepdfviewer.data.datasource.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoritePdfDao {
    @Query("SELECT * FROM FavoritePdf ORDER BY title ASC")
    fun getAll(): List<FavoritePdf>

    @Query("SELECT * FROM FavoritePdf WHERE path = :path")
    fun loadAllByIds(path: IntArray): FavoritePdf

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPdf(vararg pdf: FavoritePdf)

    @Update
    fun updatePdf(vararg pdf: FavoritePdf)

    @Delete
    fun delete(user: FavoritePdf)
}