package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AstronomyFavoritesViewModel(
    private val repository: AstronomyRepository
) : ViewModel() {

    private val _favoritesList = MutableStateFlow<List<Astronomy>>(emptyList())
    val favoritesList: StateFlow<List<Astronomy>> = _favoritesList.asStateFlow()

    init {
        repository.getFavoriteAstronomyList()
            .onEach { favorites ->
                _favoritesList.value = favorites
            }
            .launchIn(viewModelScope)
    }

    fun removeFavorite(astronomy: Astronomy) {
        viewModelScope.launch {
            repository.toggleFavorite(astronomy)
        }
    }

    fun refreshFavorites() {
        viewModelScope.launch {
            repository.getFavoriteAstronomyList()
                .onEach { favorites ->
                    _favoritesList.value = favorites
                }
                .launchIn(viewModelScope)
        }
    }
}