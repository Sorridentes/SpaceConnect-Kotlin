package br.com.thefirst.fiap.spaceconnect.features.nasa.di

import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase.GetAstronomyByDateUseCase
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase.GetAstronomyListByDateUseCase
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase.GetCachedFromDB
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase.GetFavoriteAstronomyListUseCase
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.usecase.ToggleFavoriteUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetAstronomyListByDateUseCase(repository = get()) }
    factory { GetAstronomyByDateUseCase(repository = get()) }
    factory { GetCachedFromDB(repository = get()) }
    factory { GetFavoriteAstronomyListUseCase(repository = get()) }
    factory { ToggleFavoriteUseCase(repository = get()) }
}