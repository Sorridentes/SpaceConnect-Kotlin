package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.thefirst.fiap.spaceconnect.common.data.local.SessionManager
import br.com.thefirst.fiap.spaceconnect.features.auth.presentation.auth.AuthenticationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AstronomySplashViewModel(
    private val sessionManager: SessionManager,
    private val authViewModel: AuthenticationViewModel
) : ViewModel() {

    private val _navigationTarget = MutableStateFlow<NavigationTarget?>(null)
    val navigationTarget: StateFlow<NavigationTarget?> = _navigationTarget.asStateFlow()

    sealed class NavigationTarget {
        object Onboarding : NavigationTarget()
        object Home : NavigationTarget()
        object Auth : NavigationTarget()
    }

    init {
        viewModelScope.launch {
            combine(
                sessionManager.isOnboardingCompleted(),
                authViewModel.isAuthReady,
                authViewModel.currentUserState
            ) { isOnboardingCompleted, isAuthReady, currentUser ->
                Triple(isOnboardingCompleted, isAuthReady, currentUser)
            }.collect { (isOnboardingCompleted, isAuthReady, currentUser) ->
                if (isAuthReady) {
                    _navigationTarget.value = when {
                        !isOnboardingCompleted -> NavigationTarget.Onboarding
                        currentUser != null -> NavigationTarget.Home
                        else -> NavigationTarget.Auth
                    }
                }
            }
        }
    }
}