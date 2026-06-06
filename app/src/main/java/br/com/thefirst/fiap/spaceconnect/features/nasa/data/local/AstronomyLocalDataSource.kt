package br.com.thefirst.fiap.spaceconnect.features.nasa.data.local

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.dao.AstronomyDao
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toDomain
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toEntity
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AstronomyLocalDataSource (
    private val dao: AstronomyDao
){
    fun getAllAstronomy(): Flow<List<Astronomy>>{
        return dao.getAllAstronomy().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getFavoriteAstronomy(): Flow<List<Astronomy>>{
        return dao.getFavoriteAstronomy().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getAstronomyByDate(date: String): Astronomy?{
        return dao.getAstronomyByDate(date)?.toDomain()
    }

    suspend fun insertAstronomy(astronomy: Astronomy){
        withContext(Dispatchers.IO){
            dao.insertAstronomy(astronomy.toEntity())
            dao.keepOnlyLastNItems(10)
        }
    }

    suspend fun insertAllAstronomy(astronomyList: List<Astronomy>) {
        withContext(Dispatchers.IO) {
            dao.insertAllAstronomy(astronomyList.map { it.toEntity() })
            dao.keepOnlyLastNItems(10)
        }
    }

    suspend fun updateFavoriteStatus(date: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            dao.updateFavoriteStatus(date, isFavorite)
        }
    }

    suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            dao.deleteAllAstronomy()
        }
    }

    suspend fun getCount(): Int {
        return withContext(Dispatchers.IO) {
            dao.getCount()
        }
    }
}
