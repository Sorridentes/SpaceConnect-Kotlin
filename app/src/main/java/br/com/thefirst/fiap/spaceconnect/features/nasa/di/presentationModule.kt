package br.com.thefirst.fiap.spaceconnect.features.nasa.di


import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.detail.AstronomyDetailViewModel
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::AstronomyListViewModel)
    viewModelOf(::AstronomyDetailViewModel)
}