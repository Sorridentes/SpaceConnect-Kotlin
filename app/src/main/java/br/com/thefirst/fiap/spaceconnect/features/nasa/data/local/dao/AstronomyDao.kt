package br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AstronomyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAstronomy(astronomyList: List<AstronomyEntity>)

    @Query("SELECT * FROM astronomy_table ORDER BY createdAt DESC")
    fun getAllAstronomy(): Flow<List<AstronomyEntity>>

    @Query("SELECT * FROM astronomy_table WHERE date = :date")
    suspend fun getAstronomyByDate(date: String): AstronomyEntity?

    @Query("SELECT * FROM astronomy_table WHERE favorite = 1 ORDER BY createdAt DESC")
    suspend fun getFavoriteAstronomyList(): List<AstronomyEntity>

    @Query("UPDATE astronomy_table SET favorite = :favorite WHERE date = :date")
    suspend fun updateFavoriteStatus(date: String, favorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAstronomy(astronomy: AstronomyEntity)

    @Query("DELETE FROM astronomy_table")
    suspend fun deleteAllAstronomy()

    @Query("DELETE FROM astronomy_table WHERE date = :date")
    suspend fun deleteAstronomyByDate(date: String)

    @Query("SELECT COUNT(*) FROM astronomy_table")
    suspend fun getCount(): Int

    @Query("DELETE FROM astronomy_table WHERE createdAt NOT IN (SELECT createdAt FROM astronomy_table ORDER BY createdAt DESC LIMIT :limit)")
    suspend fun keepOnlyLastNItems(limit: Int)
}