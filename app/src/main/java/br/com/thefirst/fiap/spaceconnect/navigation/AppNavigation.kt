package br.com.thefirst.fiap.spaceconnect.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.thefirst.fiap.spaceconnect.common.UiState
import br.com.thefirst.fiap.spaceconnect.features.auth.presentation.auth.AuthenticationScreen
import br.com.thefirst.fiap.spaceconnect.features.auth.presentation.auth.AuthenticationViewModel
import br.com.thefirst.fiap.spaceconnect.HomeScreen
import br.com.thefirst.fiap.spaceconnect.TestNasaViewModel
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyListScreen
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authViewModel: AuthenticationViewModel = koinViewModel()
    val astronomyListViewModel: AstronomyListViewModel = koinViewModel()

    val currentUser = authViewModel.currentUser.collectAsStateWithLifecycle()
    val createUserState by authViewModel.createUserState.collectAsState()
    val signInState by authViewModel.signInState.collectAsState()
    val signOutState by authViewModel.signOutState.collectAsState()
    val getAstronomyByDateState by astronomyListViewModel.getAstronomyByDateState.collectAsState()

    var startDate by remember { mutableStateOf("2025-01-01") }
    var endDate by remember { mutableStateOf("2025-01-10") }

    val context = LocalContext.current

    LaunchedEffect(currentUser) {
        if (currentUser.value != null) {
            navController.navigate(AppRoutes.HOME) {
                popUpTo(AppRoutes.AUTH) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(AppRoutes.AUTH) {
                popUpTo(AppRoutes.HOME) {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(createUserState) {
        when (createUserState) {
            is UiState.Success -> {
                Toast.makeText(
                    context,
                    "Usuário criado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UiState.Error -> {
                Toast.makeText(
                    context,
                    (createUserState as UiState.Error).message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            else -> {}
        }
    }

    LaunchedEffect(signInState) {
        when (signInState) {
            is UiState.Success -> {
                Toast.makeText(
                    context,
                    "Login realizado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UiState.Error -> {
                Toast.makeText(
                    context,
                    (signInState as UiState.Error).message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            else -> {}
        }
    }

    LaunchedEffect(signOutState) {
        when (signOutState) {
            is UiState.Success -> {
                Toast.makeText(
                    context,
                    "Logout realizado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UiState.Error -> {
                Toast.makeText(
                    context,
                    (signOutState as UiState.Error).message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        astronomyListViewModel.getAstronomyByDate(startDate, endDate)
    }

    NavHost(
        navController = navController,
        startDestination = if (currentUser.value != null) AppRoutes.HOME else AppRoutes.AUTH
    ) {
        composable(AppRoutes.AUTH) {
            AuthenticationScreen(
                authViewModel = authViewModel,
                onSignIn = { email, password ->
                    authViewModel.signIn(email, password)
                },
                onCreateAccount = { name, email, password ->
                    authViewModel.createUser(name, email, password)
                }
            )
        }
        composable(AppRoutes.HOME) {
            when (val state = getAstronomyByDateState) {
                UiState.Initial -> Unit

                UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is UiState.Error -> {
                    Text(
                        text = state.message,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is UiState.Success -> {


                    AstronomyListScreen(
                        astronomyList = state.data,
                        onNavigateToDetail = {},
                        onNavigateToFavorite = {},
                        onFavorite = { _ -> }
                    )
                }
            }
        }

    }
}

