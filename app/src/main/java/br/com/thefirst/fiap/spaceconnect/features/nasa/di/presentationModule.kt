package br.com.thefirst.fiap.spaceconnect.features.nasa.di


import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val graphicModule = module {
    viewModelOf(::AstronomyListViewModel)
}