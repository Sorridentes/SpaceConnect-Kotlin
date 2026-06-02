package br.com.thefirst.fiap.spaceconnect.features.nasa.di

import br.com.thefirst.fiap.spaceconnect.presentation.space.TestNasaViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val graphicModule = module {
    viewModelOf(::TestNasaViewModel)
}