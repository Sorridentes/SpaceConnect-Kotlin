package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.thefirst.fiap.spaceconnect.common.data.local.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AstronomyOnboardingViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()

    init {
        sessionManager.isOnboardingCompleted()
            .onEach { completed ->
                _isOnboardingCompleted.value = completed
            }
            .launchIn(viewModelScope)
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            sessionManager.setOnboardingCompleted(true)
            _isOnboardingCompleted.value = true
        }
    }
}