package br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase

import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository

class ToggleFavoriteUseCase (
    private val repository: AstronomyRepository
){
    suspend operator fun invoke(astronomy: Astronomy) {
        return repository.toggleFavorite(astronomy)
    }
}