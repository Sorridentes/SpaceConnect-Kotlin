package br.com.thefirst.fiap.spaceconnect.features.nasa.data.repository

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.AstronomyLocalDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toDomain
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote.AstronomyRemoteDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AstronomyRepositoryImpl(
    private val remoteDataSource: AstronomyRemoteDataSource,
    private val localDataSource: AstronomyLocalDataSource
) : AstronomyRepository {

    override suspend fun getAstronomyListByStartDateAndEndDate(
        startDate: String,
        endDate: String,
        forceRefresh: Boolean
    ): Resource<List<Astronomy>> {
        return try {
            if (!forceRefresh) {
                val cachedList = localDataSource.getCacheFromDB().firstOrNull()
                if (!cachedList.isNullOrEmpty() && isCacheValidForDateRange(cachedList, startDate, endDate)) {
                    return Resource.Success(cachedList)
                }
            }

            val response = remoteDataSource.getAstronomyListByDate(startDate, endDate)
            val astronomyList = response.map { it.toDomain() }

            localDataSource.insertAllAstronomy(response.map { it.toEntity() })

            Resource.Success(astronomyList)

        } catch (e: Exception) {
            val cachedList = localDataSource.getCacheFromDB().firstOrNull()
            if (!cachedList.isNullOrEmpty()) {
                return Resource.Success(cachedList)
            }
            Resource.Error(e.message ?: "Erro ao carregar dados")
        }
    }

    private fun isCacheValidForDateRange(cachedList: List<Astronomy>, startDate: String, endDate: String): Boolean {
        if (cachedList.isEmpty()) return false

        val dates = cachedList.map { it.date }.sorted()
        val oldestCachedDate = dates.firstOrNull() ?: return false
        val newestCachedDate = dates.lastOrNull() ?: return false

        return oldestCachedDate <= startDate && newestCachedDate >= endDate
    }

    private suspend fun updateFavoriteStatus(list: List<Astronomy>): List<Astronomy> {
        return list.map { astronomy ->
            astronomy.copy(favorite = localDataSource.isFavorite(astronomy.date))
        }
    }

    override fun getCachedFromDB(): Flow<List<Astronomy>> {
        return localDataSource.getCacheFromDB()
    }

    override suspend fun getAstronomyByDate(date: String): Resource<Astronomy> {
        return try {
            val entity = localDataSource.getAstronomyByDate(date)
            if (entity != null) {
                val isFav = localDataSource.isFavorite(date)
                Resource.Success(entity.toDomain().copy(favorite = isFav))
            } else {
                Resource.Error("Astronomia não encontrada para a data: $date")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao carregar dados")
        }
    }

    override fun getFavoriteAstronomyList(): Flow<List<Astronomy>> {
        return localDataSource.getFavoriteAstronomyList()
    }

    override suspend fun toggleFavorite(astronomy: Astronomy) {
        if (astronomy.favorite) {
            localDataSource.removeFavorite(astronomy.date)
        } else {
            localDataSource.addFavorite(astronomy)
        }
    }
}