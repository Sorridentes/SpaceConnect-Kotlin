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
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyListScreen
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthenticationViewModel = koinViewModel()

    val currentUser by authViewModel.currentUserState.collectAsState()

    val startDestination = if (currentUser != null) AppRoutes.HOME else AppRoutes.AUTH


    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppRoutes.AUTH) {
            AuthenticationScreen(
                onNavigateToHome = { user ->
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.AUTH) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AppRoutes.HOME) {
            AstronomyListScreen(
                user = currentUser,
                onNavigateToDetail = { astronomy ->
//                    navController.navigate(AppRoutes.DETAIL)
                },
                onNavigateToFavorites = {
//                    navController.navigate(AppRoutes.FAVORITES)
                },
                onSignOut = {
                    authViewModel.signOut()
                    navController.navigate(AppRoutes.AUTH) {
                        popUpTo(AppRoutes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )

        }
    }
}


