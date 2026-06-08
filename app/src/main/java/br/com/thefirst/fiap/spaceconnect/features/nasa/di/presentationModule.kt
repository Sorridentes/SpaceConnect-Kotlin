// features/nasa/di/presentationModule.kt (atualizado)
package br.com.thefirst.fiap.spaceconnect.features.nasa.di

import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.detail.AstronomyDetailViewModel
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.favorite.AstronomyFavoritesViewModel
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyListViewModel
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.onboarding.AstronomyOnboardingViewModel
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.splash.AstronomySplashViewModel

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::AstronomyListViewModel)
    viewModelOf(::AstronomyDetailViewModel)
    viewModelOf(::AstronomyFavoritesViewModel)
    viewModelOf(::AstronomyOnboardingViewModel)
    viewModelOf(::AstronomySplashViewModel)
}