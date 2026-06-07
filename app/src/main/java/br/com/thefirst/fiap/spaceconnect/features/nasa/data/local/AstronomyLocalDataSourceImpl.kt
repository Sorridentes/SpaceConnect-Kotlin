package br.com.thefirst.fiap.spaceconnect.features.nasa.data.local

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.dao.AstronomyDao
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toDomain
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AstronomyLocalDataSourceImpl(
    private val dao: AstronomyDao
) : AstronomyLocalDataSource {

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

    override suspend fun getAstronomyByDate(date: String): AstronomyEntity? {
        return withContext(Dispatchers.IO) {
            dao.getAstronomyByDate(date)
        }
    }

    override suspend fun getFavoriteAstronomyList(): List<AstronomyEntity> {
        return withContext(Dispatchers.IO) {
            dao.getFavoriteAstronomyList()
        }
    }

    override suspend fun toggleFavorite(date: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            dao.updateFavoriteStatus(date, isFavorite)
        }
    }

    override suspend fun getCount(): Int {
        return withContext(Dispatchers.IO) {
            dao.getCount()
        }
    }
}