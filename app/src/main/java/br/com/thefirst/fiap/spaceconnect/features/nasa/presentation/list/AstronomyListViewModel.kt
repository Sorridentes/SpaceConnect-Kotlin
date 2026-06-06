package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list

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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AstronomyListViewModel(
    private val getAstronomyByDateUseCase: GetAstronomyByDateUseCase,
    private val repository: AstronomyRepository
) : ViewModel() {

    private val _getAstronomyByDateState = MutableStateFlow<UiState<List<Astronomy>>>(UiState.Initial)
    val getAstronomyByDateState: StateFlow<UiState<List<Astronomy>>> = _getAstronomyByDateState.asStateFlow()

    private val _astronomyList = MutableStateFlow<List<Astronomy>>(emptyList())
    val astronomyList: StateFlow<List<Astronomy>> =_astronomyList.asStateFlow()

    private val _favoriteAstronomy = MutableStateFlow<List<Astronomy>>(emptyList())
    val favoriteAstronomy: StateFlow<List<Astronomy>> = _favoriteAstronomy.asStateFlow()

    init {
        repository.getCachedAstronomy()
            .onEach { cachedList ->
                _astronomyList.value = cachedList
            }
            .launchIn(viewModelScope)

        repository.getFavoriteAstronomy()
            .onEach { favoriteList ->
                _favoriteAstronomy.value = favoriteList
            }
            .launchIn(viewModelScope)
    }

    fun getAstronomyByDate(startDate: String, endDate: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _getAstronomyByDateState.value = UiState.Loading

            when (val result = getAstronomyByDateUseCase(startDate, endDate, forceRefresh)) {
                is Resource.Success -> {
                    _getAstronomyByDateState.value = UiState.Success(result.data)
                }
                is Resource.Error -> {
                    _getAstronomyByDateState.value = UiState.Error(result.message)
                }
                is Resource.Loading -> {
                    _getAstronomyByDateState.value = UiState.Loading
                }
            }
        }
    }

    fun toggleFavorite(astronomy: Astronomy) {
        viewModelScope.launch {
            repository.toggleFavorite(astronomy)
        }
    }
}