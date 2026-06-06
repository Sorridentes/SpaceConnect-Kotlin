package br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import kotlinx.coroutines.flow.Flow

interface AstronomyRepository {
    suspend fun getAstronomyByDate(
        startDate: String,
        endDate: String,
        forceRefresh: Boolean = false
    ): Resource<List<Astronomy>>

    fun getCachedAstronomy(): Flow<List<Astronomy>>

    fun getFavoriteAstronomy(): Flow<List<Astronomy>>

    suspend fun toggleFavorite(astronomy: Astronomy)
}