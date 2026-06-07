package br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyResponse

class AstronomyRemoteDataSourceImpl(
    private val api: AstronomyApi
): AstronomyRemoteDataSource {
    override suspend fun getAstronomyListByDate(
        startDate: String,
        endDate: String
    ): List<AstronomyResponse> {
        return api.getAstronomyListByDate(startDate = startDate, endDate = endDate)
    }
}