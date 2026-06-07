package br.com.thefirst.fiap.spaceconnect.features.nasa.data.local

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import kotlinx.coroutines.flow.Flow

interface AstronomyLocalDataSource {
    // Cache
    suspend fun insertAllAstronomy(astronomyList: List<AstronomyEntity>)
    fun getCacheFromDB(): Flow<List<Astronomy>>
    suspend fun getAstronomyByDate(date: String): AstronomyEntity?
    suspend fun getCount(): Int

    // Favoritos
    suspend fun addFavorite(astronomy: Astronomy)
    suspend fun removeFavorite(date: String)
    fun getFavoriteAstronomyList(): Flow<List<Astronomy>>
    suspend fun isFavorite(date: String): Boolean
}