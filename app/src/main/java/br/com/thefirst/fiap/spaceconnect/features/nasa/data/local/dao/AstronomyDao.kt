package br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AstronomyDao {

    // ========== Tabela Principal (Cache) ==========

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAstronomy(astronomyList: List<AstronomyEntity>)

    @Query("SELECT * FROM astronomy_table ORDER BY createdAt DESC")
    fun getAllAstronomy(): Flow<List<AstronomyEntity>>

    @Query("SELECT * FROM astronomy_table WHERE date = :date")
    suspend fun getAstronomyByDate(date: String): AstronomyEntity?

    @Query("DELETE FROM astronomy_table")
    suspend fun deleteAllAstronomy()

    @Query("DELETE FROM astronomy_table WHERE date = :date")
    suspend fun deleteAstronomyByDate(date: String)

    @Query("SELECT COUNT(*) FROM astronomy_table")
    suspend fun getCount(): Int

    @Query("""
        DELETE FROM astronomy_table 
        WHERE createdAt NOT IN (
            SELECT createdAt FROM astronomy_table 
            ORDER BY createdAt DESC LIMIT :limit
        )
    """)
    suspend fun keepOnlyLastNItems(limit: Int)

    // ========== Tabela de Favoritos (Permanente) ==========

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: AstronomyFavoriteEntity)

    @Query("DELETE FROM favorite_astronomy_table WHERE date = :date")
    suspend fun deleteFavoriteByDate(date: String)

    @Query("SELECT * FROM favorite_astronomy_table ORDER BY createdAt DESC")
    fun getAllFavorites(): Flow<List<AstronomyFavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_astronomy_table WHERE date = :date)")
    suspend fun isFavorite(date: String): Boolean

    @Query("SELECT COUNT(*) FROM favorite_astronomy_table")
    suspend fun getFavoritesCount(): Int
}