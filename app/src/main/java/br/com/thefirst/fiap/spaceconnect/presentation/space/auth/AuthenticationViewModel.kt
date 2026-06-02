package br.com.thefirst.fiap.spaceconnect.presentation.space.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.model.User
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.usecase.CreateUserUseCase
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.usecase.SignInUseCase
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.usecase.SignOutUseCase
import br.com.thefirst.fiap.spaceconnect.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _createUserState = MutableStateFlow<UiState<User>>(UiState.Initial)
    val createUserState: MutableStateFlow<UiState<User>> = _createUserState

    private val _signInState = MutableStateFlow<UiState<User>>(UiState.Initial)
    val signInState: MutableStateFlow<UiState<User>> = _signInState

    private val _signOutState = MutableStateFlow<UiState<Unit>>(UiState.Initial)
    val signOutState: MutableStateFlow<UiState<Unit>> = _signOutState

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: MutableStateFlow<User?> = _currentUser

    fun createUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _createUserState.value = UiState.Loading

            when (val result = createUserUseCase(name, email, password)) {
                is Resource.Success -> {
                    _createUserState.value = UiState.Success(result.data)
                    _currentUser.value = result.data
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
                    _currentUser.value = result.data
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
                    _currentUser.value = null
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
