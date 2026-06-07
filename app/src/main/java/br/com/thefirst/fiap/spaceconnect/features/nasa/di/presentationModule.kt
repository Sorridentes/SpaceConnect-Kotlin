package br.com.thefirst.fiap.spaceconnect.features.nasa.di

import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.detail.AstronomyDetailViewModel
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.favorite.AstronomyFavoritesViewModel
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::AstronomyListViewModel)
    viewModelOf(::AstronomyDetailViewModel)
    viewModelOf(::AstronomyFavoritesViewModel)
}