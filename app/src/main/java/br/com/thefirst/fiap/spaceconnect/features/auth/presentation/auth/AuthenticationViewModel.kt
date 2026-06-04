package br.com.thefirst.fiap.spaceconnect.features.auth.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.usecase.CreateUserUseCase
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.usecase.SignInUseCase
import br.com.thefirst.fiap.spaceconnect.common.UiState
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.repository.AuthRepository
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.usecase.SignOutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _createUserState = MutableStateFlow<UiState<User>>(UiState.Initial)
    val createUserState: StateFlow<UiState<User>> = _createUserState.asStateFlow()

    private val _signInState = MutableStateFlow<UiState<User>>(UiState.Initial)
    val signInState: StateFlow<UiState<User>> = _signInState.asStateFlow()

    private val _signOutState = MutableStateFlow<UiState<Unit>>(UiState.Initial)
    val signOutState: StateFlow<UiState<Unit>> = _signOutState.asStateFlow()

    private val _currentUserState = MutableStateFlow<User?>(null)
    val currentUserState: StateFlow<User?> = _currentUserState.asStateFlow()

    init {
        authRepository.getCurrentUser()
            .onEach { user ->
                _currentUserState.value = user
            }
            .launchIn(viewModelScope)
    }

    fun createUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _createUserState.value = UiState.Loading

            when (val result = createUserUseCase(name, email, password)) {
                is Resource.Success -> {
                    _createUserState.value = UiState.Success(result.data)
                }

                is Resource.Error -> {
                    _createUserState.value = UiState.Error(result.message)

                }

                is Resource.Loading -> {
                    _createUserState.value = UiState.Loading

                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = UiState.Loading

            when (val result = signInUseCase(email, password)) {
                is Resource.Success -> {
                    _signInState.value = UiState.Success(result.data)
                }

                is Resource.Error -> {
                    _signInState.value = UiState.Error(result.message)
                }

                is Resource.Loading -> {
                    _signInState.value = UiState.Loading
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _signOutState.value = UiState.Loading

            when (val result = signOutUseCase()) {
                is Resource.Success -> {
                    _signOutState.value = UiState.Success(result.data)
                }

                is Resource.Error -> {
                    _signOutState.value = UiState.Error(result.message)
                }

                is Resource.Loading -> {
                    _signOutState.value = UiState.Loading
                }
            }
        }
    }

}
