package br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase

import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository
import kotlinx.coroutines.flow.Flow

class GetCachedFromDB(
    private val repository: AstronomyRepository
) {
    operator fun invoke(): Flow<List<Astronomy>>{
        return repository.getCachedFromDB()
    }
}