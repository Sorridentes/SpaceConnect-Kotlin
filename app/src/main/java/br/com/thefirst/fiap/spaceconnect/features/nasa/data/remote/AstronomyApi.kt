package br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AstronomyApi {
    @GET("planetary/apod")
    suspend fun getAstronomyListByDate(
        @Query("api_key") apiKey: String = "DEMO_KEY",
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): List<AstronomyResponse>
}