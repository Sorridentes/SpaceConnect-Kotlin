package br.com.thefirst.fiap.spaceconnect.features.nasa.data.local

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.dao.AstronomyDao
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toDomain
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toFavoriteEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AstronomyLocalDataSourceImpl(
    private val dao: AstronomyDao
) : AstronomyLocalDataSource {

    // ========== Cache ==========

    override suspend fun insertAllAstronomy(astronomyList: List<AstronomyEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertAllAstronomy(astronomyList)
            dao.keepOnlyLastNItems(10)
        }
    }

    override fun getCacheFromDB(): Flow<List<Astronomy>> {
        return dao.getAllAstronomy()
            .flowOn(Dispatchers.IO)
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun getAstronomyByDateFromCache(date: String): AstronomyEntity? {
        return withContext(Dispatchers.IO) {
            dao.getAstronomyByDate(date)
        }
    }

    override suspend fun getCount(): Int {
        return withContext(Dispatchers.IO) {
            dao.getCount()
        }
    }

    // ========== Favoritos ==========

    override suspend fun addFavorite(astronomy: Astronomy) {
        withContext(Dispatchers.IO) {
            dao.insertFavorite(astronomy.toFavoriteEntity())
        }
    }

    override suspend fun removeFavorite(date: String) {
        withContext(Dispatchers.IO) {
            dao.deleteFavoriteByDate(date)
        }
    }

    override fun getFavoriteAstronomyList(): Flow<List<Astronomy>> {
        return dao.getAllFavorites()
            .flowOn(Dispatchers.IO)
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun isFavorite(date: String): Boolean {
        return withContext(Dispatchers.IO) {
            dao.isFavorite(date)
        }
    }

    override suspend fun getFavoriteByDate(date: String): Astronomy? {
        return withContext(Dispatchers.IO) {
            dao.getFavoriteByDate(date)?.toDomain()
        }
    }
}