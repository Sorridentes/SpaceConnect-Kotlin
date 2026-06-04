package br.com.thefirst.fiap.spaceconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase.GetAstronomyByDateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestNasaViewModel(
    private val getAstronomyUseCase: GetAstronomyByDateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NasaUiState())
    val uiState: StateFlow<NasaUiState> = _uiState.asStateFlow()

    fun fetchAstronomy(startDate: String, endDate: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = getAstronomyUseCase(startDate, endDate)

            when (result) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        astronomyList = result.data,
                        resultText = formatResults(result.data)
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message ?: "Erro desconhecido"
                    )
                }
                is Resource.Loading ->{
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    fun updateDates(startDate: String, endDate: String) {
        _uiState.value = _uiState.value.copy(
            startDate = startDate,
            endDate = endDate
        )
    }

    private fun formatResults(astronomyList: List<Astronomy>): String {
        // mesma função do exemplo anterior
        return if (astronomyList.isEmpty()) {
            "Nenhum dado encontrado para o período selecionado."
        } else {
            "✅ Encontrados ${astronomyList.size} registros"
        }
    }
}

data class NasaUiState(
    val startDate: String = "2024-01-01",
    val endDate: String = "2024-01-03",
    val isLoading: Boolean = false,
    val astronomyList: List<Astronomy> = emptyList(),
    val resultText: String = "",
    val error: String? = null
)
