package br.com.thefirst.fiap.spaceconnect.features.nasa.data.repository

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.AstronomyLocalDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toDomain
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote.AstronomyRemoteDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AstronomyRepositoryImpl(
    private val remoteDataSource: AstronomyRemoteDataSource,
    private val localDataSource: AstronomyLocalDataSource
) : AstronomyRepository {

    override suspend fun getAstronomyListByStartDateAndEndDate(
        startDate: String,
        endDate: String,
        forceRefresh: Boolean
    ): Resource<List<Astronomy>> {
        Log.d("AstronomyRepo", "getAstronomyListByStartDateAndEndDate - startDate: $startDate, endDate: $endDate")
        return try {
            if (!forceRefresh) {
                val cachedList = localDataSource.getCacheFromDB().firstOrNull()
                if (!cachedList.isNullOrEmpty() && isCacheValidForDateRange(cachedList, startDate, endDate)) {

                    val syncedList = syncWithFavorites(cachedList)
                    return Resource.Success(syncedList)
                }
            }

            val response = remoteDataSource.getAstronomyListByDate(startDate, endDate)
            val astronomyList = response.map { it.toDomain() }

            localDataSource.insertAllAstronomy(response.map { it.toEntity() })

            val syncedList = syncWithFavorites(astronomyList)

            Resource.Success(syncedList)

        } catch (e: Exception) {
            val cachedList = localDataSource.getCacheFromDB().firstOrNull()
            if (!cachedList.isNullOrEmpty()) {
                val syncedList = syncWithFavorites(cachedList)
                return Resource.Success(syncedList)
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

    private suspend fun syncWithFavorites(astronomyList: List<Astronomy>): List<Astronomy> {
        if (astronomyList.isEmpty()) return emptyList()

        val favoritesList = localDataSource.getFavoriteAstronomyList().firstOrNull() ?: emptyList()
        val favoriteDates = favoritesList.map { it.date }.toSet()

        return astronomyList.map { astronomy ->
            astronomy.copy(favorite = favoriteDates.contains(astronomy.date))
        }
    }

    override fun getCachedFromDB(): Flow<List<Astronomy>> {
        return localDataSource.getCacheFromDB().map { cachedList ->
            syncWithFavorites(cachedList)
        }
    }

    override suspend fun getAstronomyByDate(date: String): Resource<Astronomy> {
        return try {
            val favoriteEntity = localDataSource.getFavoriteByDate(date)

            if (favoriteEntity != null) {
                return Resource.Success(favoriteEntity.copy(favorite = true))
            }

            val cacheEntity = localDataSource.getAstronomyByDateFromCache(date)

            if (cacheEntity != null) {
                val isFav = localDataSource.isFavorite(date)
                return Resource.Success(cacheEntity.toDomain().copy(favorite = isFav))
            }

            Resource.Error("Astronomia não encontrada para a data: $date")

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