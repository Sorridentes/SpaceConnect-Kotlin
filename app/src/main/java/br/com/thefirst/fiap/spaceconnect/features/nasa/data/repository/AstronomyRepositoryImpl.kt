package br.com.thefirst.fiap.spaceconnect.features.nasa.data.repository

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.AstronomyLocalDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toDomain
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote.AstronomyRemoteDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AstronomyRepositoryImpl(
    private val remoteDataSource: AstronomyRemoteDataSource,
    private val localDataSource: AstronomyLocalDataSource
) : AstronomyRepository {

    override suspend fun getAstronomyByDate(
        startDate: String,
        endDate: String,
        forceRefresh: Boolean
    ): Resource<List<Astronomy>> {
        return try {
            if (!forceRefresh) {
                val cacheCount = localDataSource.getCount()
                if (cacheCount > 0) {
                    return Resource.Success(
                        localDataSource.getAllAstronomy().firstOrNull() ?: emptyList()
                    )
                }
            }

            val response = remoteDataSource.getAstronomyByDate(startDate, endDate)
            val astronomyList = response.map { it.toDomain() }

            localDataSource.insertAllAstronomy(astronomyList)

            Resource.Success(response.map { it.toDomain() })

        } catch (e: Exception) {
            val cachedList = getCachedAstronomy().firstOrNull()
            if (!cachedList.isNullOrEmpty()) {
                return Resource.Success(cachedList)
            }
            Resource.Error(e.message ?: "Erro ao carregar dados")
        }
    }

    override fun getCachedAstronomy(): Flow<List<Astronomy>> {
        return localDataSource.getAllAstronomy()
    }

    override fun getFavoriteAstronomy(): Flow<List<Astronomy>> {
        return localDataSource.getFavoriteAstronomy()
    }

    override suspend fun toggleFavorite(astronomy: Astronomy) {
        localDataSource.updateFavoriteStatus(astronomy.date, !astronomy.favorite)
    }


}