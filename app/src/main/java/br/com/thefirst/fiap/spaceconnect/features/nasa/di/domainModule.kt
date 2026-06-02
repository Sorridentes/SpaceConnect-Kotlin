package br.com.thefirst.fiap.spaceconnect.features.nasa.di

import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase.GetAstronomyByDateUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetAstronomyByDateUseCase(get()) }
}
