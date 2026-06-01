package br.com.thefirst.fiap.spaceconnect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.thefirst.fiap.spaceconnect.presentation.home.HomeScreen
import br.com.thefirst.fiap.spaceconnect.presentation.space.auth.AuthenticationScreen
import br.com.thefirst.fiap.spaceconnect.presentation.space.auth.AuthenticationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authViewModel: AuthenticationViewModel = koinViewModel()
    val currentUser = authViewModel.currentUser.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
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

    NavHost(
        navController = navController,
        startDestination = if (currentUser != null) AppRoutes.HOME else AppRoutes.AUTH
    ){
        composable(AppRoutes.AUTH) {
            AuthenticationScreen(viewModel = authViewModel)
        }
        composable(AppRoutes.HOME) {
            HomeScreen(viewModel = authViewModel)
        }

    }
}

