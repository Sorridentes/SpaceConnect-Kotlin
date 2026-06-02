package br.com.thefirst.fiap.spaceconnect.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.thefirst.fiap.spaceconnect.presentation.common.UiState
import br.com.thefirst.fiap.spaceconnect.presentation.space.auth.AuthenticationScreen
import br.com.thefirst.fiap.spaceconnect.presentation.space.auth.AuthenticationViewModel
import br.com.thefirst.fiap.spaceconnect.features.firebase.graphic.space.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authViewModel: AuthenticationViewModel = koinViewModel()

    val currentUser = authViewModel.currentUser.collectAsStateWithLifecycle()
    val createUserState by authViewModel.createUserState.collectAsState()
    val signInState by authViewModel.signInState.collectAsState()
    val signOutState by authViewModel.signOutState.collectAsState()

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
            HomeScreen(
                userName = currentUser.value?.name ?: "",
                onSignOut = { authViewModel.signOut() }
            )
        }

    }
}

