package br.com.thefirst.fiap.spaceconnect.features.nasa.data.repository

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.toDomain
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote.AstronomyRemoteDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository

class AstronomyRepositoryImpl(
    private val remoteDataSource: AstronomyRemoteDataSource
) : AstronomyRepository {
    override suspend fun getAstronomyByDate(
        startDate: String,
        endDate: String
    ): Resource<List<Astronomy>> {
        return try {
            val response = remoteDataSource.getAstronomyByDate(startDate, endDate)
            Resource.Success(response.map { it.toDomain() })

        }catch (e: Exception){
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }

}