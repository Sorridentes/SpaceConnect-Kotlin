package br.com.thefirst.fiap.spaceconnect.features.nasa.di

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote.AstronomyRemoteDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote.AstronomyRemoteDataSourceImpl
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.repository.AstronomyRepositoryImpl
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository
import org.koin.dsl.module

val dataModule = module {
    single<AstronomyRemoteDataSource> {
        AstronomyRemoteDataSourceImpl(api = get())
    }

    single<AstronomyRepository> {
        AstronomyRepositoryImpl(get())
    }
}