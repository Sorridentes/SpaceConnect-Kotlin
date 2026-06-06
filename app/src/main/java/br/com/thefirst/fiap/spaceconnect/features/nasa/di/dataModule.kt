// features/nasa/di/dataModule.kt
package br.com.thefirst.fiap.spaceconnect.features.nasa.di

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.AstronomyLocalDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.local.NasaDatabase
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote.AstronomyRemoteDataSource
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.remote.AstronomyRemoteDataSourceImpl
import br.com.thefirst.fiap.spaceconnect.features.nasa.data.repository.AstronomyRepositoryImpl
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.AstronomyRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single<AstronomyRemoteDataSource> {
        AstronomyRemoteDataSourceImpl(api = get())
    }

    single {
        NasaDatabase.getDatabase(androidApplication())
    }

    single {
        get<NasaDatabase>().astronomyDao()
    }

    single {
        AstronomyLocalDataSource(dao = get())
    }

    single<AstronomyRepository> {
        AstronomyRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}