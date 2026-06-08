// AppNavigation.kt
package br.com.thefirst.fiap.spaceconnect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.thefirst.fiap.spaceconnect.features.auth.presentation.auth.AuthenticationScreen
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.detail.AstronomyDetailScreen
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.favorite.AstronomyFavoritesScreen
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyListScreen
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.onboarding.AstronomyOnboardingScreen
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.splash.AstronomySplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        composable(AppRoutes.SPLASH) {
            AstronomySplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(AppRoutes.ONBOARDING) {
                        popUpTo(AppRoutes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToAuth = {
                    navController.navigate(AppRoutes.AUTH) {
                        popUpTo(AppRoutes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.ONBOARDING) {
            AstronomyOnboardingScreen(
                onNavigateToAuth = {
                    navController.navigate(AppRoutes.AUTH) {
                        popUpTo(AppRoutes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.AUTH) {
            AuthenticationScreen(
                onNavigateToHome = { user ->
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.AUTH) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.HOME) {
            AstronomyListScreen(
                onNavigateToDetail = { date ->
                    navController.navigate(AppRoutes.astronomyDetail(date))
                },
                onNavigateToFavorites = {
                    navController.navigate(AppRoutes.FAVORITES)
                },
                onSignOut = {
                    navController.navigate(AppRoutes.AUTH) {
                        popUpTo(AppRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = AppRoutes.DETAIL,
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            AstronomyDetailScreen(
                date = date,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.FAVORITES) {
            AstronomyFavoritesScreen(
                onNavigateToDetail = { date ->
                    navController.navigate(AppRoutes.astronomyDetail(date))
                },
                onNavigateToList = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.FAVORITES) { inclusive = true }
                    }
                },
                onSignOut = {
                    navController.navigate(AppRoutes.AUTH) {
                        popUpTo(AppRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}