package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.common.UiState
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase.GetAstronomyByDateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AstronomyDetailViewModel(
    private val getAstronomyByDateUseCase: GetAstronomyByDateUseCase,
    private val repository: AstronomyRepository
) : ViewModel() {

    private val _astronomyState = MutableStateFlow<UiState<Astronomy>>(UiState.Initial)
    val astronomyState: StateFlow<UiState<Astronomy>> = _astronomyState.asStateFlow()

    private val _favoriteState = MutableStateFlow<UiState<Boolean>>(UiState.Initial)
    val favoriteState: StateFlow<UiState<Boolean>> = _favoriteState.asStateFlow()

    fun loadAstronomy(date: String) {
        viewModelScope.launch {
            _astronomyState.value = UiState.Loading
            when (val result = getAstronomyByDateUseCase.invoke(date)) {
                is Resource.Success -> {
                    _astronomyState.value = UiState.Success(result.data)
                }
                is Resource.Error -> {
                    _astronomyState.value = UiState.Error(result.message)
                }
                is Resource.Loading -> {
                    _astronomyState.value = UiState.Loading
                }
            }
        }
    }

    fun toggleFavorite(astronomy: Astronomy) {
        viewModelScope.launch {
            repository.toggleFavorite(astronomy)
            val newFavoriteStatus = !astronomy.favorite
            _favoriteState.value = UiState.Success(newFavoriteStatus)

            // Atualiza o estado da astronomia para refletir a mudança na UI
            val currentState = _astronomyState.value
            if (currentState is UiState.Success) {
                _astronomyState.value = UiState.Success(currentState.data.copy(favorite = newFavoriteStatus))
            }
        }
    }
}