package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.common.UiState
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase.GetAstronomyByDateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AstronomyListViewModel(
    private val getAstronomyByDateUseCase: GetAstronomyByDateUseCase,
) : ViewModel() {
    private val _getAstronomyByDateState =
        MutableStateFlow<UiState<List<Astronomy>>>(UiState.Initial)
    val getAstronomyByDateState: MutableStateFlow<UiState<List<Astronomy>>> =
        _getAstronomyByDateState

    fun getAstronomyByDate(startDate: String, endDate: String) {
        viewModelScope.launch {
            _getAstronomyByDateState.value = UiState.Loading

            when (val result = getAstronomyByDateUseCase(startDate, endDate)) {
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
}