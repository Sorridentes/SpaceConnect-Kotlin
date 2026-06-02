package br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy

interface AstronomyRepository {
    suspend fun getAstronomyByDate(startDate: String, endDate: String): Resource<List<Astronomy>>
}