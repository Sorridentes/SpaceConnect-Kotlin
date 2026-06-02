package br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyResponse

interface AstronomyRemoteDataSource {
    suspend fun getAstronomyByDate(startDate: String, endDate: String): List<AstronomyResponse>
}