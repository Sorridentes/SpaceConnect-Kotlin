package br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyResponse

class AstronomyRemoteDataSourceImpl(
    private val api: AstronomyApi
): AstronomyRemoteDataSource {
    override suspend fun getAstronomyByDate(
        startDate: String,
        endDate: String
    ): List<AstronomyResponse> {
        return api.getAstronomyByDate(startDate = startDate, endDate = endDate)
    }
}