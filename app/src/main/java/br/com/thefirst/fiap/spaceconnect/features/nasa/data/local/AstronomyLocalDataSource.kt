package br.com.thefirst.fiap.spaceconnect.features.nasa.data.local

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import kotlinx.coroutines.flow.Flow

interface AstronomyLocalDataSource {
    suspend fun insertAllAstronomy(astronomyList: List<AstronomyEntity>)

    fun getCacheFromDB(): Flow<List<Astronomy>>

    suspend fun getAstronomyByDate(date: String): AstronomyEntity?

    suspend fun getFavoriteAstronomyList(): List<AstronomyEntity>

    suspend fun toggleFavorite(date: String, isFavorite: Boolean)

    suspend fun getCount(): Int
}