package br.com.thefirst.fiap.spaceconnect.features.firebase.di

import br.com.thefirst.fiap.spaceconnect.features.firebase.data.remote.FirebaseAuthDataSource
import br.com.thefirst.fiap.spaceconnect.features.firebase.data.remote.FirebaseAuthDataSourceImpl
import br.com.thefirst.fiap.spaceconnect.features.firebase.data.repository.AuthRepositoryImpl
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.repository.AuthRepository
import org.koin.dsl.module

val dataModule = module {
    single<FirebaseAuthDataSource> {
        FirebaseAuthDataSourceImpl(auth = get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(dataSource = get())
    }
}